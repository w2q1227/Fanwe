package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWExchangeRecordActivity;
import com.fanwe.catchdoll.appview.pagerindicator.WWMallTitleTab;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.event.ERetryInitSuccess;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.lib.viewpager.SDViewPager;
import com.fanwe.lib.viewpager.indicator.IPagerIndicatorItem;
import com.fanwe.lib.viewpager.indicator.adapter.PagerIndicatorAdapter;
import com.fanwe.lib.viewpager.indicator.impl.PagerIndicator;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.live.R;
import com.fanwe.live.model.WWMallTabTitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <积分商城父页面>
 * Created by wwb on 2017/12/26 10:01.
 */

public class WWMainMallView extends SDAppView
{
    public WWMainMallView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWMainMallView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainMallView(Context context)
    {
        super(context);
        init();
    }

    private TextView tv_exchange_record;
    private SDViewPager vpg_content;
    private LinearLayout ll_tab_menu;
    private PagerIndicator view_pager_indicator;

    private List<WWMallTabTitleModel> mModelList = new ArrayList<>();
    private WWMallTabTitleModel mSelectTitleModel;

    private void init()
    {
        setContentView(R.layout.ww_view_main_mall);

        tv_exchange_record = (TextView) findViewById(R.id.tv_exchange_record);
        tv_exchange_record.setOnClickListener(this);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
        ll_tab_menu = (LinearLayout) findViewById(R.id.ll_tab_menu);
        view_pager_indicator = (PagerIndicator) findViewById(R.id.view_pager_indicator);

        initTabsData();
        initViewPager();
    }

    private void initTabsData()
    {
        mModelList.clear();

        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            List<WWMallTabTitleModel> listTab = initActModel.getDoll_exchange_cate();

            if (listTab != null && !listTab.isEmpty())
            {
                mModelList.addAll(listTab);
            } else
            {
                WWMallTabTitleModel tabAll = new WWMallTabTitleModel();
                tabAll.setTitle("虚拟");
                mModelList.add(tabAll);
            }


            //头部菜单的选项必须大于一个才显示，否则隐藏头部菜单选项
            if (!mModelList.isEmpty() && mModelList.size() > 1)
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
            protected IPagerIndicatorItem onCreatePagerIndicatorItem(int position, ViewGroup viewGroup)
            {
                WWMallTitleTab titleTab = new WWMallTitleTab(getActivity());
                WWMallTabTitleModel model = SDCollectionUtil.get(mModelList, position);
                titleTab.setData(model);
                return titleTab;
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
            mSelectTitleModel = mModelList.get(position);
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    };

    private SDPagerAdapter mPagerAdapter = new SDPagerAdapter<WWMallTabTitleModel>(mModelList, getActivity())
    {
        @Override
        public View getView(ViewGroup viewGroup, int position)
        {
            WWMainMallListView view = new WWMainMallListView(getActivity());
            view.setTabTitleModel(mModelList.get(position));
            view.requestData(false);
            return view;
        }
    };


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_exchange_record)
        {
            getActivity().startActivity(new Intent(getActivity(), WWExchangeRecordActivity.class));
        }
    }

    public void onEventMainThread(ERetryInitSuccess event)
    {
        initTabsData();
        mPagerAdapter.notifyDataSetChanged();
        dealLastSelected();
    }

    private void dealLastSelected()
    {
        int index = mModelList.indexOf(mSelectTitleModel);
        if (index < 0)
        {
            index = 0;
        }
        vpg_content.setCurrentItem(index);
    }


}
