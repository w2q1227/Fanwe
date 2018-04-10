package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.util.PressedStateListDrawable;
import com.fanwe.live.R;

/**
 * <抓取失败>
 * Created by wang on 2017/11/16 08:55.
 */

public class WWCatchFailView extends WWBaseCatchResultView
{
    public WWCatchFailView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWCatchFailView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWCatchFailView(Context context)
    {
        super(context);
        init();
    }

    private TextView tv_again;
    private TextView tv_close;

    private void init()
    {
        setContentView(R.layout.ww_view_catch_fail);
        tv_again = (TextView) findViewById(R.id.tv_again);
        tv_close = (TextView) findViewById(R.id.tv_close);
        tv_again.setOnClickListener(this);
        tv_close.setOnClickListener(this);

        tv_again.setBackgroundDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_bg_play_again));
    }

    @Override
    protected TextView getTvCountDown()
    {
        return (TextView) findViewById(R.id.tv_count_down);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_again)
        {
            playAgain();
        } else if (v == tv_close)
        {
            close();
        }
    }
}
