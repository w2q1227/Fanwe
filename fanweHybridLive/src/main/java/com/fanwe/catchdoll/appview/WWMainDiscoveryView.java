package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.catchdoll.adapter.WWMainDiscoveryAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWMainDiscoveryListModel;
import com.fanwe.catchdoll.model.WWMainDiscoveryModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * <发现>
 */

public class WWMainDiscoveryView extends BaseAppView
{

    private SDRecyclerView mRecyclerView;
    private WWMainDiscoveryAdapter mAdapter;
    private List<WWMainDiscoveryModel> mListDiscoveryModels = new ArrayList<>();
    private int page = 1;
    private int hasNext = 1;

    public WWMainDiscoveryView(Context context)
    {
        super(context);
        init();
    }

    public WWMainDiscoveryView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainDiscoveryView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }


    private void init()
    {
        setContentView(R.layout.ww_view_main_discovery);
        mRecyclerView = (SDRecyclerView) findViewById(R.id.lv_content);
        mRecyclerView.setLinearVertical();
        setAdapter();
        requestData(false);
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                page = 1;
                requestData(false);
            }

            @Override
            public void onRefreshingFromFooter()
            {
                loadMoreViewer();
            }
        });
    }

    private void setAdapter()
    {
        mAdapter = new WWMainDiscoveryAdapter(mListDiscoveryModels, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        getStateLayout().setAdapter(mAdapter);
    }

    private void requestData(final boolean isLoadMore)
    {

        WWCommonInterface.requestMainDiscoveryList(page, new AppRequestCallback<WWMainDiscoveryListModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    fillData(isLoadMore, actModel.getList(), actModel.getHas_next());
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                onRefreshComplete();
                super.onFinish(resp);
            }
        });

    }


    private void loadMoreViewer()
    {
        if (hasNext == 1)
        {
            page++;
            requestData(true);
        } else
        {
            SDToast.showToast("没有更多数据了");
            onRefreshComplete();
        }
    }

    /**
     * 填充列表数据
     */
    protected void fillData(boolean loadMore, List<WWMainDiscoveryModel> data, int hasNext)
    {
        if (loadMore)
        {
            appendData(data);
        } else
        {
            updateData(data);
        }
        this.hasNext = hasNext;
    }

    protected void appendData(List<WWMainDiscoveryModel> data)
    {
        if (mAdapter != null)
        {
            mAdapter.appendData(data);
        }
    }

    protected void updateData(List<WWMainDiscoveryModel> data)
    {
        if (SDCollectionUtil.isEmpty(data))
        {
            getStateLayout().showEmpty();
        } else
        {
            getStateLayout().showContent();
        }
        if (mAdapter != null)
        {
            mAdapter.updateData(data);
        }
    }


    private void onRefreshComplete()
    {
        if (mRecyclerView != null)
        {
            getPullToRefreshViewWrapper().stopRefreshing();
        }
    }

}
