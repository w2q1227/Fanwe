package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.catchdoll.adapter.WWRechargeLogAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWRechargeLogActModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

/**
 * des:充值明细
 * Created by yangwb
 * on 2017/12/4.
 */

public class WWRechargeLogView extends BaseAppView
{
    public WWRechargeLogView(Context context)
    {
        super(context);
        init();
    }

    public WWRechargeLogView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWRechargeLogView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private SDRecyclerView lv_content;

    private WWRechargeLogAdapter mRechargeLogAdapter;

    private FPageModel mPageModel = new FPageModel();

    private void init()
    {
        setContentView(R.layout.ww_act_recharge_log);

        initView();
        initData();
    }

    private void initView()
    {
        lv_content = (SDRecyclerView) findViewById(R.id.lv_content);
    }

    private void initData()
    {
        setAdapter();
        initPullToRefresh();
    }

    private void setAdapter()
    {
        mRechargeLogAdapter = new WWRechargeLogAdapter(null, getActivity());
        lv_content.setAdapter(mRechargeLogAdapter);

        getStateLayout().setAdapter(mRechargeLogAdapter);

        WWMainMyDollListEmptyView view_empty = new WWMainMyDollListEmptyView(getContext());
        getStateLayout().getEmptyView().setContentView(view_empty);
        getStateLayout().setContentTop(false);
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
        requestData(false);
    }

    /**
     * 请求数据
     * @param isLoadMore
     */
    protected void requestData(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestRechargeLog(page, new AppRequestCallback<WWRechargeLogActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mPageModel.updatePageOnSuccess(isLoadMore, getActModel().getHas_next());
                    if (isLoadMore)
                    {
                        mRechargeLogAdapter.appendData(actModel.getList());
                    } else
                    {
                        mRechargeLogAdapter.updateData(actModel.getList());
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
