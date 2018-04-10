package com.fanwe.catchdoll.model;

import com.fanwe.library.model.SelectableModel;

import java.io.Serializable;

/**
 * 配送地址列表数据模型
 * Created by LianCP on 2017/11/13.
 */
public class WWAddressModel extends SelectableModel implements Serializable
{

    /**
     * id : 22
     * user_id : 1
     * province : 福建省
     * city : 福州市
     * county : 鼓楼区
     * address : 西二环中路XXX小区
     * mobile : 18888888888
     * consignee : 张三
     * is_default : 1
     */
    private String id;
    private String user_id;
    private String province;
    private String city;
    private String county;
    private String address;
    private String mobile;
    private String consignee;
    private int is_default;//	状态: 0 非默认地址, 1 默认地址

    public String getDetailedAddress()
    {
        return "" + province + city + county + address;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
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

    public int getIs_default()
    {
        return is_default;
    }

    public void setIs_default(int is_default)
    {
        this.is_default = is_default;
    }
}
