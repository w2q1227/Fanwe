package com.fanwe.catchdoll.control;

import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/14.
 */

public interface ILiveRoom
{

    boolean isInRoom();

    void setCallback(Callback callback);

    void joinRoom(int roomId);

    void quitRoom();

    void onPause();

    void onResume();

    void onDestroy();

    interface Callback
    {
        void onJoinRoomSuccess();
        void onJoinRoomFailure();
        void onTraceQuality(boolean isTrace);
    }
}
