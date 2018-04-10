package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * des:
 * Created by yangwb
 * on 2017/11/16.
 */

public class WWAccountLogActModel extends BaseActModel
{
    private int has_next;
    private int page;
    private List<WWCatchLogModel> list;

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

    public List<WWCatchLogModel> getList()
    {
        return list;
    }

    public void setList(List<WWCatchLogModel> list)
    {
        this.list = list;
    }
}
