package com.fanwe.catchdoll.model;

/**
 * Created by Administrator on 2017/12/15.
 */

public class WWAppMyReserveModel
{
    private int status;
    private String error;
    private int has_reserve;
    private int my_reserve;
    private int reserve_count;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public int getHas_reserve()
    {
        return has_reserve;
    }

    public void setHas_reserve(int has_reserve)
    {
        this.has_reserve = has_reserve;
    }

    public int getMy_reserve()
    {
        return my_reserve;
    }

    public void setMy_reserve(int my_reserve)
    {
        this.my_reserve = my_reserve;
    }

    public int getReserve_count()
    {
        return reserve_count;
    }

    public void setReserve_count(int reserve_count)
    {
        this.reserve_count = reserve_count;
    }
}
