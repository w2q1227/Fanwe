package com.fanwe.catchdoll.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.lib.looper.impl.SDSimpleLooper;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.LiveInformation;


import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Administrator on 2017/11/27.
 */

public class LiveFWWaWaSDK implements ILiveWaWa
{

    private Socket socket;

    private ILiveWaWaCallback callback;

    private String permission = null;

    private int gameId;

    private boolean isInGame = false;

    private SDTimer timeoutWorker;
    private SDSimpleLooper controlLooper;

    private static final int MAX_HANDLE_RECONNECT_TIMES = 3;
    private int handle_reconnect_times = 0;

    public LiveFWWaWaSDK()
    {
        timeoutWorker = new SDTimer();
    }

    private void makeSocket(String server)
    {
        logProcess("making socket [server=" + server + "]");
        try
        {
            IO.Options options = new IO.Options();
            options.reconnectionDelay = 100;
            socket = IO.socket("http://" + server, options);
            socket.on(Socket.EVENT_CONNECT, new EmitterListenerWrapper(Socket.EVENT_CONNECT)
            {
                @Override
                protected void call(String event, Object... objects)
                {
                    logSocketEvent(event, objects);
                    if (isInGame)
                    {
                        handle_reconnect_times = 0;
                        emitToServer("begin", permission);
                    }
                }
            });
            socket.on(Socket.EVENT_RECONNECTING, new EmitterListenerWrapper(Socket.EVENT_RECONNECTING)
            {
                @Override
                protected void call(String event, Object... objects)
                {
                    logSocketEvent(event, objects);
                    SDToast.showToast("控制器正在重连...");
                }
            });
            socket.on("catch", new EmitterListenerWrapper("catch")
            {
                @Override
                protected void call(String event, Object... objects)
                {
                    logReceive(event, objects);
                    Integer result = 0;
                    if (objects[0] instanceof Integer)
                    {
                        result = (Integer) objects[0];
                    } else if (objects[0] instanceof String)
                    {
                        result = Integer.valueOf((String) objects[0]);
                    }

                    callback.onResult(gameId, result.intValue() == 1 ? true : false);
                }
            });

            addSocketListener(Socket.EVENT_DISCONNECT);
            addSocketListener(Socket.EVENT_CONNECT_TIMEOUT);


            addSocketListener(Socket.EVENT_MESSAGE);
            addSocketListener(Socket.EVENT_PING);
            addSocketListener(Socket.EVENT_PONG);

            addSocketListener(Socket.EVENT_RECONNECT);
            addSocketListener(Socket.EVENT_RECONNECT_ATTEMPT);
            addSocketListener(Socket.EVENT_RECONNECT_ERROR);
            addSocketListener(Socket.EVENT_RECONNECT_FAILED);

            addSocketListener(Socket.EVENT_ERROR);
            addBizListener("begin");
            addBizListener("end");
            addBizListener("left");
            addBizListener("right");
            addBizListener("forward");
            addBizListener("backwards");
            addBizListener("stop");
            addBizListener("catch");

        } catch (Exception e)
        {
            logProcess("make socket failed [msg =" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setSocketAddress(String address)
    {
        makeSocket(address);
    }

    private void emitToServer(String event)
    {
        logSend(event);
        emit(event);
    }

    private void emitToServer(String event, String permission)
    {
        logSend(event, permission);
        JSONObject msgObj = new JSONObject();
        msgObj.put("permission_name", permission);
        emit(event, msgObj.toJSONString());
    }

    private void emit(String event, Object... args)
    {
        if (isSocketReady())
        {
            socket.emit(event, args);
        }
    }

    private void addSocketListener(String event)
    {
        if (socket != null)
        {
            socket.on(event, new EmitterListenerWrapper(event)
            {
                @Override
                protected void call(String event, Object... objects)
                {
                    switch (event)
                    {
                        case Socket.EVENT_CONNECT_ERROR:
                        case Socket.EVENT_CONNECT_TIMEOUT:
                        case Socket.EVENT_DISCONNECT:
                            if (isInGame && handle_reconnect_times < MAX_HANDLE_RECONNECT_TIMES && socket != null)
                            {
                                socket.connect();
                                handle_reconnect_times++;
                            }
                            break;
                    }
                    logSocketEvent(event, objects);
                }
            });
        }
    }

    private void addBizListener(String event)
    {
        if (socket != null)
        {
            socket.on(event, new EmitterListenerWrapper(event)
            {
                @Override
                protected void call(String event, Object... objects)
                {
                    logReceive(event, objects);
                    if (objects != null && objects.length > 0 && objects[0] != null)
                    {
                        resolve(objects[0]);
                    }
                }
            });
        }
    }


    @Override
    public void registerWaWaCallback(ILiveWaWaCallback listener)
    {
        callback = listener;
    }

    @Override
    public void joinQueue(String data, int gameId)
    {
        logProcess("join queue");
        this.gameId = gameId;
        permission = data;
        socket.connect();
        callback.onReady();
    }

    @Override
    public void drag(DIRECTION direction, boolean isPress)
    {
        if (isInGame)
        {
            if (isPress)
            {
                startMove(direction);
            } else
            {
                moveEnd();
            }
        }
    }

    @Override
    public void doCatch()
    {
        logProcess("do catch");
        if (isInGame)
            emitToServer("catch");
    }

    @Override
    public void roundTimeFinish()
    {
        if (isInGame)
        {
            doCatch();
            startTimeOutWorker();
        }
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
                    if (isInGame && callback != null)
                    {
                        logTimeoutWorker("try to end game");
                        callback.tryEndGame(gameId);
                    }
                }
            });
        }
    }

    @Override
    public void startGame()
    {
        logProcess("start game");
        isInGame = true;
        emitToServer("begin", permission);
    }

    @Override
    public void quitGame()
    {
        logProcess("quit game");
        emitToServer("end");
        isInGame = false;
        if (socket != null)
            socket.disconnect();
        socket = null;
        permission = null;
        timeoutWorker.stopWork();
        if (controlLooper != null)
            controlLooper.stop();
    }

    @Override
    public void onDestroy()
    {
        logProcess("on destroy");
        if (socket != null)
        {
            socket.disconnect();
            socket = null;
        }
        if (controlLooper != null)
        {
            controlLooper.stop();
        }
        controlLooper = null;
        callback = null;
    }

    private void resolve(Object msg)
    {
        try
        {
            JSONObject jMsg = JSON.parseObject(msg.toString(), JSONObject.class);
            if (jMsg.getInteger("errcode").intValue() == 3 && isInGame && callback != null)
            {
                logProcess("permission expired -> [errCode = 3]");
                callback.tryEndGame(gameId);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void startMove(final DIRECTION direction)
    {
        WWLiveView.VideoPlayer videoPlayer = null;
        if (callback != null)
            videoPlayer = callback.getCurrentPlayer();
        int position = 0;
        if (videoPlayer != null && videoPlayer == WWLiveView.VideoPlayer.MAIN)
        {
            position = LiveInformation.getInstance().getMainPlayerConfig().getCameraPosition();

        } else if (videoPlayer != null && videoPlayer == WWLiveView.VideoPlayer.SUB)
        {
            position = LiveInformation.getInstance().getSubPlayerConfig().getCameraPosition();
        }
        DirectionHelper.translate(direction, position, new DirectionHelper.Callback()
        {
            @Override
            public void left()
            {
                doMove("left");
            }

            @Override
            public void right()
            {
                doMove("right");
            }

            @Override
            public void forward()
            {
                doMove("backwards");
            }

            @Override
            public void backwards()
            {
                doMove("forward");
            }
        });
    }

    private void doMove(final String event)
    {
        if (controlLooper == null)
        {
            controlLooper = new SDSimpleLooper();
        }

        controlLooper.start(200, new Runnable()
        {
            @Override
            public void run()
            {
                emitToServer(event);
            }
        });
    }


    @Override
    public int getGameId()
    {
        return gameId;
    }

    public void moveEnd()
    {
        if (controlLooper != null)
        {
            controlLooper.stop();
        }
        emitToServer("stop");
    }

    private boolean isSocketReady()
    {
        return socket != null && socket.connected();
    }

    private void logProcess(String msg)
    {
        String log = "Process -> " + msg;
        log(log);
    }

    private void logTimeoutWorker(String msg)
    {
        String log = "Timeout -> " + msg;
        log(log);
    }

    private void logSocketEvent(String event, Object... objects)
    {
        StringBuilder builder = new StringBuilder();
        for (Object o : objects)
        {
            builder.append(o.toString());
        }
        String log = "Socket -> [event = " + event + "][params = " + builder.toString() + "]";
        log(log);
    }

    private void logReceive(String event, Object... objects)
    {
        StringBuilder builder = new StringBuilder();
        for (Object o : objects)
        {
            builder.append(o.toString());
        }
        String log = "Receive -> [event = " + event + "][params = " + builder.toString() + "]";
        log(log);
    }

    private void logSend(String event, String permission)
    {
        String log = "Send -> [event = " + event + "][permission = " + permission + "]";
        log(log);
    }

    private void logSend(String event)
    {
        String log = "Send -> [event = " + event + "]";
        log(log);
    }

    private void log(String log)
    {
        WWLogHelper.getInstance().log("[FW_SDK]" + log);
    }

}
