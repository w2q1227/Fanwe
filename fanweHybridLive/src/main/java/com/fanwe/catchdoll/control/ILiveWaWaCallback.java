package com.fanwe.catchdoll.control;

/**
 * Created by Administrator on 2017/11/14.
 */

public interface ILiveWaWaCallback
{

    void onQueue(int queueSize);

    void onReady();

    void onResult(int gameId,boolean isSuccess);

    void onTime(int time);

    void tryEndGame(int gameId);

    WWLiveView.VideoPlayer getCurrentPlayer();
}
