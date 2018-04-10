package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWGrabDollDataModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * des:
 * Created by yangwb
 * on 2017/12/7.
 */

public class WWGrabDollRecordAdapter extends SDSimpleRecyclerAdapter<WWGrabDollDataModel>
{
    public WWGrabDollRecordAdapter(Activity activity)
    {
        super(activity);
    }

    public WWGrabDollRecordAdapter(List<WWGrabDollDataModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int position)
    {
        return R.layout.ww_item_grab_doll_record;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWGrabDollDataModel> holder, int position, WWGrabDollDataModel model)
    {
        CircleImageView iv_header = holder.get(R.id.iv_header);
        TextView tv_name = holder.get(R.id.tv_name);
        TextView tv_time = holder.get(R.id.tv_time);

        GlideUtil.load(model.getHead_image()).into(iv_header);
        SDViewBinder.setTextView(tv_name, model.getNick_name());
        SDViewBinder.setTextView(tv_time, model.getEnd_time());
    }
}
