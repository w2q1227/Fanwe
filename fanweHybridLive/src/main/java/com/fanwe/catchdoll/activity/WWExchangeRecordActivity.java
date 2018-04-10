package com.fanwe.catchdoll.activity;

import android.os.Bundle;

import com.fanwe.catchdoll.adapter.WWExchangeRecordAdapter;
import com.fanwe.catchdoll.appview.WWEmptyView;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWExchangeRecordActModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.lib.pulltorefresh.SDPullToRefreshView;
import com.fanwe.lib.statelayout.SDStateLayout;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

/**
 * <商城积分兑换记录>
 * Created by wwb on 2017/12/13 11:54.
 */

public class WWExchangeRecordActivity extends BaseTitleActivity
{

    private SDRecyclerView mRecyclerView;
    private SDPullToRefreshView mPullToRefreshView;
    private FPageModel mPageModel = new FPageModel();
    private WWExchangeRecordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ww_act_exchange_record);
        mTitle.setMiddleTextTop("兑换记录");

        init();
    }

    private void init()
    {
        mRecyclerView = (SDRecyclerView) findViewById(R.id.lv_content);
        mRecyclerView.setLinearVertical();

        mPullToRefreshView = (SDPullToRefreshView) findViewById(R.id.view_pull_to_refresh);

        setAdapter();

        getPullToRefreshViewWrapper().setPullToRefreshView(mPullToRefreshView);
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

        mPullToRefreshView.startRefreshingFromHeader();

    }

    private void setAdapter()
    {
        mAdapter = new WWExchangeRecordAdapter(null, this);

        mRecyclerView.setAdapter(mAdapter);

        setStateLayout((SDStateLayout)findViewById(R.id.view_state_layout));
        getStateLayout().setAdapter(mAdapter);

        WWEmptyView view_empty = new WWEmptyView(this);
        view_empty.getTextHint().setText("暂无兑换记录");
        view_empty.getImageHint().setImageResource(R.drawable.ww_ic_exchange_record_empty);

        getStateLayout().getEmptyView().setContentView(view_empty);
        getStateLayout().setContentTop(false);
    }

    public void requestData(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);

        WWCommonInterface.requestExchangeRecordList(page, new AppRequestCallback<WWExchangeRecordActModel>()
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

}
