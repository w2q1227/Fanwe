package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.PayItemModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by yhz on 2017/11/14.
 */

public class WWRechargeWayAdapter extends SDSimpleAdapter<PayItemModel>
{
    public WWRechargeWayAdapter(List<PayItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
        this.getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public int getLayoutId(int i, View view, ViewGroup viewGroup)
    {
        return R.layout.ww_item_recharge_way;
    }

    @Override
    protected void onUpdateView(int position, View convertView, ViewGroup parent, PayItemModel model)
    {
        ImageView iv_selected = get(R.id.iv_selected, convertView);
        if (model.isSelected())
        {
            SDViewUtil.setVisible(iv_selected);
        } else
        {
            SDViewUtil.setGone(iv_selected);
        }
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup viewGroup, final PayItemModel model)
    {
        ImageView iv_logo = get(R.id.iv_logo, convertView);

        GlideUtil.load(model.getLogo()).placeholder(0).into(iv_logo);
        onUpdateView(position, convertView, viewGroup, model);

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, model, v);
            }
        });
    }
}
