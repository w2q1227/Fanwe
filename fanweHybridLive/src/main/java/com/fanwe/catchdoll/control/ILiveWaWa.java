package com.fanwe.catchdoll.control;

/**
 * Created by Administrator on 2017/11/14.
 */

public interface ILiveWaWa
{
    //控制方向选项
    enum DIRECTION{
        LEFT, UP,RIGHT, DOWN
    }

    void setSocketAddress(String address);

    void registerWaWaCallback(ILiveWaWaCallback listener);

    void joinQueue(String data,int gameId);

    void startGame();

    void drag(DIRECTION direction,boolean isPress);

    void doCatch();

    void roundTimeFinish();

    void startTimeOutWorker();

    void quitGame();

    void onDestroy();

    int getGameId();
}
