package com.fanwe.catchdoll.push.msg;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class WWReserveMsg implements Serializable
{

    private String type;

    private String room_id;

    private String group_id;

    private String reserve_info;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(String room_id)
    {
        this.room_id = room_id;
    }

    public String getGroup_id()
    {
        return group_id;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }

    public String getReserve_info()
    {
        return reserve_info;
    }

    public void setReserve_info(String reserve_info)
    {
        this.reserve_info = reserve_info;
    }
}
