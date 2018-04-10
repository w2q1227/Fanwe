package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.catchdoll.activity.WWMallDetailWebActivity;
import com.fanwe.catchdoll.adapter.WWMainIntegralMallAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.dialog.WWExchangeDiamondsDialog;
import com.fanwe.catchdoll.event.WWEMallThingsRefresh;
import com.fanwe.catchdoll.model.WWMainIntegralMallActModel;
import com.fanwe.catchdoll.model.WWMainIntegralMallModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.model.WWMallTabTitleModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

/**
 * <积分商城列表页面>
 * Created by wwb on 2017/12/13 09:21.
 */

public class WWMainMallListView extends WWBaseAppView
{
    public WWMainMallListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWMainMallListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainMallListView(Context context)
    {
        super(context);
        init();
    }

    private WWMallTabTitleModel mTabTitleModel;

    private SDRecyclerView mRecyclerView;
    private FPageModel mPageModel = new FPageModel();
    private WWMainIntegralMallAdapter mAdapter;


    private WWExchangeDiamondsDialog mDialog;

    private void init()
    {
        setContentView(R.layout.ww_view_main_mall_list);

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

    private void setAdapter()
    {
        mAdapter = new WWMainIntegralMallAdapter(null, getActivity());

        mAdapter.setItemClickCallback(new SDItemClickCallback<WWMainIntegralMallModel>()
        {
            @Override
            public void onItemClick(int i, WWMainIntegralMallModel model, View view)
            {
                if (mTabTitleModel.getId() == 0)
                {
                    showExchangeDialog(model);
                } else
                {
                    Intent intent = new Intent(getActivity(), WWMallDetailWebActivity.class);
                    intent.putExtra(WWMallDetailWebActivity.EXTRA_ID, model.getId());
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
        if (mTabTitleModel.getId() == 0)
        {
            requestFictitious(isLoadMore);
        } else
        {
            requestThing(isLoadMore);
        }

    }


    /**
     * 请求虚拟物品列表
     */
    private void requestFictitious(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestMainIntegralMallList(page, new AppRequestCallback<WWMainIntegralMallActModel>()
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

    /**
     * 请求实物列表
     */
    private void requestThing(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestExchangeThingList(page, mTabTitleModel.getId(), new AppRequestCallback<WWMainIntegralMallActModel>()
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


    private void showExchangeDialog(WWMainIntegralMallModel model)
    {
        if (mDialog == null)
        {
            mDialog = new WWExchangeDiamondsDialog(getActivity());
        }
        mDialog.setModel(model);
        mDialog.show();
    }

    /**
     * 设置分类实体
     */
    public void setTabTitleModel(WWMallTabTitleModel tabTitleModel)
    {
        mTabTitleModel = tabTitleModel;
    }


    /**
     * 刷新实物列表数据
     */
    public void onEventMainThread(WWEMallThingsRefresh event)
    {
        requestThing(false);
    }
}
