package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * <兑换记录>
 * Created by wwb on 2017/12/13 09:37.
 */

public class WWExchangeRecordActModel extends BaseActModel
{
    private int has_next;
    private int page;

    private List<WWExchangeRecordModel> list;

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

    public List<WWExchangeRecordModel> getList()
    {
        return list;
    }

    public void setList(List<WWExchangeRecordModel> list)
    {
        this.list = list;
    }
}
