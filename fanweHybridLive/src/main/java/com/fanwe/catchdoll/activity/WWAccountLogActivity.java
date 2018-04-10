package com.fanwe.catchdoll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.catchdoll.appview.WWConsumeLogView;
import com.fanwe.catchdoll.appview.WWRechargeLogView;
import com.fanwe.hybrid.activity.BaseTitleActivity;
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
 * des:账单明细
 * Created by yangwb
 * on 2017/12/4.
 */

public class WWAccountLogActivity extends BaseTitleActivity
{
    private LiveTabUnderline tab_recharge_log;
    private LiveTabUnderline tab_consume_log;
    private SDViewPager vpg_content;

    private SDSelectViewManager<LiveTabUnderline> mSelectManager = new SDSelectViewManager<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_account_log_new);

        initView();
        initData();
    }

    private void initView()
    {
        tab_recharge_log = (LiveTabUnderline) findViewById(R.id.tab_recharge_log);
        tab_consume_log = (LiveTabUnderline) findViewById(R.id.tab_consume_log);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
    }

    private void initData()
    {
        initTitle();
        initViewPager();
        initTab();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("账单明细");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    private void initViewPager()
    {
        List<String> listModel = new ArrayList<>();
        listModel.add("");
        listModel.add("");

        vpg_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
//                if (selectViewManager.getSelectedIndex() != position)
//                {
                    mSelectManager.setSelected(position, true);
//                }
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
        vpg_content.setAdapter(new LogPagerAdapter(listModel, getActivity()));
    }
    
    private void initTab()
    {
        changeLiveTabUnderline(tab_recharge_log, "充值明细");
        changeLiveTabUnderline(tab_consume_log, "消费明细");

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<LiveTabUnderline>()
        {
            @Override
            public void onNormal(int index, LiveTabUnderline liveTabUnderline)
            {

            }

            @Override
            public void onSelected(int index, LiveTabUnderline liveTabUnderline)
            {
                tabSelected(index);
            }
        });

        mSelectManager.setItems(new LiveTabUnderline[]{tab_recharge_log, tab_consume_log});
        mSelectManager.performClick(0);
    }

    private void changeLiveTabUnderline(LiveTabUnderline tabUnderline, String title)
    {
        tabUnderline.getTextViewTitle().setText(title);
        tabUnderline.configViewUnderline().setWidthNormal(SDViewUtil.dp2px(50)).setWidthSelected(SDViewUtil.dp2px(50));
        tabUnderline.configTextViewTitle().setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_12)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_12));
    }

    private void tabSelected(int index)
    {
        switch (index)
        {
            case 0:
                vpg_content.setCurrentItem(0);
                break;
            case 1:
                vpg_content.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    private class LogPagerAdapter extends SDPagerAdapter<String>
    {

        public LogPagerAdapter(List<String> listModel, Activity activity)
        {
            super(listModel, activity);
        }

        @Override
        public View getView(ViewGroup viewGroup, int position)
        {
            BaseAppView view = null;
            switch (position)
            {
                case 0:
                    view = new WWRechargeLogView(getActivity());
                    break;
                case 1:
                    view = new WWConsumeLogView(getActivity());
                    break;
                default:
                    break;
            }
            return view;
        }
    }
}
