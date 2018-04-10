package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.catchdoll.adapter.WWMainMyDollAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEMyDollListChange;
import com.fanwe.catchdoll.event.WWEReceiveDoll;
import com.fanwe.catchdoll.event.WWEUserInfoChange;
import com.fanwe.catchdoll.model.WWDollExchangeDiamondActModel;
import com.fanwe.catchdoll.model.WWMainMyDollListActModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.lib.dialog.ISDDialogConfirm;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.lib.dialog.impl.SDDialogConfirm;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

import de.greenrobot.event.EventBus;

/**
 * <我的娃娃列表>
 * Created by wang on 2017/11/14 10:43.
 */

public class WWMainMyDollListView extends WWBaseAppView
{
    public final static int ALL_DOLL = 0;//全部
    public final static int NOT_RECEIVE_DOLL = 1;//待领取
    public final static int RECEIVED_DOLL = 2;//已领取
    private SDDialogConfirm mDialogConfirm;

    public WWMainMyDollListView(Context context)
    {
        super(context);
        init();
    }

    public WWMainMyDollListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainMyDollListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private SDRecyclerView mRecyclerView;
    private WWMainMyDollAdapter mAdapter;
    private int status;

    private FPageModel mPageModel = new FPageModel();

    private void init()
    {
        setContentView(R.layout.ww_view_main_mydoll_list);

        mRecyclerView = (SDRecyclerView) findViewById(R.id.lv_content);
        mRecyclerView.setGridVertical(2);
        setAdapter();

        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                requestData(false);
            }

            @Override
            public void onRefreshingFromFooter()
            {
                if (mPageModel.hasNextPage())
                {
                    requestData(true);
                } else
                {
                    SDToast.showToast("没有更多数据了");
                    getPullToRefreshViewWrapper().stopRefreshing();
                }
            }
        });
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    private void setAdapter()
    {
        mAdapter = new WWMainMyDollAdapter(null, getActivity());
        mAdapter.setCallBack(new WWMainMyDollAdapter.CallBack()
        {
            @Override
            public void onClickExchange(String id, String diamonds)
            {
                showExchangeDialog(id, diamonds);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        getStateLayout().setAdapter(mAdapter);

        WWMainMyDollListEmptyView view_empty = new WWMainMyDollListEmptyView(getContext());
        getStateLayout().getEmptyView().setContentView(view_empty);
        getStateLayout().setContentTop(false);
    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && visibility == View.VISIBLE)
        {
            requestData(false);
        }
    }

    public void requestData(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestMainMyDollList(page, status, new AppRequestCallback<WWMainMyDollListActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mPageModel.updatePageOnSuccess(isLoadMore, getActModel().getHas_next());
                    if (isLoadMore)
                    {
                        mAdapter.appendData(actModel.getList());
                    } else
                    {
                        mAdapter.updateData(actModel.getList());
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                getPullToRefreshViewWrapper().stopRefreshing();
            }
        });
    }

    public void onEventMainThread(WWEReceiveDoll event)
    {
        requestData(false);
    }


    private void showExchangeDialog(final String id, String diamonds)
    {
        if (mDialogConfirm == null)
        {
            mDialogConfirm = new SDDialogConfirm(getActivity());
            mDialogConfirm.setTextTitle("兑换" + AppRuntimeWorker.getDiamondName())
                    .setTextConfirm("确认")
                    .setTextCancel("取消")
                    .setTextColorConfirm(SDResourcesUtil.getColor(R.color.res_main_color))
                    .setTextColorCancel(SDResourcesUtil.getColor(R.color.res_text_gray_l));
        }
        mDialogConfirm.setCallback(new ISDDialogConfirm.Callback()
        {
            @Override
            public void onClickCancel(View view, SDDialogBase sdDialogBase)
            {

            }

            @Override
            public void onClickConfirm(View view, SDDialogBase sdDialogBase)
            {
                requestExchangeDiamond(id);
            }
        });
        mDialogConfirm.setTextContent("确认使用娃娃兑换" + diamonds + AppRuntimeWorker.getDiamondName());
        mDialogConfirm.show();
    }

    private void requestExchangeDiamond(String id)
    {
        WWCommonInterface.requestExchangeDiamond(id, new AppRequestCallback<WWDollExchangeDiamondActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    EventBus.getDefault().post(new WWEMyDollListChange());//三个列表都要刷新
                    EventBus.getDefault().post(new WWEUserInfoChange());//更新个人中心的数据
                    SDToast.showToast("兑换成功");
                }
            }
        });
    }


}
