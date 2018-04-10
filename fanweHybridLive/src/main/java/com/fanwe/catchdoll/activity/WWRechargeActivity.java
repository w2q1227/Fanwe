package com.fanwe.catchdoll.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.adapter.WWRechargeMoneyAdapter;
import com.fanwe.catchdoll.appview.WWRechargeWayView;
import com.fanwe.catchdoll.event.WWEUserInfoChange;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.common.CommonOpenSDK;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.lib.gridlayout.SDGridLayout;
import com.fanwe.lib.pulltorefresh.SDPullToRefreshView;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_payActModel;
import com.fanwe.live.model.App_rechargeActModel;
import com.fanwe.live.model.PayModel;
import com.fanwe.live.model.RuleItemModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;
import com.fanwei.jubaosdk.shell.OnPayResultListener;

import de.greenrobot.event.EventBus;


/**
 * Created by yhz on 2017/11/14. 娃娃充值页面
 */

public class WWRechargeActivity extends BaseTitleActivity
{
    private TextView tv_recharge_diamonds;
    private SDGridLayout gll_recharge_list;
    private WWRechargeWayView view_recharge_way;

    private WWRechargeMoneyAdapter mAdapterRechargeMoney;

    /**
     * 支付方式id
     */
    private int mPaymentId;
    /**
     * 支付金额选项id
     */
    private int mPaymentRuleId;

    private String mPayId;//订单ID

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_recharge);
        tv_recharge_diamonds = (TextView) findViewById(R.id.tv_recharge_diamonds);
        gll_recharge_list = (SDGridLayout) findViewById(R.id.gll_recharge_list);
        view_recharge_way = (WWRechargeWayView) findViewById(R.id.ww_recharge_way);
        getPullToRefreshViewWrapper().setPullToRefreshView((SDPullToRefreshView) findViewById(R.id.view_pull_to_refresh));

        initTitle();
        initAdapter();
        initPullToRefresh();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("充值");
    }

    private void initAdapter()
    {
        mAdapterRechargeMoney = new WWRechargeMoneyAdapter(null, getActivity());
        mAdapterRechargeMoney.setItemClickCallback(new SDItemClickCallback<RuleItemModel>()
        {
            @Override
            public void onItemClick(int i, RuleItemModel model, View view)
            {
                mPaymentRuleId = model.getId();
                clickTvConfirmPay();
            }
        });

        gll_recharge_list.setAdapter(mAdapterRechargeMoney);
    }

    private void initPullToRefresh()
    {
        getPullToRefreshViewWrapper().setModePullFromHeader();
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                requestData();
            }

            @Override
            public void onRefreshingFromFooter()
            {

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        requestData();
    }

    private void requestData()
    {
        CommonInterface.requestRecharge(new AppRequestCallback<App_rechargeActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    SDViewBinder.setTextView(tv_recharge_diamonds, String.valueOf(actModel.getDiamonds()));

                    view_recharge_way.setData(actModel.getPay_list());
                    mAdapterRechargeMoney.updateData(actModel.getRule_list());
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                getPullToRefreshViewWrapper().stopRefreshing();
                super.onFinish(resp);
            }
        });
    }

    /**
     * 输入金额支付
     */
    private void clickTvConfirmPay()
    {
        if (!validatePayment())
        {
            return;
        }
        requestPay();
    }

    private boolean validatePayment()
    {
        if (mPaymentRuleId <= 0)
        {
            SDToast.showToast("请选择充值金额");
            return false;
        }

        mPaymentId = view_recharge_way.getPayId();
        if (mPaymentId <= 0)
        {
            SDToast.showToast("请选择支付方式");
            return false;
        }
        return true;
    }

    private void requestPay()
    {
        CommonInterface.requestPay(mPaymentId, mPaymentRuleId, 0, new AppRequestCallback<App_payActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                showProgressDialog("正在启动插件");
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    PayModel pay = actModel.getPay();
                    if (pay != null)
                    {
                        mPayId = pay.getPay_id();
                    }
                    CommonOpenSDK.dealPayRequestSuccess(actModel, getActivity(), payResultListner, jbfPayResultListener);
                }
            }
        });
    }

    private void startWWRecharegeResultActivity()
    {
        if (TextUtils.isEmpty(mPayId))
        {
            SDToast.showToast("pay_id为空");
            return;
        }

        Intent intent = new Intent(getActivity(), WWRechargeResultActivity.class);
        intent.putExtra(WWRechargeResultActivity.EXTRA_ID, mPayId);
        startActivity(intent);
    }

    private PayResultListner payResultListner = new PayResultListner()
    {
        @Override
        public void onSuccess()
        {
            EventBus.getDefault().post(new WWEUserInfoChange());
            startWWRecharegeResultActivity();
        }

        @Override
        public void onDealing()
        {

        }

        @Override
        public void onFail()
        {
            startWWRecharegeResultActivity();
        }

        @Override
        public void onCancel()
        {
            startWWRecharegeResultActivity();
        }

        @Override
        public void onNetWork()
        {

        }

        @Override
        public void onOther()
        {

        }
    };

    private OnPayResultListener jbfPayResultListener = new OnPayResultListener()
    {

        @Override
        public void onSuccess(Integer integer, String s, String s1)
        {
            startWWRecharegeResultActivity();
        }

        @Override
        public void onFailed(Integer integer, String s, String s1)
        {
            startWWRecharegeResultActivity();
        }
    };
}
