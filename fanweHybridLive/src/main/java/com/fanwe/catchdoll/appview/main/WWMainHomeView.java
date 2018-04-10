package com.fanwe.catchdoll.appview.main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.appview.pagerindicator.WWHomeTitleTab;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.event.ERetryInitSuccess;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.lib.viewpager.SDViewPager;
import com.fanwe.lib.viewpager.indicator.IPagerIndicatorItem;
import com.fanwe.lib.viewpager.indicator.adapter.PagerIndicatorAdapter;
import com.fanwe.lib.viewpager.indicator.impl.PagerIndicator;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.appview.main.LiveTabBaseView;
import com.fanwe.live.model.HomeTabTitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页-首页
 * Created by LianCP on 2017/11/13.
 */
public class WWMainHomeView extends BaseAppView
{

    public WWMainHomeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWMainHomeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainHomeView(Context context)
    {
        super(context);
        init();
    }

    private TextView tv_app_name;
    private SDViewPager vpg_content;
    private LinearLayout ll_tab_menu;
    private PagerIndicator view_pager_indicator;

    private List<HomeTabTitleModel> mListModel = new ArrayList<>();

    private HomeTabTitleModel mSelectTitleModel;

    protected void init()
    {
        setContentView(R.layout.ww_view_main_home);
        tv_app_name = (TextView) findViewById(R.id.tv_app_name);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
        ll_tab_menu = (LinearLayout) findViewById(R.id.ll_tab_menu);
        view_pager_indicator = (PagerIndicator) findViewById(R.id.view_pager_indicator);

        initTitle();
        initTabsData();
        initViewPager();
    }

    private void initTitle()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            SDViewBinder.setTextView(tv_app_name, initActModel.getApp_name(), "方维抓娃娃");
        }
    }

    private void initTabsData()
    {
        mListModel.clear();

        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            List<HomeTabTitleModel> listTab = initActModel.getVideo_classified();
            if (listTab != null && !listTab.isEmpty())
            {
                mListModel.addAll(listTab);
            } else
            {
                HomeTabTitleModel tabAll = new HomeTabTitleModel();
                tabAll.setTitle("全部");
                mListModel.add(tabAll);
            }

            //头部菜单的选项必须大于一个才显示，否则隐藏头部菜单选项
            if (!mListModel.isEmpty() && mListModel.size() > 1)
            {
                ll_tab_menu.setVisibility(View.VISIBLE);
            } else
            {
                ll_tab_menu.setVisibility(View.GONE);
            }
        }
    }

    private void initViewPager()
    {
        view_pager_indicator.setViewPager(vpg_content);
        view_pager_indicator.setAdapter(new PagerIndicatorAdapter()
        {
            @Override
            protected IPagerIndicatorItem onCreatePagerIndicatorItem(int position, ViewGroup viewParent)
            {
                WWHomeTitleTab item = new WWHomeTitleTab(getActivity());
                HomeTabTitleModel model = SDCollectionUtil.get(mListModel, position);
                item.setData(model);
                return item;
            }
        });

        vpg_content.removeOnPageChangeListener(mOnPageChangeListener);
        vpg_content.addOnPageChangeListener(mOnPageChangeListener);

        vpg_content.setOffscreenPageLimit(2);
        vpg_content.setAdapter(mPagerAdapter);

        dealLastSelected();
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            mSelectTitleModel = mListModel.get(position);
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };

    private SDPagerAdapter mPagerAdapter = new SDPagerAdapter<HomeTabTitleModel>(mListModel, getActivity())
    {
        @Override
        public View getView(ViewGroup container, int position)
        {
            LiveTabBaseView view = null;

            WWTabAllView tabView = new WWTabAllView(getActivity());
            tabView.setTabTitleModel(mListModel.get(position));

            view = tabView;
            if (view != null)
            {
                view.setParentViewPager(vpg_content);
            }

            return view;
        }
    };

    public void onEventMainThread(ERetryInitSuccess event)
    {
        initTabsData();
        mPagerAdapter.notifyDataSetChanged();
        dealLastSelected();
    }

    private void dealLastSelected()
    {
        int index = mListModel.indexOf(mSelectTitleModel);
        if (index < 0)
        {
            index = 0;
        }
        vpg_content.setCurrentItem(index);
    }

}
