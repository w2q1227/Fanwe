package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.catchdoll.adapter.WWGrabDollRecordAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWGrabDollRecordActModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

/**
 * des:最近抓中记录
 * Created by yangwb
 * on 2017/12/7.
 */

public class WWGrabDollRecordView extends BaseAppView
{
    public WWGrabDollRecordView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWGrabDollRecordView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWGrabDollRecordView(Context context)
    {
        super(context);
        init();
    }

    private SDRecyclerView rv_content;

    private WWGrabDollRecordAdapter mRecordAdapter;

    private int mRoom_id;
    private FPageModel mPageModel = new FPageModel();

    public void setData(int room_id)
    {
        this.mRoom_id = room_id;
        requestData(false);
    }

    private void init()
    {
        setContentView(R.layout.ww_view_consume_log);

        initView();
        initData();
    }

    private void initView()
    {
        rv_content = (SDRecyclerView) findViewById(R.id.rv_content);
    }

    private void initData()
    {
        setAdapter();
        initPullToRefresh();
    }

    private void setAdapter()
    {
        mRecordAdapter = new WWGrabDollRecordAdapter(null, getActivity());
        rv_content.setAdapter(mRecordAdapter);
    }

    private void initPullToRefresh()
    {
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

    /**
     * 请求数据
     * @param isLoadMore
     */
    protected void requestData(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestGrabDollRecord(mRoom_id, page, new AppRequestCallback<WWGrabDollRecordActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mPageModel.updatePageOnSuccess(isLoadMore, getActModel().getHas_next());
                    if (isLoadMore)
                    {
                        mRecordAdapter.appendData(actModel.getList());
                    } else
                    {
                        mRecordAdapter.updateData(actModel.getList());
                    }
                }
            }
            @Override
            protected void onFinish(SDResponse resp)
            {
                getPullToRefreshViewWrapper().stopRefreshing();
                super.onFinish(resp);
            }
        });
    }
}
