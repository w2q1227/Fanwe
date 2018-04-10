package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * des:
 * Created by yangwb
 * on 2017/12/7.
 */

public class WWGrabDollRecordActModel extends BaseActModel
{
    private int page;
    private int has_next;
    private List<WWGrabDollDataModel> list;

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getHas_next()
    {
        return has_next;
    }

    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }

    public List<WWGrabDollDataModel> getList()
    {
        return list;
    }

    public void setList(List<WWGrabDollDataModel> list)
    {
        this.list = list;
    }
}
