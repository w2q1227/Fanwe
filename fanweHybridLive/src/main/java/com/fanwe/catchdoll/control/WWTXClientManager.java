package com.fanwe.catchdoll.control;

import com.fanwe.catchdoll.event.WWLiveException;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.live.LiveInformation;
import com.tencent.wawasdk.TXWawaCallBack;
import com.tencent.wawasdk.TXWawaPlayer;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018/1/15.
 */

public class WWTXClientManager
{

    private static WWTXClientManager sInstance;

    public static WWTXClientManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (WWTXClientManager.class)
            {
                if (sInstance == null)
                {
                    sInstance = new WWTXClientManager();
                }
            }
        }
        return sInstance;
    }

    private TXWawaPlayer client;

    private ClientListener mListener;

    public interface ClientListener{

        void onReady();

        void onResult(boolean result);

        void onClose();
    }

    public void registerClientListener(ClientListener listener)
    {
        this.mListener = listener;
    }

    private TXWawaPlayer getClient()
    {
        if (client == null)
        {
            client = new TXWawaPlayer(new TXWawaPlayer.PlayerListener()
            {
                @Override
                public void OnWait(int queueSize)
                {
                    logListener("OnWait -> [queueSize = " + queueSize + "]");
                }

                @Override
                public void OnReady(int state)
                {
                    if (mListener != null)
                    {
                        mListener.onReady();
                    }
                    logListener("OnReady -> [state = " + state + "]");
                }

                @Override
                public void OnState(int state)
                {
                    logListener("OnState -> [state = " + state + "]");
                }

                @Override
                public void OnResult(boolean result)
                {
                    if (mListener != null)
                    {
                        mListener.onResult(result);
                    }
                    logListener("OnResult -> [result = " + result + "]");
                }

                @Override
                public void OnTime(int time)
                {
                    logListener("OnTime -> [time = " + time + "]");
                }

                @Override
                public void OnClose(int errtype, int errcode, String reason)
                {
                    if (mListener != null)
                    {
                        mListener.onClose();
                    }
                    logListener("OnClose -> [errtype = " + errtype + "][errcode = " + errcode + "][reason = " + reason + "]");
                }


                @Override
                public void OnControlRTT(int data)
                {
                }

            });
        }
        return client;
    }

    public void joinQueue(String url, int gameId,TXWawaCallBack callBack)
    {
        getClient().startQueue(String.valueOf(gameId), url, callBack);
    }

    public void startGame(TXWawaCallBack callBack)
    {
        getClient().startGame(callBack);
    }

    public void quitGame()
    {
        getClient().quitGame();
    }

    public void left(boolean isPress)
    {
        if (isPress)
        {
            getClient().controlClaw(TXWawaPlayer.CTL_LEFT_START);
        } else
        {
            getClient().controlClaw(TXWawaPlayer.CTL_LEFT_END);
        }
    }

    public void right(boolean isPress)
    {
        if (isPress)
        {
            getClient().controlClaw(TXWawaPlayer.CTL_RIGHT_START);
        } else
        {
            getClient().controlClaw(TXWawaPlayer.CTL_RIGHT_END);
        }
    }

    public void forward(boolean isPress)
    {
        if (isPress)
        {
            getClient().controlClaw(TXWawaPlayer.CTL_FORWARD_START);
        } else
        {
            getClient().controlClaw(TXWawaPlayer.CTL_FORWARD_END);
        }
    }

    public void backwards(boolean isPress)
    {
        if (isPress)
        {
            getClient().controlClaw(TXWawaPlayer.CTL_BACKWARD_START);
        } else
        {
            getClient().controlClaw(TXWawaPlayer.CTL_BACKWARD_END);
        }
    }


    public void doCatch()
    {
        getClient().controlClaw(TXWawaPlayer.CTL_CATCH);
    }

    public void onDestroy()
    {
        client = null;
        mListener = null;
    }

    private void logListener(String msg)
    {
        String log = "Client -> " + msg;
        log(log);
    }

    private void log(String log)
    {
        WWLogHelper.getInstance().log("[TX_CLIENT]" + log);
    }


}
