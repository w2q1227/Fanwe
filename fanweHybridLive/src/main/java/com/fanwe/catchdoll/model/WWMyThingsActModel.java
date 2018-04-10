package com.fanwe.catchdoll.model;


import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 */

public class WWMyThingsActModel extends BaseActModel
{
    private int has_next;
    List<WWMyThingsModel> list;

    public List<WWMyThingsModel> getList()
    {
        return list;
    }

    public void setList(List<WWMyThingsModel> list)
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
