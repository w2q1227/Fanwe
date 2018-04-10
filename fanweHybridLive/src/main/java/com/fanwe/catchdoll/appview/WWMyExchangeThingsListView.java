package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.catchdoll.activity.WWOrderDetailActivity;
import com.fanwe.catchdoll.activity.WWReceiveThingsActivity;
import com.fanwe.catchdoll.adapter.WWMyThingsAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEReceive;
import com.fanwe.catchdoll.model.WWMyThingsActModel;
import com.fanwe.catchdoll.model.WWMyThingsModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

/**
 * <我的实物列表>
 * Created by wwb on 2017/12/27 15:09.
 */

public class WWMyExchangeThingsListView extends WWBaseAppView
{
    public WWMyExchangeThingsListView(Context context)
    {
        super(context);
        init();
    }

    public WWMyExchangeThingsListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMyExchangeThingsListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public final static int STATE_ALL = 0;//全部
    public final static int STATE_NOT_RECEIVE = 1;//待领取
    public final static int STATE_RECEIVED = 2;//已领取


    private SDRecyclerView mRecyclerView;
    private WWMyThingsAdapter mAdapter;
    private int status;

    private FPageModel mPageModel = new FPageModel();

    private void init()
    {
        setContentView(R.layout.ww_view_mythings_list);

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
        mAdapter = new WWMyThingsAdapter(null, getActivity());
        mAdapter.setItemClickCallback(new SDItemClickCallback<WWMyThingsModel>()
        {
            @Override
            public void onItemClick(int i, WWMyThingsModel model, View view)
            {
                if (model.getStatus() == 0)//未领取
                {
                    Intent intent = new Intent(getActivity(), WWReceiveThingsActivity.class);
                    intent.putExtra(WWReceiveThingsActivity.EXTRA_ID, model.getId());
                    getActivity().startActivity(intent);
                } else
                {
                    Intent intent = new Intent(getActivity(), WWOrderDetailActivity.class);
                    intent.putExtra(WWOrderDetailActivity.EXTRA_ORDER_ID, model.getId());
                    intent.putExtra(WWOrderDetailActivity.EXTRA_TYPE, WWOrderDetailActivity.TYPE_MY_THINGS);
                    getActivity().startActivity(intent);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        getStateLayout().setAdapter(mAdapter);

        WWEmptyView view_empty = new WWEmptyView(getContext());
        getStateLayout().getEmptyView().setContentView(view_empty);
        getStateLayout().setContentTop(false);
    }

    public void requestData(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestMyThingsList(page, status, new AppRequestCallback<WWMyThingsActModel>()
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

    public void onEventMainThread(WWEReceive event)
    {
        requestData(false);
    }


}
