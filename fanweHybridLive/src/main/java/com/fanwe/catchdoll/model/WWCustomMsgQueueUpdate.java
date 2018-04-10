package com.fanwe.catchdoll.model;

import com.fanwe.live.model.custommsg.CustomMsg;

/**
 * Created by Administrator on 2017/12/8.
 */

public class WWCustomMsgQueueUpdate extends CustomMsg
{
    private int room_id;
    private int num;

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }
}
