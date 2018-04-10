package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWGrabDollDataModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * des:
 * Created by yangwb
 * on 2017/12/7.
 */

public class WWGrabDollMasterAdapter extends SDSimpleRecyclerAdapter<WWGrabDollDataModel>
{
    public WWGrabDollMasterAdapter(Activity activity)
    {
        super(activity);
    }

    public WWGrabDollMasterAdapter(List<WWGrabDollDataModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int position)
    {
        return R.layout.ww_item_grab_doll_master;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWGrabDollDataModel> holder, int position, WWGrabDollDataModel model)
    {
        TextView tv_rank = holder.get(R.id.tv_rank);
        CircleImageView iv_master = holder.get(R.id.iv_master);
        ImageView iv_crown = holder.get(R.id.iv_crown);
        TextView tv_master_name = holder.get(R.id.tv_master_name);
        TextView tv_grab_times = holder.get(R.id.tv_grab_times);

        if (position == 0)
        {
            SDViewUtil.setVisible(iv_crown);
            SDViewUtil.setBackgroundResource(tv_rank, R.drawable.ww_layer_grab_doll_master_rank1_corner_2dp);
            iv_master.setBorderColor(SDResourcesUtil.getColor(R.color.grab_doll_master_rank1));
            iv_crown.setImageResource(R.drawable.ww_ic_crown);
        }else if (position == 1)
        {
            SDViewUtil.setVisible(iv_crown);
            SDViewUtil.setBackgroundResource(tv_rank, R.drawable.ww_layer_grab_doll_master_rank2_corner_2dp);
            iv_master.setBorderColor(SDResourcesUtil.getColor(R.color.grab_doll_master_rank2));
            iv_crown.setImageResource(R.drawable.ww_ic_silver_crown);
        }else if (position == 2)
        {
            SDViewUtil.setVisible(iv_crown);
            SDViewUtil.setBackgroundResource(tv_rank, R.drawable.ww_layer_grab_doll_master_rank3_corner_2dp);
            iv_master.setBorderColor(SDResourcesUtil.getColor(R.color.grab_doll_master_rank3));
            iv_crown.setImageResource(R.drawable.ww_ic_copper_crown);
        }else
        {
            SDViewUtil.setGone(iv_crown);
            SDViewUtil.setBackgroundResource(tv_rank, R.drawable.ww_layer_grab_doll_master_rank4_corner_2dp);
            iv_master.setBorderColor(SDResourcesUtil.getColor(R.color.white));
        }

        SDViewBinder.setTextView(tv_rank, String.valueOf(position + 1));
        GlideUtil.load(model.getHead_image()).into(iv_master);
        SDViewBinder.setTextView(tv_master_name, model.getNick_name());
        SDViewBinder.setTextView(tv_grab_times, "抓中" + model.getNum() + "次");
    }
}
