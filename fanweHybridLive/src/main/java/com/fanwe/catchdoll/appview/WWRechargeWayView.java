package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.catchdoll.adapter.WWRechargeWayAdapter;
import com.fanwe.lib.gridlayout.SDGridLayout;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.model.PayItemModel;

import java.util.List;

/**
 * Created by yhz on 2017/11/14.
 */

public class WWRechargeWayView extends BaseAppView
{
    public WWRechargeWayView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWRechargeWayView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWRechargeWayView(Context context)
    {
        super(context);
        init();
    }

    private SDGridLayout gl_payment;
    private WWRechargeWayAdapter mAdapter;

    private void init()
    {
        setContentView(R.layout.ww_view_recharge_way);
        gl_payment = (SDGridLayout) findViewById(R.id.gl_payment);
        gl_payment.setSpanCount(2);
        gl_payment.setVerticalSpacing(SDViewUtil.dp2px(10));

        initAdapter();
    }

    private void initAdapter()
    {
        mAdapter = new WWRechargeWayAdapter(null, getActivity());
        gl_payment.setAdapter(mAdapter);
        mAdapter.setItemClickCallback(new SDItemClickCallback<PayItemModel>()
        {
            @Override
            public void onItemClick(int i, PayItemModel payItemModel, View view)
            {
                mAdapter.getSelectManager().performClick(payItemModel);
            }
        });
    }

    /**
     * 设置支付方式数据
     *
     * @param listModel
     */
    public void setData(List<PayItemModel> listModel)
    {
        int selectIndex = 0;

        final int oldPayId = getPayId();
        if (oldPayId > 0 && listModel != null)
        {
            for (int i = 0; i < listModel.size(); i++)
            {
                PayItemModel item = listModel.get(i);
                if (oldPayId == item.getId())
                {
                    selectIndex = i;
                    break;
                }
            }
        }

        mAdapter.updateData(listModel);
        mAdapter.getSelectManager().performClick(selectIndex);
    }

    /**
     * 返回选中的支付方式id
     *
     * @return
     */
    public int getPayId()
    {
        PayItemModel model = mAdapter.getSelectManager().getSelectedItem();
        if (model != null)
        {
            return model.getId();
        }
        return 0;
    }
}
