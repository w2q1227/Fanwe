package com.fanwe.catchdoll.control;

import android.view.ViewGroup;

import com.fanwe.catchdoll.model.WWQualityData;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.lib.looper.impl.SDSimpleLooper;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.LiveInformation;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.adapter.CommonConstants;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.core.ILiveRoomOption;
import com.tencent.ilivesdk.tools.quality.ILiveQualityData;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.ilivesdk.view.AVVideoView;
import com.tencent.ilivesdk.view.VideoListener;

import java.util.logging.Level;

/**
 * Created by Administrator on 2017/11/14.
 */

public class LiveRoomSDK implements ILiveRoom
{

    private boolean isInRoom = false;
    private ILiveRoom.Callback callback;

    public void setCallback(ILiveRoom.Callback callback)
    {
        this.callback = callback;
    }

    @Override
    public boolean isInRoom()
    {
        return isInRoom;
    }

    @Override
    public void onPause()
    {
        ILiveRoomManager.getInstance().onPause();
    }

    @Override
    public void onResume()
    {
        ILiveRoomManager.getInstance().onResume();
    }

    @Override
    public void joinRoom(int roomId)
    {
        if (isInRoom) return;
        logProcess("joinRoom -> [roomId = " + roomId + "]");
        ILiveRoomOption memberOption = new ILiveRoomOption("")
                .autoCamera(false)
                .imsupport(false)
                .controlRole("Guest") //角色设置
                .authBits(CommonConstants.Const_Auth_Host)
                .videoRecvMode(CommonConstants.Const_AutoRecv_Camera)
                .autoMic(false);

        ILiveRoomManager.getInstance().joinRoom(roomId, memberOption, new ILiveCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
                isInRoom = true;
                ILiveRoomManager.getInstance().enableSpeaker(false);
                ILiveRoomManager.getInstance().enableMic(false);
                if (callback != null)
                {
                    callback.onJoinRoomSuccess();
                    callback.onTraceQuality(true);
                }
                logProcess("joinRoom onSuccess -> [data = " + data + "]");
            }

            @Override
            public void onError(String module, int errCode, String errMsg)
            {
                isInRoom = false;
                if (errCode == 1003)
                {
                    isInRoom = true;
                    callback.onJoinRoomSuccess();
                }

                if (callback != null)
                {
                    callback.onJoinRoomFailure();
                }
                logProcess("joinRoom onError -> [module = " + module + "][errCode = " + errCode + "][errMsg = " + errMsg + "]");
            }
        });
    }

    @Override
    public void quitRoom()
    {
        logProcess("quitRoom");
        if (isInRoom)
            ILiveRoomManager.getInstance().quitRoom(new ILiveCallBack()
            {
                @Override
                public void onSuccess(Object data)
                {
                    isInRoom = false;
                    logProcess("quitRoom onSuccess -> [data = " + data + "]");
                }

                @Override
                public void onError(String module, int errCode, String errMsg)
                {
                    isInRoom = false;
                    logProcess("quitRoom onError -> [module = " + module + "][errCode = " + errCode + "][errMsg = " + errMsg + "]");
                }
             });
        if (callback != null)
        {
            callback.onTraceQuality(false);
        }
    }

    @Override
    public void onDestroy()
    {
        logProcess("onDestroy");
        quitRoom();
        ILiveRoomManager.getInstance().onDestory();
    }


    private void logProcess(String msg)
    {
        String log = "Process -> " + msg;
        log(log);
    }

    private void log(String log)
    {
        WWLogHelper.getInstance().log("[Room]" + log);
    }
}
