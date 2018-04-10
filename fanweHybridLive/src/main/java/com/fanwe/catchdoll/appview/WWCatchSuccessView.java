package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.util.PressedStateListDrawable;
import com.fanwe.live.R;

/**
 * <抓取成功>
 * Created by wang on 2017/11/16 08:53.
 */

public class WWCatchSuccessView extends WWBaseCatchResultView
{
    private int mDollId;
    public WWCatchSuccessView(Context context)
    {
        super(context);
        init();
    }

    public WWCatchSuccessView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWCatchSuccessView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private TextView tv_again;
    private TextView tv_close;
    private TextView tv_receive;

    private void init()
    {
        setContentView(R.layout.ww_view_catch_success);
        tv_again = (TextView) findViewById(R.id.tv_again);
        tv_close = (TextView) findViewById(R.id.tv_close);
        tv_receive = (TextView) findViewById(R.id.tv_receive);
        tv_again.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        tv_receive.setOnClickListener(this);

        tv_receive.setBackgroundDrawable(PressedStateListDrawable.get(R.drawable.ww_bg_receive));
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
        } else if (v == tv_receive)
        {
            receive();
        }
    }


}
