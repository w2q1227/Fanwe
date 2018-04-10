package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.live.R;

/**
 * <空布局>
 * Created by wwb on 2017/12/7 10:21.
 */

public class WWEmptyView extends WWBaseAppView
{
    public WWEmptyView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public WWEmptyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WWEmptyView(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.ww_view_empty;
    }

    public TextView getTextHint()
    {
        return (TextView) findViewById(R.id.tv_hint);
    }

    public ImageView getImageHint()
    {
        return (ImageView) findViewById(R.id.iv_hint);
    }
}
