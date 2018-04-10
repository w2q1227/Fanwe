package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 */

public class WWMainMyDollListActModel extends BaseActModel
{
    private int has_next;
    List<WWMainMyDollModel> list;

    public List<WWMainMyDollModel> getList()
    {
        return list;
    }

    public void setList(List<WWMainMyDollModel> list)
    {
        this.list = list;
    }

    public int getHas_next()
    {
        return has_next;
    }

    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }
}
