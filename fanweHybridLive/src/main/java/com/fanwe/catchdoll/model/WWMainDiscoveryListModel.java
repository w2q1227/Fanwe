package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/15 08:41.
 */

public class WWMainDiscoveryListModel extends BaseActModel
{
    private int has_next;
    private List<WWMainDiscoveryModel> list;

    public List<WWMainDiscoveryModel> getList()
    {
        return list;
    }

    public void setList(List<WWMainDiscoveryModel> list)
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
