package com.fanwe.catchdoll.appview.pagerindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.pagerindicator.LiveHomeTitleTab;

/**
 * Created by Administrator on 2017/11/24.
 */

public class WWHomeTitleTab extends LiveHomeTitleTab
{
    public WWHomeTitleTab(Context context)
    {
        super(context);
        init();
    }

    public WWHomeTitleTab(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setMinimumWidth(SDViewUtil.dp2px(60));

        configTextViewTitle()
                .setTextColorResIdNormal(R.color.res_text_gray_s)
                .setTextColorResIdSelected(R.color.home_tab_select)
                .setSelected(false);

        configViewUnderline()
                .setBackgroundColorResIdNormal(R.color.res_text_gray_s)
                .setBackgroundColorResIdSelected(R.color.home_tab_select)
                .setVisibilityNormal(View.INVISIBLE)
                .setVisibilitySelected(View.VISIBLE)
                .setSelected(false);
    }
}
