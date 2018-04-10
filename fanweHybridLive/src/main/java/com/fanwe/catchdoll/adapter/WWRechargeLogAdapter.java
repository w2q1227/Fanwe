package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWRechargeLogModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;

import java.util.List;

/**
 * des:充值明细
 * Created by yangwb
 * on 2017/11/14.
 */

public class WWRechargeLogAdapter extends SDSimpleRecyclerAdapter<WWRechargeLogModel>
{
    public WWRechargeLogAdapter(List<WWRechargeLogModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int position)
    {
        return R.layout.ww_item_recharge_log;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWRechargeLogModel> holder, int position, WWRechargeLogModel model)
    {
        TextView tv_recharge_type = holder.get(R.id.tv_recharge_type);
        TextView tv_recharge_time = holder.get(R.id.tv_recharge_time);
        TextView tv_recharge_money = holder.get(R.id.tv_recharge_money);

        SDViewBinder.setTextView(tv_recharge_type,model.getPayment_name() + model.getMoney() + "元");
        SDViewBinder.setTextView(tv_recharge_time,model.getPay_time());
        SDViewBinder.setTextView(tv_recharge_money," + " + model.getDiamonds());
    }
}
