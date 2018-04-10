package com.fanwe.catchdoll.appview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.catchdoll.control.ILiveWaWa;
import com.fanwe.catchdoll.util.PressedStateListDrawable;
import com.fanwe.lib.player.SDMediaPlayer;
import com.fanwe.live.R;

/**
 * Created by Administrator on 2017/11/14.
 */

public class WWLiveControlPanel extends WWBaseAppView implements
        View.OnTouchListener
{

    private ImageView btnLeft, btnTop, btnRight, btnBottom, btnCatch;

    private ILiveWaWa iLiveWaWa;

    private SDMediaPlayer mPlayer;

    public WWLiveControlPanel(Context context)
    {
        super(context);
        init();
    }

    public void setWaWaSDK(ILiveWaWa waWaSDK)
    {
        this.iLiveWaWa = waWaSDK;
    }

    public void setSoundPlayer(SDMediaPlayer player)
    {
        mPlayer = player;
    }

    private void init()
    {
        setContentView(R.layout.ww_view_live_control_panel);

        btnLeft = (ImageView) findViewById(R.id.btn_left);
        btnTop = (ImageView) findViewById(R.id.btn_top);
        btnRight = (ImageView) findViewById(R.id.btn_right);
        btnBottom = (ImageView) findViewById(R.id.btn_bottom);
        btnCatch = (ImageView) findViewById(R.id.btn_catch);


        btnLeft.setTag(ILiveWaWa.DIRECTION.LEFT);
        btnTop.setTag(ILiveWaWa.DIRECTION.UP);
        btnRight.setTag(ILiveWaWa.DIRECTION.RIGHT);
        btnBottom.setTag(ILiveWaWa.DIRECTION.DOWN);

        btnLeft.setOnTouchListener(this);
        btnTop.setOnTouchListener(this);
        btnRight.setOnTouchListener(this);
        btnBottom.setOnTouchListener(this);
        btnCatch.setOnClickListener(this);

        btnLeft.setImageDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_left));
        btnTop.setImageDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_top));
        btnRight.setImageDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_right));
        btnBottom.setImageDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_bottom));
        btnCatch.setImageDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_catch));

    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == btnCatch)
        {
            if (mPlayer != null && !mPlayer.isPlaying())
            {
                mPlayer.start();
            }
            if (iLiveWaWa != null)
                iLiveWaWa.doCatch();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (iLiveWaWa == null) return false;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (mPlayer != null && !mPlayer.isPlaying())
                {
                    mPlayer.start();
                }
                iLiveWaWa.drag((ILiveWaWa.DIRECTION) v.getTag(), true);
                v.setPressed(true);
                break;
            case MotionEvent.ACTION_UP:
                iLiveWaWa.drag((ILiveWaWa.DIRECTION) v.getTag(), false);
                v.setPressed(false);
                break;
        }
        return true;
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        super.onActivityDestroyed(activity);
        if (mPlayer != null)
            mPlayer.release();
    }
}
