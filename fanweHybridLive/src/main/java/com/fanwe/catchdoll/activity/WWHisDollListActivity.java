package com.fanwe.catchdoll.activity;

import android.os.Bundle;

import com.fanwe.catchdoll.adapter.WWMainHisDollAdapter;
import com.fanwe.catchdoll.appview.WWMainMyDollListEmptyView;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWMainMyDollListActModel;
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
 * <TA的娃娃列表>
 * Created by wwb on 2017/12/11 10:02.
 */

public class WWHisDollListActivity extends BaseTitleActivity
{
    /**
     * 房间room_id(int)
     */
    public static final String EXTRA_ROOM_ID = "extra_room_id";

    /**
     * 用户id(int)
     */
    public static final String EXTRA_USER_ID = "extra_user_id";

    private SDRecyclerView mRecyclerView;
    private WWMainHisDollAdapter mAdapter;
    private SDPullToRefreshView viewPullToRefresh;

    private FPageModel mPageModel = new FPageModel();


    private int user_id;
    private int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_hisdoll_list);

        mTitle.setMiddleTextTop("TA的娃");

        initView();
        getIntentData();
        requestData(false);
    }


    private void getIntentData()
    {
        user_id = getIntent().getIntExtra(EXTRA_USER_ID, 0);
        room_id = getIntent().getIntExtra(EXTRA_ROOM_ID, 0);
    }


    private void initView()
    {
        mRecyclerView = (SDRecyclerView) findViewById(R.id.lv_content);
        mRecyclerView.setGridVertical(2);
        viewPullToRefresh = (SDPullToRefreshView) findViewById(R.id.view_pull_to_refresh);
        setAdapter();

        getPullToRefreshViewWrapper().setPullToRefreshView(viewPullToRefresh);
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
        mAdapter = new WWMainHisDollAdapter(null, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setStateLayout((SDStateLayout)findViewById(R.id.view_state_layout));
        getStateLayout().setAdapter(mAdapter);

        WWMainMyDollListEmptyView view_empty = new WWMainMyDollListEmptyView(this);
        getStateLayout().getEmptyView().setContentView(view_empty);
        getStateLayout().setContentTop(false);
    }


    public void requestData(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);

        WWCommonInterface.requestHisDollList(page, user_id, room_id, new AppRequestCallback<WWMainMyDollListActModel>()
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
