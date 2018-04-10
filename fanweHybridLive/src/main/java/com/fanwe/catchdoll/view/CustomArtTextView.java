package com.fanwe.catchdoll.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.view.SDBorderTextView;

/**
 * 自定义艺术字
 * Created by LianCP on 2017/11/15.
 */
public class CustomArtTextView extends SDBorderTextView
{
    public CustomArtTextView(Context context)
    {
        super(context);
    }

    public CustomArtTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomArtTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(AttributeSet attrs)
    {
        super.init(attrs);

        if (getBorderColor() == 0)
        {
            setBorderColor(Color.BLACK);
        }
        if (getBorderWidth() <= 0)
        {
            setBorderWidth(SDViewUtil.dp2px(1));
        }
    }
}
