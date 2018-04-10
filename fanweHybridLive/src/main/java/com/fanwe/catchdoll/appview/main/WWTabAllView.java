package com.fanwe.catchdoll.appview.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.catchdoll.activity.WWLiveLayoutActivity;
import com.fanwe.catchdoll.adapter.WWMainHomeAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWMainHomeListModel;
import com.fanwe.catchdoll.model.WWMainHomeModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.lib.viewpager.SDViewPager;
import com.fanwe.lib.viewpager.pullcondition.IgnorePullCondition;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveTabHotHeaderView;
import com.fanwe.live.appview.main.LiveTabBaseView;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.model.HomeTabTitleModel;
import com.fanwe.live.model.LiveBannerModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;
import com.headerfooter.songhang.library.SmartRecyclerAdapter;

/**
 * Created by Administrator on 2017/11/23.
 */
public class WWTabAllView extends LiveTabBaseView
{
    public WWTabAllView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWTabAllView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWTabAllView(Context context)
    {
        super(context);
        init();
    }

    private HomeTabTitleModel mTabTitleModel;

    private SDRecyclerView lv_content;
    private LiveTabHotHeaderView mHeaderView;
    private WWMainHomeAdapter mAdapter;

    private FPageModel mPageModel = new FPageModel();

    /**
     * 设置直播分类实体
     *
     * @param tabTitleModel
     */
    public void setTabTitleModel(HomeTabTitleModel tabTitleModel)
    {
        mTabTitleModel = tabTitleModel;
        startLoopRunnable();
    }

    protected void init()
    {
        setContentView(R.layout.ww_view_tab_all);
        lv_content = (SDRecyclerView) findViewById(R.id.lv_content);
        lv_content.setGridVertical(2);

        setAdapter();
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                startLoopRunnable();
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

    private void initHeaderView()
    {
        mHeaderView = new LiveTabHotHeaderView(getActivity());
        mHeaderView.setBannerItemClickCallback(new SDItemClickCallback<LiveBannerModel>()
        {
            @Override
            public void onItemClick(int position, LiveBannerModel item, View view)
            {
                Intent intent = item.parseType(getActivity());
                if (intent != null)
                {
                    getActivity().startActivity(intent);
                }
            }
        });
        SDViewPager viewPager = getParentViewPager();
        if (viewPager != null)
        {
            viewPager.addPullCondition(new IgnorePullCondition(mHeaderView.getSlidingView()));
        }
    }

    private void setAdapter()
    {
        initHeaderView();

        mAdapter = new WWMainHomeAdapter(null, getActivity());
        mAdapter.setItemClickCallback(new SDItemClickCallback<WWMainHomeModel>()
        {
            @Override
            public void onItemClick(int i, WWMainHomeModel model, View view)
            {
                if (model.getStatus() == 2) {
                    SDToast.showToast("房间维护中");
                    return;
                }

                Intent intent = WWLiveLayoutActivity.startIntent(model.getRoom_id(), model.getGroup_id(), getContext());
                getActivity().startActivity(intent);
            }
        });
        getStateLayout().setAdapter(mAdapter);

        SmartRecyclerAdapter smartAdapter = new SmartRecyclerAdapter(mAdapter);
        smartAdapter.setHeaderView(mHeaderView);

        lv_content.setAdapter(smartAdapter);
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        super.onActivityResumed(activity);
        startLoopRunnable();
    }

    @Override
    protected void onLoopRun()
    {
        requestData(false);
    }

    /**
     * 请求数据
     */
    protected void requestData(final boolean isLoadMore)
    {
        if (!SDActivityManager.getInstance().isLastActivity(getActivity())) return;
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestHome(mTabTitleModel.getClassified_id(), page, new AppRequestCallback<WWMainHomeListModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    mPageModel.updatePageOnSuccess(isLoadMore, actModel.getHas_next());
                    mHeaderView.setData(actModel.getBanner());
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


    @Override
    protected void onRoomClosed(int roomId)
    {

    }

    @Override
    public void scrollToTop()
    {
        lv_content.scrollToStart();
    }

}
