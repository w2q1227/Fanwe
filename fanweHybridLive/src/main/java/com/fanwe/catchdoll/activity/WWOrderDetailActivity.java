package com.fanwe.catchdoll.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.appview.WWOrderAddressInfoView;
import com.fanwe.catchdoll.appview.WWOrderDollReceiveInfoView;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWOrderDetailActModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveWebViewActivity;

/**
 * des:订单详情
 * Created by yangwb
 * on 2017/12/5.
 */

public class WWOrderDetailActivity extends BaseTitleActivity
{
    /**
     * 订单id（String）
     */
    public static final String EXTRA_ORDER_ID = "extra_order_id";

    public static final String EXTRA_TYPE = "extra_type";

    private WWOrderDollReceiveInfoView view_receive_info;
    private WWOrderAddressInfoView view_address_info;
    private TextView tv_check_logistics;

    private String mOrderId;
    private String mLogisticsUrl;

    public static final int TYPE_DOLL = 0;
    public static final int TYPE_MY_THINGS = 1;
    private int type;//0-我的娃娃详情  1-我的实物详情

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_order_detail);
        view_receive_info = (WWOrderDollReceiveInfoView) findViewById(R.id.view_receive_info);
        view_address_info = (WWOrderAddressInfoView) findViewById(R.id.view_address_info);
        tv_check_logistics = (TextView) findViewById(R.id.tv_check_logistics);

        mTitle.setMiddleTextTop("订单详情");

        getIntentData();
        requestData();

        tv_check_logistics.setOnClickListener(this);
    }

    private void getIntentData()
    {
        mOrderId = getIntent().getStringExtra(EXTRA_ORDER_ID);
        type = getIntent().getIntExtra(EXTRA_TYPE, -1);
    }

    private void requestData()
    {
        if (type == TYPE_DOLL)
        {
            requestDollOrderDetail();
        } else if (type == TYPE_MY_THINGS)
        {
            SDViewBinder.setTextView(view_receive_info.getTv_receive_info(), "实物领取信息");
            requestMyThingsOrderDetail();
        }

    }


    private void requestDollOrderDetail()
    {
        WWCommonInterface.requestOrderDetail(mOrderId, new AppRequestCallback<WWOrderDetailActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mLogisticsUrl = actModel.getDetail().getLogistics_url();
                    view_receive_info.setData(actModel.getDetail_similar());
                    view_address_info.setData(actModel.getDetail());
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }


    private void requestMyThingsOrderDetail()
    {
        WWCommonInterface.requestMyThingsOrderDetail(mOrderId, new AppRequestCallback<WWOrderDetailActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mLogisticsUrl = actModel.getDetail().getLogistics_url();
                    view_receive_info.setData(actModel.getDetail_similar());
                    view_address_info.setData(actModel.getDetail());
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_check_logistics)
        {
            clickCheckLogistics();
        }
    }

    /**
     * 查看物流详情
     */
    private void clickCheckLogistics()
    {
        if (mLogisticsUrl != null)
        {
            Intent intent = new Intent(this, LiveWebViewActivity.class);
            intent.putExtra(LiveWebViewActivity.EXTRA_URL, mLogisticsUrl);
            startActivity(intent);
        } else
        {
            SDToast.showToast("暂无物流信息");
        }
    }
}
