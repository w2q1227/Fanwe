package com.fanwe.catchdoll.model;

/**
 * des:
 * Created by yangwb
 * on 2017/12/6.
 */

public class WWOrderDetailAddressInfoModel
{
    private String address_id;
    private String address;
    private String mobile;
    private String consignee;

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress_id()
    {
        return address_id;
    }

    public void setAddress_id(String address_id)
    {
        this.address_id = address_id;
    }

    public String getConsignee()
    {
        return consignee;
    }

    public void setConsignee(String consignee)
    {
        this.consignee = consignee;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
}
