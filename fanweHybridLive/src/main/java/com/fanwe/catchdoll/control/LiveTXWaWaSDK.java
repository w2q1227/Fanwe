package com.fanwe.catchdoll.control;

import com.fanwe.catchdoll.event.WWLiveException;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.live.LiveInformation;
import com.sunday.eventbus.SDEventManager;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.library.utils.LogUtil;
import com.tencent.wawasdk.TXWawaCallBack;
import com.tencent.wawasdk.TXWawaLog;
import com.tencent.wawasdk.TXWawaPlayer;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/11/14.
 */

public class LiveTXWaWaSDK implements ILiveWaWa
{


    private int gameId;

    private SDTimer timeoutWorker;

    private boolean isInGame = false;

    private ILiveWaWaCallback mCallback;

    @Override
    public void registerWaWaCallback(ILiveWaWaCallback callback)
    {
        mCallback = callback;
    }

    public LiveTXWaWaSDK()
    {
        WWTXClientManager.getInstance().registerClientListener(new WWTXClientManager.ClientListener()
        {
            @Override
            public void onReady()
            {
                if (mCallback != null)
                {
                    mCallback.onReady();
                }
            }

            @Override
            public void onResult(boolean result)
            {
                if (mCallback != null)
                {
                    mCallback.onResult(gameId, result);
                }
            }

            @Override
            public void onClose()
            {
                if (isInGame)
                {
                    logProcess("EXCEPTION: closed in game");
                    SDEventManager.post(new WWLiveException(WWLiveException.CLOSED_IN_GAME));
                }
            }
        });

        TXWawaLog.setHandler(new TXWawaLog.LogHandler()
        {
            @Override
            public void v(String tag, String msg)
            {
                WWLogHelper.getInstance().log(tag + msg);
            }

            @Override
            public void d(String tag, String msg)
            {
                WWLogHelper.getInstance().log(tag + msg);
            }

            @Override
            public void i(String tag, String msg)
            {
                WWLogHelper.getInstance().log(tag + msg);
            }

            @Override
            public void w(String tag, String msg)
            {
                WWLogHelper.getInstance().log(tag + msg);
            }

            @Override
            public void e(String tag, String msg)
            {
                WWLogHelper.getInstance().log(tag + msg);
            }
        });
        timeoutWorker = new SDTimer();
    }

    @Override
    public void setSocketAddress(String address)
    {

    }

    @Override
    public void joinQueue(String url, int gameId)
    {
        if (isInGame) return;
        isInGame = true;

        logProcess("joinQueue -> [url = " + url + "][gameId = " + gameId + "]" + this.hashCode());
        this.gameId = gameId;
        WWTXClientManager.getInstance().joinQueue(url, gameId, new TXWawaCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
                logProcess("joinQueue onSuccess -> [data = " + data + "]");
            }

            @Override
            public void onError(int errCode, String errMsg)
            {
                logProcess("joinQueue onError -> [errCode = " + errCode + "][errMsg = " + errMsg + "]");
                SDEventManager.post(new WWLiveException(WWLiveException.JOIN_QUEUE_FAILED));
            }
        });
    }

    @Override
    public void startGame()
    {
        logProcess("startGame");
        WWTXClientManager.getInstance().startGame(new TXWawaCallBack<Integer>()
        {
            @Override
            public void onSuccess(Integer coins)
            {
                logProcess("startGame onSuccess -> [coins = " + coins + "]");
            }

            @Override
            public void onError(int errCode, String errMsg)
            {
                EventBus.getDefault().post(new WWLiveException(WWLiveException.START_GAME_FAILED));
                logProcess("startGame onError -> [errCode = " + errCode + "][errMsg = " + errMsg + "]");
            }
        });
    }

    @Override
    public void drag(final DIRECTION direction, final boolean isPress)
    {
        if (mCallback == null) return;

        int position = 0;
        if (mCallback.getCurrentPlayer() == WWLiveView.VideoPlayer.MAIN)
        {
            position = LiveInformation.getInstance().getMainPlayerConfig().getCameraPosition();
        } else if (mCallback.getCurrentPlayer() == WWLiveView.VideoPlayer.SUB)
        {
            position = LiveInformation.getInstance().getSubPlayerConfig().getCameraPosition();
        }
        logPlayer(mCallback.getCurrentPlayer(), position);
        DirectionHelper.translate(direction, position, new DirectionHelper.Callback()
        {
            @Override
            public void left()
            {
                logControl(direction + " -> left", isPress);
                WWTXClientManager.getInstance().left(isPress);
            }

            @Override
            public void right()
            {
                logControl(direction + " -> right", isPress);
                WWTXClientManager.getInstance().right(isPress);
            }

            @Override
            public void forward()
            {
                logControl(direction + " -> forward", isPress);
                WWTXClientManager.getInstance().forward(isPress);
            }

            @Override
            public void backwards()
            {
                logControl(direction + " -> backwards", isPress);
                WWTXClientManager.getInstance().backwards(isPress);
            }
        });

    }

    @Override
    public int getGameId()
    {
        return gameId;
    }

    @Override
    public void doCatch()
    {
        logControl("doCatch", true);
        WWTXClientManager.getInstance().doCatch();
    }

    @Override
    public void roundTimeFinish()
    {
        logProcess("round time finish");
        doCatch();
        startTimeOutWorker();
    }

    @Override
    public void startTimeOutWorker()
    {
        logProcess("start timeout worker");
        if (isInGame)
        {
            logTimeoutWorker("start work");
            timeoutWorker.startWork(15000, new SDTimer.SDTimerListener()
            {
                @Override
                public void onWork()
                {
                }

                @Override
                public void onWorkMain()
                {
                    logTimeoutWorker("on working");
                    if (isInGame && mCallback != null)
                    {
                        logTimeoutWorker("try to end game");
                        mCallback.tryEndGame(gameId);
                    }
                }
            });
        }
    }

    @Override
    public void quitGame()
    {
        isInGame = false;
        WWTXClientManager.getInstance().quitGame();
        timeoutWorker.stopWork();
        logProcess("quit game");
    }

    @Override
    public void onDestroy()
    {
        mCallback = null;

        WWTXClientManager.getInstance().onDestroy();

        if (timeoutWorker != null)
            timeoutWorker.stopWork();
        timeoutWorker = null;
        logProcess("on destroy");
    }

    private void logProcess(String msg)
    {
        String log = "Process -> " + msg;
        log(log);
    }

    private String playerLog;

    private void logPlayer(WWLiveView.VideoPlayer player, int position)
    {
        String newLog = "Player -> [player = " + player + "][position = " + position + "]";
        if (!newLog.equals(playerLog))
        {
            playerLog = newLog;
            log(playerLog);
        }
    }

    private void logControl(String msg, boolean isPress)
    {
        if (!isPress) return;
        String log = "TXControl -> " + msg;
        log(log);
    }

    private void logTimeoutWorker(String msg)
    {
        String log = "Timeout -> " + msg;
        log(log);
    }

    private void logHandler(String tag, String msg)
    {
        String log = tag + " -> [msg = " + msg + "]";
        log(log);
    }

    private void log(String log)
    {
        WWLogHelper.getInstance().log("[TX_SDK]" + log);
    }
}
