package com.fanwe.catchdoll.model;

/**
 * Created by Administrator on 2017/11/20.
 */

public class WWAppGameEndModel
{
    private int status;
    private int is_grab;
    private int id;
    private String error;
    private int count_down;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getIs_grab()
    {
        return is_grab;
    }

    public void setIs_grab(int is_grab)
    {
        this.is_grab = is_grab;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public int getCount_down()
    {
        return count_down;
    }

    public void setCount_down(int count_down)
    {
        this.count_down = count_down;
    }
}
