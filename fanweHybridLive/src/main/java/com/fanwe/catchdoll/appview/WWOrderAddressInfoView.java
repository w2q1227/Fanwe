package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWOrderDetailLogisticsModel;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * des:订单收货地址信息
 * Created by yangwb
 * on 2017/12/5.
 */

public class WWOrderAddressInfoView extends BaseAppView
{
    public WWOrderAddressInfoView(Context context)
    {
        super(context);
        init();
    }

    public WWOrderAddressInfoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWOrderAddressInfoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private TextView tv_receiver;
    private TextView tv_receive_mobile;
    private TextView tv_receive_address;
    private TextView tv_logistics_num;

    private void init()
    {
        setContentView(R.layout.ww_view_order_address_info);
        tv_receiver = (TextView) findViewById(R.id.tv_receiver);
        tv_receive_mobile = (TextView) findViewById(R.id.tv_receive_mobile);
        tv_receive_address = (TextView) findViewById(R.id.tv_receive_address);
        tv_logistics_num = (TextView) findViewById(R.id.tv_logistics_num);
    }

    public void setData(WWOrderDetailLogisticsModel detail)
    {
        SDViewBinder.setTextView(tv_receiver, detail.getDispatching().getConsignee());
        SDViewBinder.setTextView(tv_receive_mobile, detail.getDispatching().getMobile());
        SDViewBinder.setTextView(tv_receive_address, detail.getDispatching().getAddress());
        SDViewBinder.setTextView(tv_logistics_num, detail.getLogistics());
    }
}
