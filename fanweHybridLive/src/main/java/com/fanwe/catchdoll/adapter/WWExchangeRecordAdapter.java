package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWExchangeRecordModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;

import java.util.List;

/**
 * <兑换记录>
 * Created by wwb on 2017/12/13 12:02.
 */

public class WWExchangeRecordAdapter extends SDSimpleRecyclerAdapter<WWExchangeRecordModel>
{
    public WWExchangeRecordAdapter(List<WWExchangeRecordModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_exchange_record;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWExchangeRecordModel> viewHolder, int i, WWExchangeRecordModel model)
    {
        TextView tv_diamonds = viewHolder.get(R.id.tv_diamonds);
        TextView tv_creae_time = viewHolder.get(R.id.tv_creae_time);
        TextView tv_score = viewHolder.get(R.id.tv_score);

        SDViewBinder.setTextView(tv_diamonds, model.getDiamonds_info());
        SDViewBinder.setTextView(tv_score, model.getScore());
        SDViewBinder.setTextView(tv_creae_time, model.getCreate_time());
    }
}
