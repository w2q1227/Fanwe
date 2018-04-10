package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWOrderDetailActivity;
import com.fanwe.catchdoll.activity.WWReceiveDollActivity;
import com.fanwe.catchdoll.model.WWMainMyDollModel;
import com.fanwe.catchdoll.view.CustomArtTextView;
import com.fanwe.lib.dialog.impl.SDDialogConfirm;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.xianrou.widget.QKDiamondTextView;

import java.util.List;

/**
 * <我的娃列表适配器>
 * Created by wang on 2017/11/14 14:33.
 */

public class WWMainMyDollAdapter extends SDSimpleRecyclerAdapter<WWMainMyDollModel>
{
    public WWMainMyDollAdapter(List<WWMainMyDollModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup viewGroup, int i)
    {
        return R.layout.ww_item_main_mydoll;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<WWMainMyDollModel> viewHolder, int i, final WWMainMyDollModel model)
    {
        ImageView iv_bg = viewHolder.get(R.id.iv_bg);
        TextView tv_receive_status = viewHolder.get(R.id.tv_receive_status);
        CustomArtTextView tv_doll_name = viewHolder.get(R.id.tv_doll_name);
        TextView tv_time = viewHolder.get(R.id.tv_time);

        QKDiamondTextView tv_exchange_diamonds = viewHolder.get(R.id.tv_exchange_diamonds);
        TextView tv_receive_doll = viewHolder.get(R.id.tv_receive_doll);


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

        final int status = model.getStatus();

        if (status == 0)//未领取 两个按钮都可以点击
        {
            tv_exchange_diamonds.setEnabled(true);
            tv_receive_doll.setEnabled(true);
            tv_receive_doll.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), WWReceiveDollActivity.class);
                    intent.putExtra(WWReceiveDollActivity.EXTRA_ID, model.getId());
                    getActivity().startActivity(intent);
                }
            });
            tv_exchange_diamonds.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mCallBack.onClickExchange(model.getId(), model.getEqual_diamonds());
                }
            });

        } else if (status == -2)//已兑换 两个按钮都不能点击 且点击列表会出现已经兑换过
        {
            tv_exchange_diamonds.setEnabled(false);
            tv_receive_doll.setEnabled(false);

            iv_bg.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SDDialogConfirm dialogConfirm = new SDDialogConfirm(getActivity());
                    SDViewUtil.setGone(dialogConfirm.tv_cancel);
                    dialogConfirm.setTextTitle("温馨提示")
                            .setTextCancel("")
                            .setTextContent("您已使用该娃娃兑换了" + model.getEqual_diamonds() + AppRuntimeWorker.getDiamondName())
                            .setTextConfirm("知道了");
                    dialogConfirm.show();
                }
            });

        } else if (status == -3)
        {//取消订单
            tv_exchange_diamonds.setEnabled(false);
            tv_receive_doll.setEnabled(false);

            iv_bg.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SDDialogConfirm dialogConfirm = new SDDialogConfirm(getActivity());
                    dialogConfirm.setTextTitle("关闭原因")
                            .setTextCancel("")
                            .setTextContent(model.getClose_reason())
                            .setTextConfirm("知道了");
                    dialogConfirm.show();
                }
            });

        } else //已领取或者领取中 两个按钮不能点击且 跳转到订单详情
        {
            tv_exchange_diamonds.setEnabled(false);
            tv_receive_doll.setEnabled(false);

            iv_bg.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), WWOrderDetailActivity.class);
                    intent.putExtra(WWOrderDetailActivity.EXTRA_ORDER_ID, model.getId());
                    intent.putExtra(WWOrderDetailActivity.EXTRA_TYPE, WWOrderDetailActivity.TYPE_DOLL);
                    getActivity().startActivity(intent);
                }
            });
        }
    }


    private CallBack mCallBack;

    public void setCallBack(CallBack callBack)
    {
        mCallBack = callBack;
    }

    public interface CallBack
    {
        void onClickExchange(String id, String diamonds);
    }


}