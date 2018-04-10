package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWAddressListActivity;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * Created by Administrator on 2017/12/7.
 */

public class WWAddressCardView extends BaseAppView
{
    public WWAddressCardView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWAddressCardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWAddressCardView(Context context)
    {
        super(context);
        init();
    }

    private LinearLayout ll_select_address;
    private RelativeLayout rl_default_address;
    private TextView tv_consignee;
    private TextView tv_mobile;
    private TextView tv_detail_address;

    private void init()
    {
        setContentView(R.layout.ww_view_address_card);
        ll_select_address = (LinearLayout) findViewById(R.id.ll_select_address);
        ll_select_address.setOnClickListener(this);
        SDViewUtil.setGone(ll_select_address);
        rl_default_address = (RelativeLayout) findViewById(R.id.rl_default_address);
        rl_default_address.setOnClickListener(this);
        SDViewUtil.setGone(ll_select_address);
        tv_consignee = (TextView) findViewById(R.id.tv_consignee);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_detail_address = (TextView) findViewById(R.id.tv_detail_address);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ll_select_address:
            case R.id.rl_default_address:
                clickSelectAddress();
                break;
            default:
                break;
        }
    }

    /**
     * 选择地址
     */
    private void clickSelectAddress()
    {
        Intent intent = new Intent(getActivity(), WWAddressListActivity.class);
        getActivity().startActivity(intent);
    }

    //设置为领取地址展示
    public void setNoGetAddressVisible(boolean visible)
    {
        if (visible)
        {
            SDViewUtil.setGone(ll_select_address);
            SDViewUtil.setVisible(rl_default_address);
        } else
        {
            SDViewUtil.setVisible(ll_select_address);
            SDViewUtil.setGone(rl_default_address);
        }
    }

    public void setDefaultAddress(@NonNull String consignee, @NonNull String mobile, @NonNull String detail_address)
    {
        SDViewUtil.setGone(ll_select_address);
        SDViewUtil.setVisible(rl_default_address);
        SDViewBinder.setTextView(tv_consignee, consignee);
        SDViewBinder.setTextView(tv_mobile, mobile);
        SDViewBinder.setTextView(tv_detail_address, detail_address);
    }
}
