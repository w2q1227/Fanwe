package com.fanwe.catchdoll.model;

/**
 * 要提交的添加或者修改配送地址的参数模型
 * Created by LianCP on 2017/11/15.
 */
public class WWSubmitAddAddressModel
{
    private String id = "";//收货地址id(编辑要传，新增没有)
    private String consignee;//姓名
    private String mobile;//手机
    private String province;//省
    private String city;//市
    private String county;//县/区
    private String address;//详细地址

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCounty()
    {
        return county;
    }

    public void setCounty(String county)
    {
        this.county = county;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
