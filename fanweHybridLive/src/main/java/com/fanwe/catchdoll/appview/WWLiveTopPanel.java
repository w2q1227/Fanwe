package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveViewerListRecyclerAdapter;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.model.App_viewerActModel;
import com.fanwe.live.model.UserModel;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMManager;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */

public class WWLiveTopPanel extends BaseAppView
{
    private ImageView btnQuit;
    private SDRecyclerView revIcons;
    private TextView tvRoomNum;

    private Callback callback;

    private LiveViewerListRecyclerAdapter adapter;

    public WWLiveTopPanel(Context context)
    {
        super(context);
        init();
    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
    }

    private void init()
    {
        setContentView(R.layout.ww_view_live_top_panel);

        btnQuit = (ImageView) findViewById(R.id.btn_quit);
        revIcons = (SDRecyclerView) findViewById(R.id.rev_icons);
        tvRoomNum = (TextView) findViewById(R.id.tv_room_num);

        btnQuit.setOnClickListener(this);
        revIcons.setOnClickListener(this);
        tvRoomNum.setOnClickListener(this);

        revIcons.setLinearHorizontal();
        adapter = new LiveViewerListRecyclerAdapter(getActivity());
        revIcons.setAdapter(adapter);
    }

    /**
     * 更新观众列表
     *
     * @param viewerModel
     */
    public void onLiveRefreshViewerList(App_viewerActModel viewerModel)
    {
        if (viewerModel == null)
        {
            return;
        }
        List<UserModel> listModel = viewerModel.getList();
        if (listModel != null && listModel.size() > 0)
        {
            onLiveRefreshViewerList(listModel);
        }
    }

    /**
     * 更新观众列表
     *
     * @param listModel
     */
    public void onLiveRefreshViewerList(List<UserModel> listModel)
    {
        if (listModel != null && listModel.size() > 0)
        {
            adapter.updateData(listModel);
        }
    }

    /**
     * 更新观众数量
     *
     * @param viewerNumber
     */
    public void updateViewerNumber(int viewerNumber)
    {
        if (viewerNumber < 0)
        {
            viewerNumber = 0;
        }
        SDViewBinder.setTextView(tvRoomNum, String.valueOf(viewerNumber));
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (callback == null) return;

        if (v == btnQuit)
        {
            callback.onClickFinish();
        }
    }

    public interface Callback
    {
        void onClickFinish();
    }
}
