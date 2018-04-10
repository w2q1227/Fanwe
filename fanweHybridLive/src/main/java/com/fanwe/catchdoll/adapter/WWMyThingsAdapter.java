package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWMyThingsModel;
import com.fanwe.catchdoll.view.CustomArtTextView;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * <我的实物>
 * Created by wwb on 2017/12/27 15:23.
 */

public class WWMyThingsAdapter extends SDSimpleRecyclerAdapter<WWMyThingsModel>
{
    public WWMyThingsAdapter(List<WWMyThingsModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_mythings;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWMyThingsModel> viewHolder, int i, WWMyThingsModel model)
    {
        ImageView iv_bg = viewHolder.get(R.id.iv_bg);
        TextView tv_receive_status = viewHolder.get(R.id.tv_receive_status);
        CustomArtTextView tv_doll_name = viewHolder.get(R.id.tv_doll_name);
        TextView tv_time = viewHolder.get(R.id.tv_time);

        SDViewBinder.setTextView(tv_doll_name, model.getDoll_name());
        SDViewBinder.setTextView(tv_time, model.getGrab_time());
        GlideUtil.load(model.getImg()).into(iv_bg);

        if (TextUtils.isEmpty(model.getStatus_name()))
        {
            SDViewUtil.setGone(tv_receive_status);
        } else
        {
            SDViewUtil.setVisible(tv_receive_status);
            SDViewBinder.setTextView(tv_receive_status, model.getStatus_name());
            tv_receive_status.setBackgroundDrawable(new SDDrawable().cornerTopLeft(25).cornerBottomLeft(25).color(Color.parseColor(model.getStatus_color())));
        }

    }
}
