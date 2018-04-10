package com.fanwe.catchdoll.util;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface WWConstant
{
    interface DollStatus
    {
        int ENABLE_PLAY = 0;
        int OCCUPIED = 1;
        int MALFUNCTION = 2;
    }

    interface DollEvent
    {
        int GAME_START = 1001;
        int GAME_END = 1002;
        int GAME_FINISH = 1003;
        int QUEUE_JOIN = 2001;
        int QUEUE_QUIT = 2002;
        int QUEUE_GIVE_UP = 2003;
    }

    interface DollSolutionType
    {
        int SOLUTION_FWSDK_STEAM = 2;
        int SOLUTION_TXSDK_ILIVE = 3;
        int SOLUTION_FWSDK_ILIVE = 4;
    }

    interface DollSDKType
    {
        int FW_SDK = 2;
        int FW_SDK1 = 4;
        int TX_SDK = 3;
    }

    interface VIDEO_PLAYER
    {
        int NONE = -1;
        int STEAM_PLAYER = 0;
        int ROOM_PLAYER = 1;
    }

}
