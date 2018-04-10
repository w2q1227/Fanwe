package com.fanwe.catchdoll.model;


/**
 * des:
 * Created by yangwb
 * on 2017/12/6.
 */

public class WWOrderDetailLogisticsModel
{
    private String logistics;
    private String logistics_url;
    private WWOrderDetailAddressInfoModel dispatching;

    public WWOrderDetailAddressInfoModel getDispatching()
    {
        return dispatching;
    }

    public void setDispatching(WWOrderDetailAddressInfoModel dispatching)
    {
        this.dispatching = dispatching;
    }

    public String getLogistics()
    {
        return logistics;
    }

    public void setLogistics(String logistics)
    {
        this.logistics = logistics;
    }

    public String getLogistics_url()
    {
        return logistics_url;
    }

    public void setLogistics_url(String logistics_url)
    {
        this.logistics_url = logistics_url;
    }
}
