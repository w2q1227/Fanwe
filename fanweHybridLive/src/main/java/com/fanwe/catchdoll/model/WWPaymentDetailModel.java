package com.fanwe.catchdoll.model;

/**
 * <交易详情>
 * Created by wang on 2017/11/15 17:54.
 */

public class WWPaymentDetailModel
{
    private int is_paid;
    private String money;
    private String payment_id;
    private String payment_type;
    private String pay_time;
    private String logo;
    private String recharge_name;
    private String diamonds;
    private long balance;

    public int getIs_paid()
    {
        return is_paid;
    }

    public void setIs_paid(int is_paid)
    {
        this.is_paid = is_paid;
    }

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money)
    {
        this.money = money;
    }

    public String getPayment_id()
    {
        return payment_id;
    }

    public void setPayment_id(String payment_id)
    {
        this.payment_id = payment_id;
    }

    public String getPayment_type()
    {
        return payment_type;
    }

    public void setPayment_type(String payment_type)
    {
        this.payment_type = payment_type;
    }

    public String getPay_time()
    {
        return pay_time;
    }

    public void setPay_time(String pay_time)
    {
        this.pay_time = pay_time;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getRecharge_name()
    {
        return recharge_name;
    }

    public void setRecharge_name(String recharge_name)
    {
        this.recharge_name = recharge_name;
    }

    public String getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(String diamonds)
    {
        this.diamonds = diamonds;
    }

    public long getBalance()
    {
        return balance;
    }

    public void setBalance(long balance)
    {
        this.balance = balance;
    }
}
