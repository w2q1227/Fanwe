package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWMainIntegralMallModel;
import com.fanwe.catchdoll.view.CustomArtTextView;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * <积分商城适配器>
 * Created by wwb on 2017/12/13 09:30.
 */

public class WWMainIntegralMallAdapter extends SDSimpleRecyclerAdapter<WWMainIntegralMallModel>
{

    public WWMainIntegralMallAdapter(List<WWMainIntegralMallModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWMainIntegralMallModel> viewHolder, int i, WWMainIntegralMallModel model)
    {
        ImageView iv_bg = viewHolder.get(R.id.iv_bg);
        CustomArtTextView tv_diamonds_info = viewHolder.get(R.id.tv_diamonds_info);
        TextView tv_score_info = viewHolder.get(R.id.tv_score_info);
        TextView tv_number_info = viewHolder.get(R.id.tv_number_info);

        SDViewBinder.setTextView(tv_diamonds_info, model.getDiamonds_info());
        SDViewBinder.setTextView(tv_score_info, model.getScore_info());
        SDViewBinder.setTextView(tv_number_info, model.getNumber_info());
        GlideUtil.load(model.getImage()).into(iv_bg);

    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_main_integral_mall;
    }

}
