package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWOrderDollReceiveInfoModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * des:
 * Created by yangwb
 * on 2017/12/5.
 */

public class WWOrderDollReceiveInfoAdapter extends SDSimpleRecyclerAdapter<WWOrderDollReceiveInfoModel>
{
    public WWOrderDollReceiveInfoAdapter(Activity activity)
    {
        super(activity);
    }

    public WWOrderDollReceiveInfoAdapter(List<WWOrderDollReceiveInfoModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_order_dot_receive_info;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWOrderDollReceiveInfoModel> holder, int position, WWOrderDollReceiveInfoModel model)
    {
        ImageView iv_doll = holder.get(R.id.iv_doll);
        TextView tv_dot_name = holder.get(R.id.tv_dot_name);
        TextView tv_time = holder.get(R.id.tv_time);
        View view_line = holder.get(R.id.view_line);

        if (position == 0)
        {
            SDViewUtil.setGone(view_line);
        }else
        {
            SDViewUtil.setVisible(view_line);
        }

        GlideUtil.load(model.getImg()).into(iv_doll);
        SDViewBinder.setTextView(tv_dot_name, model.getDoll_name());
        SDViewBinder.setTextView(tv_time, model.getGrab_time());
    }
}
