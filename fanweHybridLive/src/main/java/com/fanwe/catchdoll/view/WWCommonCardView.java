package com.fanwe.catchdoll.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.fanwe.library.utils.SDViewUtil;

/**
 * Created by Administrator on 2017/12/4.
 */
public class WWCommonCardView extends CardView
{
    public WWCommonCardView(Context context)
    {
        super(context);
        init();
    }

    public WWCommonCardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWCommonCardView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setRadius(SDViewUtil.dp2px(5));
    }
}
