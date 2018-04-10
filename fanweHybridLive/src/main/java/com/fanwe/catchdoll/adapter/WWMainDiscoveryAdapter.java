package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWMainDiscoveryModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * <发现列表页>
 */

public class WWMainDiscoveryAdapter extends SDSimpleRecyclerAdapter<WWMainDiscoveryModel>
{

    public WWMainDiscoveryAdapter(List<WWMainDiscoveryModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_main_discovery;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWMainDiscoveryModel> viewHolder, final int i, final WWMainDiscoveryModel model)
    {
        RelativeLayout rl_root = viewHolder.get(R.id.rl_root);
        ImageView iv_bg = viewHolder.get(R.id.iv_bg);
        TextView tv_title = viewHolder.get(R.id.tv_title);
        TextView tv_end_time = viewHolder.get(R.id.tv_end_time);
        GlideUtil.load(model.getImg()).into(iv_bg);

        SDViewBinder.setTextView(tv_title, model.getTitle());
        SDViewBinder.setTextView(tv_end_time, model.getEnd_time());

        rl_root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
                intent.putExtra(LiveWebViewActivity.EXTRA_URL,model.getUrl());
                getActivity().startActivity(intent);
            }
        });

    }
}
