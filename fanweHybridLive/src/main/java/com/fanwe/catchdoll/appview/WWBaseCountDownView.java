package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class WWBaseCountDownView extends WWBaseAppView
{

    private CountDownTimer mCountDownTimer;
    private Callback mCallback;

    public WWBaseCountDownView(Context context)
    {
        super(context);
    }

    public WWBaseCountDownView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WWBaseCountDownView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public void setCallBack(WWBaseCountDownView.Callback callBack)
    {
        this.mCallback = callBack;
    }

    /**
     * 开始倒计时
     *
     * @param second 单位：秒
     */
    public void startCountDown(int second)
    {
        if (second <= 0)
        {
            return;
        }
        stopCountDown(); //如果原先有倒计时，先停止
        if (mCountDownTimer == null)
        {
            mCountDownTimer = new CountDownTimer(second * 1000, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                   onCount(millisUntilFinished / 1000);
                }

                @Override
                public void onFinish()
                {
                    onTick(0);
                    if (mCallback != null)
                    {
                        mCallback.onCountDownFinish(WWBaseCountDownView.this);
                    }
                }
            };
            mCountDownTimer.start();
        }
    }

    protected abstract void onCount(long second);

    /**
     * 停止倒计时
     */
    public void stopCountDown()
    {
        if (mCountDownTimer != null)
        {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stopCountDown();
    }

    public interface Callback
    {
        /**
         * 倒计时结束
         */
        void onCountDownFinish(View view);
    }
}
