package com.fanwe.catchdoll.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/12/7.
 */

public class WWListViewForScrollView extends ListView
{
    public WWListViewForScrollView(Context context)
    {
        super(context);
    }

    public WWListViewForScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WWListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
