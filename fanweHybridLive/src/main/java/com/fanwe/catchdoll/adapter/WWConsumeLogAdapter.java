package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWCatchLogModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * des:消费明细
 * Created by yangwb
 * on 2017/11/14.
 */

public class WWConsumeLogAdapter extends SDSimpleRecyclerAdapter<WWCatchLogModel>
{
    public WWConsumeLogAdapter(List<WWCatchLogModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int position)
    {
        return R.layout.ww_item_consume_log;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWCatchLogModel> holder, int position, WWCatchLogModel model)
    {
        ImageView iv_game = holder.get(R.id.iv_game);
        TextView tv_game_name = holder.get(R.id.tv_game_name);
        TextView tv_game_time = holder.get(R.id.tv_game_time);
        TextView tv_deduct_money = holder.get(R.id.tv_deduct_money);

        GlideUtil.load(model.getImg()).into(iv_game);
        SDViewBinder.setTextView(tv_game_name,model.getTitle());
        SDViewBinder.setTextView(tv_game_time,model.getCreate_time());
        SDViewBinder.setTextView(tv_deduct_money,model.getDiamonds());
    }
}
