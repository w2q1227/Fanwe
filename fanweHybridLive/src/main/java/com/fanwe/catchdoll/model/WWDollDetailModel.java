package com.fanwe.catchdoll.model;

/**
 * Created by yhz on 2017/11/15.
 */

public class WWDollDetailModel
{
    private String doll_id;

    private String doll_name;

    private String order_sn;

    private String img;

    private int status;    //状态: 0 未领取, 1 领取中, 2 已领取

    private String grab_time;

    private String pay_time;

    private String logistics;//快递地址

    private String logistics_url;//查询快递地址

    private int recharge_btn_show;//是否显示支付方式

    private String freight;//运费

    private double freight_number;

    private WWUserDollDispatchingModel dispatching;

    private WWUserDollDispatchingDefaultModel dispatching_default;

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

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
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

    public WWUserDollDispatchingModel getDispatching()
    {
        return dispatching;
    }

    public void setDispatching(WWUserDollDispatchingModel dispatching)
    {
        this.dispatching = dispatching;
    }

    public WWUserDollDispatchingDefaultModel getDispatching_default()
    {
        return dispatching_default;
    }

    public void setDispatching_default(WWUserDollDispatchingDefaultModel dispatching_default)
    {
        this.dispatching_default = dispatching_default;
    }

    public int getRecharge_btn_show()
    {
        return recharge_btn_show;
    }

    public void setRecharge_btn_show(int recharge_btn_show)
    {
        this.recharge_btn_show = recharge_btn_show;
    }

    public String getFreight()
    {
        return freight;
    }

    public void setFreight(String freight)
    {
        this.freight = freight;
    }

    public double getFreight_number()
    {
        return freight_number;
    }

    public void setFreight_number(double freight_number)
    {
        this.freight_number = freight_number;
    }

    public String getLogistics_url()
    {
        return logistics_url;
    }

    public void setLogistics_url(String logistics_url)
    {
        this.logistics_url = logistics_url;
    }

    public String getOrder_sn()
    {
        return order_sn;
    }

    public void setOrder_sn(String order_sn)
    {
        this.order_sn = order_sn;
    }
}
