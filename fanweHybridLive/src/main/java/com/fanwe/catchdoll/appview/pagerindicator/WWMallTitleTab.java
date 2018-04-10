package com.fanwe.catchdoll.appview.pagerindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.lib.viewpager.indicator.IPagerIndicatorItem;
import com.fanwe.lib.viewpager.indicator.model.PositionData;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.WWMallTabTitleModel;
import com.fanwe.live.view.LiveTabUnderline;

/**
 * <商城>
 * Created by wwb on 2017/12/26 10:44.
 */

public class WWMallTitleTab extends LiveTabUnderline implements IPagerIndicatorItem
{
    public WWMallTitleTab(Context context)
    {
        super(context);
        init();
    }

    public WWMallTitleTab(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private WWMallTabTitleModel mData;


    private void init()
    {
        setMinimumWidth(SDViewUtil.dp2px(60));
        int padding = SDViewUtil.dp2px(10);
        getTextViewTitle().setPadding(padding, 0, padding, 0);

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


    public void setData(WWMallTabTitleModel data)
    {
        mData = data;
        if (data != null)
        {
            getTextViewTitle().setText(data.getTitle());
        }
    }

    public WWMallTabTitleModel getData()
    {
        return mData;
    }


    @Override
    public void onSelectedChanged(boolean b)
    {
        setSelected(b);
    }

    @Override
    public void onShowPercent(float v, boolean b, boolean b1)
    {

    }

    @Override
    public PositionData getPositionData()
    {
        return null;
    }
}
