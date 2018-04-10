package com.fanwe.catchdoll.model;

import com.fanwe.library.model.SelectableModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/5.
 */

public class WWReceiveDollModel extends SelectableModel implements Serializable
{

    /**
     * id : 28
     * order_sn : 20171130110434710
     * room_id : 100001
     * doll_id : 1
     * doll_name : 自建兔子
     * img : http://liveimage.fanwe.net/public/attachment/201711/24/13/5a17b0c98097c.png
     * status : 0
     * grab_time : 2017-11-30 11:04:34
     * pay_time : 0
     * logistics :
     * freight :
     */
    private String id;
    private String order_sn;
    private String room_id;
    private String doll_id;
    private String doll_name;
    private String img;
    private String status;
    private String grab_time;
    private String pay_time;
    private String logistics;
    private String freight;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOrder_sn()
    {
        return order_sn;
    }

    public void setOrder_sn(String order_sn)
    {
        this.order_sn = order_sn;
    }

    public String getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(String room_id)
    {
        this.room_id = room_id;
    }

    public String getDoll_id()
    {
        return doll_id;
    }

    public void setDoll_id(String doll_id)
    {
        this.doll_id = doll_id;
    }

    public String getDoll_name()
    {
        return doll_name;
    }

    public void setDoll_name(String doll_name)
    {
        this.doll_name = doll_name;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getGrab_time()
    {
        return grab_time;
    }

    public void setGrab_time(String grab_time)
    {
        this.grab_time = grab_time;
    }

    public String getPay_time()
    {
        return pay_time;
    }

    public void setPay_time(String pay_time)
    {
        this.pay_time = pay_time;
    }

    public String getLogistics()
    {
        return logistics;
    }

    public void setLogistics(String logistics)
    {
        this.logistics = logistics;
    }

    public String getFreight()
    {
        return freight;
    }

    public void setFreight(String freight)
    {
        this.freight = freight;
    }
}
