package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWMainHomeModel;
import com.fanwe.catchdoll.view.CustomArtTextView;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * 主页-首页适配器
 * Created by LianCP on 2017/11/13.
 */
public class WWMainHomeAdapter extends SDSimpleRecyclerAdapter<WWMainHomeModel>
{
    private int mSpanCount = 2;

    public WWMainHomeAdapter(List<WWMainHomeModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_main_home_view;
    }

    /**
     * 是否是第一列
     *
     * @param position
     * @param spanCount
     * @return
     */
    private static boolean isFirstColumn(int position, int spanCount)
    {
        return position % spanCount == 0;
    }

    /**
     * 是否第一行
     *
     * @param position
     * @param spanCount
     * @return
     */
    private static boolean isFirstRow(int position, int spanCount)
    {
        return position / spanCount <= 0;
    }

    /**
     * 是否最后一列
     *
     * @param position
     * @param spanCount
     * @return
     */
    private static boolean isLastColumn(int position, int spanCount)
    {
        return (position + 1) % spanCount == 0;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWMainHomeModel> holder, final int position, final WWMainHomeModel model)
    {
        ImageView iv_video_cover = holder.get(R.id.iv_video_cover);
        CustomArtTextView tv_price = holder.get(R.id.tv_price);
        TextView tv_state = holder.get(R.id.tv_state);
        CustomArtTextView tv_name = holder.get(R.id.tv_name);
        View view_card = holder.get(R.id.view_card);

        final int margins = SDViewUtil.dp2px(10);
        SDViewUtil.setMargins(view_card, margins / 2);

        if (isFirstColumn(position, mSpanCount))
        {
            SDViewUtil.setMarginLeft(view_card, margins);
        }
        if (isFirstRow(position, mSpanCount))
        {
            SDViewUtil.setMarginTop(view_card, margins);
        }
        if (isLastColumn(position, mSpanCount))
        {
            SDViewUtil.setMarginRight(view_card, margins);
        }

        GlideUtil.load(model.getLive_image()).into(iv_video_cover);
        tv_price.setText(model.getPrice());
        switch (model.getStatus())
        {
            case 0:
                tv_state.setBackgroundResource(R.drawable.ww_item_mian_home_free);
                break;
            case 1:
                tv_state.setBackgroundResource(R.drawable.ww_item_mian_home_use);
                break;
            case 2:
                tv_state.setBackgroundResource(R.drawable.ww_item_mian_home_maintain);
                break;
            default:
                break;
        }
        tv_state.setText(model.getStatus_name());
        tv_name.setText(model.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, model, v);
            }
        });
    }
}
