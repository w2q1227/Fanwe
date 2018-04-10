package com.fanwe.catchdoll.model;

/**
 * Created by Administrator on 2017/11/14.
 */

public class WWRechargeLogModel
{
    private String order_id;
    private String payment_name;
    private String payment_logo;
    private String money;
    private String diamonds;
    private String pay_time;

    public String getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(String diamonds)
    {
        this.diamonds = diamonds;
    }

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money)
    {
        this.money = money;
    }

    public String getOrder_id()
    {
        return order_id;
    }

    public void setOrder_id(String order_id)
    {
        this.order_id = order_id;
    }

    public String getPay_time()
    {
        return pay_time;
    }

    public void setPay_time(String pay_time)
    {
        this.pay_time = pay_time;
    }

    public String getPayment_logo()
    {
        return payment_logo;
    }

    public void setPayment_logo(String payment_logo)
    {
        this.payment_logo = payment_logo;
    }

    public String getPayment_name()
    {
        return payment_name;
    }

    public void setPayment_name(String payment_name)
    {
        this.payment_name = payment_name;
    }
}
