package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

import io.reactivex.annotations.NonNull;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/16 10:13.
 */

public abstract class WWBaseCatchResultView extends WWBaseCountDownView
{
    public WWBaseCatchResultView(Context context)
    {
        super(context);
        init();
    }

    public WWBaseCatchResultView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWBaseCatchResultView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private Callback mCallback;

    private void init()
    {
        setConsumeTouchEvent(true);
    }

    /**
     * 关闭
     */
    protected void close()
    {
        stopCountDown();
        if (mCallback != null)
        {
            mCallback.onClickClose();
        }
    }

    /**
     * 再玩一次
     */
    protected void playAgain()
    {
        stopCountDown();
        if (mCallback != null)
        {
            mCallback.onClickPlayAgain();
        }
    }

    /**
     * 立即领取
     */
    protected void receive()
    {
        stopCountDown();
        if (mCallback != null)
        {
            mCallback.onClickReceive();
        }
    }

    @Override
    protected void onCount(long second)
    {
        getTvCountDown().setText(String.format("倒计时 %d秒", second));
    }

    @NonNull
    protected abstract TextView getTvCountDown();

    public void setCallBack(Callback callBack)
    {
        super.setCallBack(callBack);
        this.mCallback = callBack;
    }

    public interface Callback extends WWBaseCountDownView.Callback
    {
        /**
         * 关闭
         */
        void onClickClose();

        /**
         * 再次挑战
         */
        void onClickPlayAgain();

        /**
         * 立即领取
         */
        void onClickReceive();
    }

}
