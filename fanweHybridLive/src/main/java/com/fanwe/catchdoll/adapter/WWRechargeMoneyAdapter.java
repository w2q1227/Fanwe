package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.RuleItemModel;

import java.util.List;

/**
 * Created by yhz on 2017/11/14.
 */

public class WWRechargeMoneyAdapter extends SDSimpleAdapter<RuleItemModel>
{
    public WWRechargeMoneyAdapter(List<RuleItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int i, View view, ViewGroup viewGroup)
    {
        return R.layout.ww_item_recharge_money;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup viewGroup, final RuleItemModel model)
    {
        TextView tv_name = get(R.id.tv_name, convertView);
        TextView tv_money = get(R.id.tv_money, convertView);
        TextView tv_gift_coins_des = get(R.id.tv_gift_coins_des, convertView);

        SDViewBinder.setTextView(tv_name, String.valueOf(model.getName()));
        SDViewBinder.setTextView(tv_money, model.getMoney_name());
        SDViewBinder.setTextView(tv_gift_coins_des, model.getGift_coins_des());

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, model, convertView);
            }
        });
    }
}
