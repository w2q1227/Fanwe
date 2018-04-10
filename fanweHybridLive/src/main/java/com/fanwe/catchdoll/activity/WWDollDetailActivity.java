package com.fanwe.catchdoll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.catchdoll.appview.WWDollDetailWebView;
import com.fanwe.catchdoll.appview.WWGrabDollMasterView;
import com.fanwe.catchdoll.appview.WWGrabDollRecordView;
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
 * des:娃娃详情
 * Created by yangwb
 * on 2017/12/7.
 */

public class WWDollDetailActivity extends BaseTitleActivity
{
    /**
     * 房间room_id(int)
     */
    public static final String EXTRA_ROOM_ID = "extra_room_id";
    /**
     * 娃娃详情url(String)
     */
    public static final String EXTRA_URL = "extra_url";

    private LiveTabUnderline tab_doll_detail;
    private LiveTabUnderline tab_grab_record;
    private LiveTabUnderline tab_grab_master;
    private SDViewPager vpg_content;

    private SDSelectViewManager<LiveTabUnderline> mSelectManager = new SDSelectViewManager<>();

    private int mRoom_id;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_doll_detail);

        initView();
        initData();
    }

    private void initView()
    {
        tab_doll_detail = (LiveTabUnderline) findViewById(R.id.tab_doll_detail);
        tab_grab_record = (LiveTabUnderline) findViewById(R.id.tab_grab_record);
        tab_grab_master = (LiveTabUnderline) findViewById(R.id.tab_grab_master);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
    }

    private void initData()
    {
        initTitle();
        getIntentData();
        initViewPager();
        initTab();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("娃娃详情");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    private void getIntentData()
    {
        mRoom_id = getIntent().getIntExtra(EXTRA_ROOM_ID, 0);
        mUrl = getIntent().getStringExtra(EXTRA_URL);
    }

    private void initViewPager()
    {
        List<String> listModel = new ArrayList<>();
        listModel.add("");
        listModel.add("");
        listModel.add("");

        vpg_content.setOffscreenPageLimit(2);
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
        changeLiveTabUnderline(tab_doll_detail, "娃娃详情");
        changeLiveTabUnderline(tab_grab_record, "最近抓中记录");
        changeLiveTabUnderline(tab_grab_master, "抓娃娃达人");

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

        mSelectManager.setItems(new LiveTabUnderline[]{tab_doll_detail, tab_grab_record, tab_grab_master});
        mSelectManager.performClick(0);
    }

    private void changeLiveTabUnderline(LiveTabUnderline tabUnderline, String title)
    {
        tabUnderline.getTextViewTitle().setText(title);
        tabUnderline.configViewUnderline().setWidthNormal(SDViewUtil.dp2px(50)).setWidthSelected(SDViewUtil.dp2px(50));
        tabUnderline.configTextViewTitle().setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));
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
            case 2:
                vpg_content.setCurrentItem(2);
                break;
            default:
                break;
        }
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
            BaseAppView view = null;
            switch (position)
            {
                case 0:
                    WWDollDetailWebView webView = new WWDollDetailWebView(getActivity());
                    webView.setUrl(mUrl);
                    view = webView;
                    break;
                case 1:
                    WWGrabDollRecordView recordView = new WWGrabDollRecordView(getActivity());
                    recordView.setData(mRoom_id);
                    view = recordView;
                    break;
                case 2:
                    WWGrabDollMasterView masterView = new WWGrabDollMasterView(getActivity());
                    masterView.setData(mRoom_id);
                    view = masterView;
                    break;
                default:
                    break;
            }
            return view;
        }
    }
}
