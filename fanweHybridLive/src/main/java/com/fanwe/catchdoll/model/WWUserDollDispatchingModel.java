package com.fanwe.catchdoll.model;

/**
 * Created by yhz on 2017/11/15.
 */

public class WWUserDollDispatchingModel
{
    private String address_id;
    private String address;
    private String mobile;
    private String consignee;

    public String getAddress_id()
    {
        return address_id;
    }

    public void setAddress_id(String address_id)
    {
        this.address_id = address_id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getConsignee()
    {
        return consignee;
    }

    public void setConsignee(String consignee)
    {
        this.consignee = consignee;
    }
}
