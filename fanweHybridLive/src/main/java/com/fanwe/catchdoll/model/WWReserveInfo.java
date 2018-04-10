package com.fanwe.catchdoll.model;

/**
 * Created by Administrator on 2017/12/20.
 */

public class WWReserveInfo
{

    private String id;
    private String dolls_id;
    private String room_id;
    private String user_id;
    private long create_time;
    private long begin_time;
    private long end_time;
    private String is_effect;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDolls_id()
    {
        return dolls_id;
    }

    public void setDolls_id(String dolls_id)
    {
        this.dolls_id = dolls_id;
    }

    public String getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(String room_id)
    {
        this.room_id = room_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public long getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(long create_time)
    {
        this.create_time = create_time;
    }

    public long getBegin_time()
    {
        return begin_time;
    }

    public void setBegin_time(long begin_time)
    {
        this.begin_time = begin_time;
    }

    public long getEnd_time()
    {
        return end_time;
    }

    public void setEnd_time(long end_time)
    {
        this.end_time = end_time;
    }

    public String getIs_effect()
    {
        return is_effect;
    }

    public void setIs_effect(String is_effect)
    {
        this.is_effect = is_effect;
    }
}
