package com.fanwe.catchdoll.appview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.catchdoll.event.WWEMyDollListChange;
import com.fanwe.lib.viewpager.SDViewPager;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.view.LiveTabUnderline;

import java.util.ArrayList;
import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/5 10:34.
 */

public class WWMainMyDollView extends WWBaseAppView
{
    public WWMainMyDollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWMainMyDollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainMyDollView(Context context)
    {
        super(context);
        init();
    }

    private LiveTabUnderline tab_all_doll;
    private LiveTabUnderline tab_not_receive_doll;
    private LiveTabUnderline tab_received_doll;

    private SDViewPager vpg_content;

    private SparseArray<BaseAppView> mArrayView = new SparseArray<>();
    private SDSelectViewManager<LiveTabUnderline> mSelectManager = new SDSelectViewManager<>();

    private void init()
    {
        setContentView(R.layout.ww_view_main_mydoll);
        initViewPager();
        initTab();
    }

    private void initViewPager()
    {
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
        vpg_content.setOffscreenPageLimit(2);

        List<String> listModel = new ArrayList<>();
        listModel.add("");
        listModel.add("");
        listModel.add("");

        vpg_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                mSelectManager.setSelected(position, true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
        vpg_content.setAdapter(new DollPagerAdapter(listModel, getActivity()));
    }


    private void initTab()
    {
        tab_all_doll = (LiveTabUnderline) findViewById(R.id.tab_all_doll);
        tab_not_receive_doll = (LiveTabUnderline) findViewById(R.id.tab_not_receive_doll);
        tab_received_doll = (LiveTabUnderline) findViewById(R.id.tab_received_doll);
        changeLiveTabUnderline(tab_all_doll, "全部");
        changeLiveTabUnderline(tab_not_receive_doll, "待领取");
        changeLiveTabUnderline(tab_received_doll, "已领取");

        LiveTabUnderline[] items = new LiveTabUnderline[]{tab_all_doll, tab_not_receive_doll, tab_received_doll};

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<LiveTabUnderline>()
        {
            @Override
            public void onNormal(int index, LiveTabUnderline liveTabUnderline)
            {

            }

            @Override
            public void onSelected(int index, LiveTabUnderline liveTabUnderline)
            {
                vpg_content.setCurrentItem(index);
            }
        });

        mSelectManager.setItems(items);
        mSelectManager.performClick(0);
    }

    private void changeLiveTabUnderline(LiveTabUnderline tabUnderline, String title)
    {
        tabUnderline.getTextViewTitle().setText(title);
        tabUnderline.configViewUnderline().setWidthNormal(SDViewUtil.dp2px(50)).setWidthSelected(SDViewUtil.dp2px(50));
        tabUnderline.configTextViewTitle().setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_12)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_12));

    }

    private class DollPagerAdapter extends SDPagerAdapter<String>
    {

        public DollPagerAdapter(List<String> listModel, Activity activity)
        {
            super(listModel, activity);
        }

        @Override
        public View getView(ViewGroup viewGroup, int position)
        {
            WWMainMyDollListView view = new WWMainMyDollListView(getActivity());
            switch (position)
            {
                case 0:
                    view.setStatus(WWMainMyDollListView.ALL_DOLL);
                    view.requestData(false);
                    break;
                case 1:
                    view.setStatus(WWMainMyDollListView.NOT_RECEIVE_DOLL);
                    view.requestData(false);
                    break;
                case 2:
                    view.setStatus(WWMainMyDollListView.RECEIVED_DOLL);
                    view.requestData(false);
                    break;
                default:
                    break;
            }
            if (null != view)
            {
                mArrayView.put(position, view);
            }
            return view;
        }
    }

    public void onEventMainThread(WWEMyDollListChange event)
    {
        for (int i = 0; i < mArrayView.size(); i++)
        {
            ((WWMainMyDollListView) mArrayView.get(i)).requestData(false);
        }
    }
}
