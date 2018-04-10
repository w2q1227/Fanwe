package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 收货地址列表数据模型
 * Created by LianCP on 2017/11/15.
 */
public class WWAddressListModel extends BaseActModel
{
    private List<WWAddressModel> consignee_list;
    private int has_next;
    private int page;

    public List<WWAddressModel> getConsignee_list()
    {
        return consignee_list;
    }

    public void setConsignee_list(List<WWAddressModel> consignee_list)
    {
        this.consignee_list = consignee_list;
    }

    public int getHas_next()
    {
        return has_next;
    }

    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }
}
