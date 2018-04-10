package com.fanwe.catchdoll.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fanwe.catchdoll.appview.WWAddressCardView;
import com.fanwe.catchdoll.appview.WWDollCardView;
import com.fanwe.catchdoll.appview.WWRechargeWayView;
import com.fanwe.catchdoll.appview.WWSelectDollListView;
import com.fanwe.catchdoll.event.WWESelectAddress;
import com.fanwe.catchdoll.model.WWAddressModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.lib.pulltorefresh.SDPullToRefreshView;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.event.EWxPayResultCodeComplete;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;
//import com.fanwe.live.wxapi.WXPayEntryActivity;
import com.wawakuailai.www.wxapi.WXPayEntryActivity;
import com.fanwei.jubaosdk.shell.OnPayResultListener;

/**
 * <提取详情基类>
 * Created by wwb on 2017/12/27 15:58.
 */

public class WWBaseReceiveActivity extends BaseTitleActivity
{

    //对应的商品记录ID (String)
    public static final String EXTRA_ID = "extra_id";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_receive_doll);
        init();
    }

    protected ScrollView sv_content;
    protected WWAddressCardView ww_address_card;
    protected WWDollCardView ww_doll_card;
    protected WWSelectDollListView ww_select_doll_list;
    protected WWRechargeWayView ww_recharge_way;
    protected TextView tv_confirm_get;

    protected String mId;
    protected String mAddressID;

    protected int mPayId;

    protected int mRechargeBtnShow;

    protected void init()
    {
        initTitle();
        initView();
        initIntent();
        initValue();
        requestDetail();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("提取详情");
    }

    private void initView()
    {
        sv_content = (ScrollView) findViewById(R.id.sv_content);
        ww_address_card = (WWAddressCardView) findViewById(R.id.ww_address_card);
        ww_doll_card = (WWDollCardView) findViewById(R.id.ww_doll_card);
        ww_select_doll_list = (WWSelectDollListView) findViewById(R.id.ww_select_doll_list);
        ww_recharge_way = (WWRechargeWayView) findViewById(R.id.ww_recharge_way);
        tv_confirm_get = (TextView) findViewById(R.id.tv_confirm_get);
        tv_confirm_get.setOnClickListener(this);
    }

    private void initIntent()
    {
        mId = getIntent().getStringExtra(EXTRA_ID);
    }

    private void initValue()
    {
        getPullToRefreshViewWrapper().setPullToRefreshView((SDPullToRefreshView) findViewById(R.id.view_pull_to_refresh));
        getPullToRefreshViewWrapper().setModePullFromHeader();
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                requestDetail();
            }

            @Override
            public void onRefreshingFromFooter()
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_confirm_get:
                clickTvConfirmGet();
                break;
            default:
                break;
        }
    }

    /**
     * 立即领取
     */
    private void clickTvConfirmGet()
    {
        pay();
    }

    protected void onRefreshComplete()
    {
        if (sv_content != null)
        {
            getPullToRefreshViewWrapper().stopRefreshing();
        }
    }

    private void pay()
    {
        if (mRechargeBtnShow == 1)
        {
            mPayId = ww_recharge_way.getPayId();
            if (mPayId <= 0)
            {
                SDToast.showToast("请选择支付方式");
                return;
            }
        }

        if (TextUtils.isEmpty(mAddressID))
        {
            SDToast.showToast("请选择地址");
            return;
        }
        String ids = ww_select_doll_list.getSelectGoodsIds(mId);
        String totalFreight = ww_select_doll_list.getTotalFreight();

        requestPay(ids, totalFreight);
    }

    protected PayResultListner payResultListner = new PayResultListner()
    {
        @Override
        public void onSuccess()
        {
            onPaySuccess();
        }

        @Override
        public void onDealing()
        {

        }

        @Override
        public void onFail()
        {

        }

        @Override
        public void onCancel()
        {

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

    protected OnPayResultListener jbfPayResultListener = new OnPayResultListener()
    {

        @Override
        public void onSuccess(Integer integer, String s, String s1)
        {
            onPaySuccess();
        }

        @Override
        public void onFailed(Integer integer, String s, String s1)
        {

        }
    };

    public void onEventMainThread(WWESelectAddress model)
    {
        WWAddressModel wwAddressModel = model.mSelectAddress;
        if (wwAddressModel != null)
        {
            mAddressID = wwAddressModel.getId();
            ww_address_card.setDefaultAddress(wwAddressModel.getConsignee(), wwAddressModel.getMobile(), wwAddressModel.getDetailedAddress());
        }
    }


    protected void requestDetail()
    {
    }

    protected void requestPay(String ids, String totalFreight)
    {
    }

    protected void onPaySuccess()
    {
    }


    /*微信支付回调返回信息*/
    public void onEventMainThread(final EWxPayResultCodeComplete event)
    {
        switch (event.WxPayResultCode)
        {
            case WXPayEntryActivity.RespErrCode.CODE_CANCEL:
                payResultListner.onCancel();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_FAIL:
                payResultListner.onFail();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_SUCCESS:
                payResultListner.onSuccess();
                break;
        }
    }


}
