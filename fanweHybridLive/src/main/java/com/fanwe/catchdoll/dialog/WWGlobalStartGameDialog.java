package com.fanwe.catchdoll.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWLiveLayoutActivity;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEGiveUpQueue;
import com.fanwe.catchdoll.model.WWAppJoinQueueModel;
import com.fanwe.catchdoll.model.WWCustomMsgQueueNotify;
import com.fanwe.catchdoll.model.WWReserveInfo;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.lib.looper.SDCountDownTimer;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.MsgModel;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by Administrator on 2017/12/7.
 */

public class WWGlobalStartGameDialog extends SDDialogBase
{

    private TextView tvSec;
    private TextView tvCancel;
    private TextView tvConfirm;

    private String roomId;
    private SDCountDownTimer timer;

    private boolean isExpired = false;
    private boolean isGiveUp = false;

    public WWGlobalStartGameDialog(final Activity activity)
    {
        super(activity);

        setContentView(R.layout.ww_dlg_global_start_game);

        tvSec = (TextView) findViewById(R.id.tv_sec);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);

        setFullScreen();

        tvCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isGiveUp = true;
                dismiss();
            }
        });
    }

    public void updateReserve()
    {
        final WWReserveInfo reserveInfo = LiveInformation.getInstance().getCurrentReserve();

        roomId = reserveInfo.getRoom_id();

        long seconds = reserveInfo.getEnd_time() - reserveInfo.getBegin_time();
        if (System.currentTimeMillis() / 1000 - reserveInfo.getEnd_time() > seconds * 5)
        {
            isExpired = true;
        }
        startCountDown(seconds);

        tvConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getOwnerActivity().startActivity(WWLiveLayoutActivity.quickStartIntent(Integer.valueOf(reserveInfo.getRoom_id()), String.valueOf(reserveInfo.getRoom_id()), getOwnerActivity()));
                isGiveUp = false;
                dismiss();
            }
        });
    }

    private void startCountDown(long seconds)
    {

        if (timer != null)
        {
            timer.stop();
            timer = null;
        }

        timer = new SDCountDownTimer();
        timer.setCallback(new SDCountDownTimer.Callback()
        {
            @Override
            public void onTick(long l)
            {
                tvSec.setText(String.format("%ds", l / 1000));
                if (getOwnerActivity().isFinishing())
                {
                    dismiss();
                }
            }

            @Override
            public void onFinish()
            {
                isGiveUp = true;
                tvSec.setText(String.valueOf("0s"));
                dismiss();
            }
        });
        timer.start(seconds * 1000, 1000);
    }

    private void giveUp()
    {
        WWCommonInterface.requestQuitQueue(SDTypeParseUtil.getInt(roomId), new AppRequestCallback<WWAppJoinQueueModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                SDEventManager.post(new WWEGiveUpQueue());
            }
        });
    }

    @Override
    public void show()
    {
        if (isExpired)
        {
            dismiss();
        } else
        {
            LiveInformation.getInstance().setReserveConfirm(true);
            super.show();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        if (isGiveUp)
        {
            giveUp();
        }
        if (timer != null)
        {
            timer.stop();
            timer = null;
        }
        LiveInformation.getInstance().setReserveConfirm(false);
        super.onDismiss(dialog);
    }
}
