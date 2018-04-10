package com.fanwe.catchdoll.appview.main;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fanwe.catchdoll.appview.WWTabMainMenuView;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;

/**
 * 首页底部菜单栏
 * Created by LianCP on 2017/11/13.
 */
public class WWMainBottomNavigationView extends FrameLayout implements View.OnClickListener
{
    public WWMainBottomNavigationView(Context context)
    {
        super(context);
        init();
    }

    public WWMainBottomNavigationView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public static final int INDEX_HOME = 0;
    public static final int INDEX_MY_DOLL = 1;
    public static final int INDEX_SCORE_SHOP = 2;
    public static final int INDEX_FIND = 3;
    public static final int INDEX_ME = 4;

    private LinearLayout mTabRecharge, ll_tab_mall;
    private WWTabMainMenuView mTabHome, mTabMyDoll, mTabShop, mTabFind, mTabMe;

    private SDSelectViewManager<WWTabMainMenuView> mSelectionManager = new SDSelectViewManager<>();

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.ww_view_main_tab, this, true);
        mTabRecharge = (LinearLayout) findViewById(R.id.ll_tab_recharge);
        ll_tab_mall = (LinearLayout) findViewById(R.id.ll_tab_mall);
        mTabHome = (WWTabMainMenuView) findViewById(R.id.view_tab_live);
        mTabMyDoll = (WWTabMainMenuView) findViewById(R.id.view_tab_my_doll);
        mTabShop = (WWTabMainMenuView) findViewById(R.id.view_tab_score_shop);
        mTabFind = (WWTabMainMenuView) findViewById(R.id.view_tab_find);
        mTabMe = (WWTabMainMenuView) findViewById(R.id.view_tab_me);


        mTabRecharge.setOnClickListener(this);

        initTabs();
        initSelectManager();
    }

    private void initTabs()
    {
        changeBottomNavigtion(mTabHome, R.drawable.ww_ic_tab_home_normal,
                R.drawable.ww_ic_tab_home_selected, "首页");
        changeBottomNavigtion(mTabMyDoll, R.drawable.ww_ic_tab_my_doll_normal,
                R.drawable.ww_ic_tab_my_doll_selected, "我的娃");
        changeBottomNavigtion(mTabShop, R.drawable.ww_ic_tab_mall_normal,
                R.drawable.ww_ic_tab_mall_selected, "商城");
        changeBottomNavigtion(mTabFind, R.drawable.ww_ic_tab_find_normal,
                R.drawable.ww_ic_tab_find_selected, "发现");
        changeBottomNavigtion(mTabMe, R.drawable.ww_ic_tab_me_normal,
                R.drawable.ww_ic_tab_me_selected, "个人中心");
    }

    public void changeBottomNavigtion(WWTabMainMenuView tabMainMenuView, int drawableNormal, int drawableSelect, String textName)
    {
        tabMainMenuView.configImage()
                .setImageResIdNormal(drawableNormal)
                .setImageResIdSelected(drawableSelect)
                .setSelected(false);
        tabMainMenuView.getTabNameTextView().setText(textName);
        tabMainMenuView.configTextView()
                .setTextColorNormal(Color.parseColor("#999999"))
                .setTextColorSelected(Color.parseColor("#ff6a8d"))
                .setSelected(false);
    }

    private void initSelectManager()
    {
        mSelectionManager.setReSelectCallback(new SDSelectManager.ReSelectCallback<WWTabMainMenuView>()
        {
            @Override
            public void onSelected(int index, WWTabMainMenuView item)
            {
                getCallback().onTabReselected(index);
            }
        });

        mSelectionManager.addSelectCallback(new SDSelectViewManager.SelectCallback<WWTabMainMenuView>()
        {
            @Override
            public void onNormal(int index, WWTabMainMenuView item)
            {
            }

            @Override
            public void onSelected(int index, WWTabMainMenuView item)
            {
                getCallback().onTabSelected(index);
            }
        });

        mSelectionManager.setItems(new WWTabMainMenuView[]{mTabHome, mTabMyDoll, mTabShop, mTabFind, mTabMe});
    }

    public void selectTab(int index)
    {
        mSelectionManager.performClick(index);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mTabRecharge)
        {
            getCallback().onClickRecharge(v);
        }
    }

    private Callback mCallback;

    public void setCallback(Callback callback)
    {
        this.mCallback = callback;
    }

    private Callback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new Callback()
            {
                @Override
                public void onTabSelected(int index)
                {
                }

                @Override
                public void onTabReselected(int index)
                {
                }

                @Override
                public void onClickRecharge(View view)
                {
                }
            };
        }
        return mCallback;
    }

    public interface Callback
    {
        void onTabSelected(int index);

        void onTabReselected(int index);

        void onClickRecharge(View view);
    }

    public void showOrHideTabRecharge(boolean isShow)
    {
        if (isShow)
        {
            SDViewUtil.setVisible(mTabRecharge);
        } else
        {
            SDViewUtil.setGone(mTabRecharge);
        }
    }


    public void showOrHideTabShop(boolean isShow)
    {
        if (isShow)
        {
            SDViewUtil.setVisible(ll_tab_mall);
        } else
        {
            SDViewUtil.setGone(ll_tab_mall);
        }
    }


}
