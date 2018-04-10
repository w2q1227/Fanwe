package com.fanwe.catchdoll.activity;

import android.os.Bundle;

import com.fanwe.catchdoll.adapter.WWRechargeLogAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWRechargeLogActModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.lib.pulltorefresh.SDPullToRefreshView;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

/**
 * des:充值明细
 * Created by yangwb
 * on 2017/11/15.
 */

public class WWRechargeLogActivity extends BaseTitleActivity
{
    private SDRecyclerView lv_content;

    private WWRechargeLogAdapter mRechargeLogAdapter;

    private int mHas_next;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
        initTitle();
        setAdapter();
        initPullToRefresh();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("充值明细");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    private void setAdapter()
    {
        mRechargeLogAdapter = new WWRechargeLogAdapter(null, getActivity());
        lv_content.setAdapter(mRechargeLogAdapter);
    }

    private void initPullToRefresh()
    {
        getPullToRefreshViewWrapper().setPullToRefreshView((SDPullToRefreshView) findViewById(R.id.view_pull_to_refresh));
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
                requestData(true);
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
        if (isLoadMore)
        {
            if (mHas_next == 1)
            {
                mPage++;
            } else
            {
                getPullToRefreshViewWrapper().stopRefreshing();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            mPage = 1;
        }

        WWCommonInterface.requestRechargeLog(mPage, new AppRequestCallback<WWRechargeLogActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mHas_next = actModel.getHas_next();
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
