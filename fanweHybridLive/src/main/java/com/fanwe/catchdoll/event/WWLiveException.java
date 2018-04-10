package com.fanwe.catchdoll.event;

/**
 * Created by Administrator on 2017/11/17.
 */

public class WWLiveException
{

    public static final int CLOSED_IN_GAME = 0;
    public static final int START_GAME_FAILED = 1;
    public static final int JOIN_QUEUE_FAILED = 2;

    public int exceptionCode;

    public WWLiveException(int exceptionCode)
    {
        this.exceptionCode = exceptionCode;
    }
}
