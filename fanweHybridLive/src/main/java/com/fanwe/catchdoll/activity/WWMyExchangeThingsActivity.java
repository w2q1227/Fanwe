package com.fanwe.catchdoll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.catchdoll.appview.WWMyExchangeThingsListView;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.lib.viewpager.SDViewPager;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.view.LiveTabUnderline;

import java.util.ArrayList;
import java.util.List;
/**
 * <我的实物列表>
 * Created by wwb on 2017/12/27 15:55.
 */
public class WWMyExchangeThingsActivity extends BaseTitleActivity
{
    private LiveTabUnderline tab_all;
    private LiveTabUnderline tab_not_receive;
    private LiveTabUnderline tab_received;

    private SDViewPager vpg_content;

    private SDSelectViewManager<LiveTabUnderline> mSelectManager = new SDSelectViewManager<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_my_exchange_things);

        initView();
    }

    private void initView()
    {
        mTitle.setMiddleTextTop("我的实物");
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
        vpg_content.setAdapter(new MPagerAdapter(listModel, getActivity()));
    }

    private void initTab()
    {
        tab_all = (LiveTabUnderline) findViewById(R.id.tab_all);
        tab_not_receive = (LiveTabUnderline) findViewById(R.id.tab_not_receive);
        tab_received = (LiveTabUnderline) findViewById(R.id.tab_received);
        changeLiveTabUnderline(tab_all, "全部");
        changeLiveTabUnderline(tab_not_receive, "待领取");
        changeLiveTabUnderline(tab_received, "已领取");
    }

    private void changeLiveTabUnderline(LiveTabUnderline tabUnderline, String title)
    {
        tabUnderline.getTextViewTitle().setText(title);
        tabUnderline.configViewUnderline().setWidthNormal(SDViewUtil.dp2px(50)).setWidthSelected(SDViewUtil.dp2px(50));
        tabUnderline.configTextViewTitle().setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_12)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_12));

        LiveTabUnderline[] items = new LiveTabUnderline[]{tab_all, tab_not_receive, tab_received};

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


    private class MPagerAdapter extends SDPagerAdapter<String>
    {

        public MPagerAdapter(List<String> listModel, Activity activity)
        {
            super(listModel, activity);
        }

        @Override
        public View getView(ViewGroup viewGroup, int position)
        {
            WWMyExchangeThingsListView view = new WWMyExchangeThingsListView(getActivity());
            switch (position)
            {
                case 0:
                    view.setStatus(WWMyExchangeThingsListView.STATE_ALL);
                    view.requestData(false);
                    break;
                case 1:
                    view.setStatus(WWMyExchangeThingsListView.STATE_NOT_RECEIVE);
                    view.requestData(false);
                    break;
                case 2:
                    view.setStatus(WWMyExchangeThingsListView.STATE_RECEIVED);
                    view.requestData(false);
                    break;
                default:
                    break;
            }
            return view;
        }
    }

}
