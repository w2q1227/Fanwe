package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWInviteHistoryModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;

import java.util.List;

/**
 * <邀请记录列表>
 * Created by wwb on 2017/12/14 10:40.
 */

public class WWInviteHistoryAdapter extends SDSimpleRecyclerAdapter<WWInviteHistoryModel>
{
    public WWInviteHistoryAdapter(List<WWInviteHistoryModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }


    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_invite_history;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWInviteHistoryModel> viewHolder, int i, WWInviteHistoryModel model)
    {
        TextView tv_nick_name = viewHolder.get(R.id.tv_nick_name);
        TextView tv_time = viewHolder.get(R.id.tv_time);

        SDViewBinder.setTextView(tv_nick_name, model.getNick_name());
        SDViewBinder.setTextView(tv_time, model.getTime());

    }
}
