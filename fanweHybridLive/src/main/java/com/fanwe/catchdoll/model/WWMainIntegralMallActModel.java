package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * <积分商城>
 * Created by wwb on 2017/12/13 09:37.
 */

public class WWMainIntegralMallActModel extends BaseActModel
{
    private int has_next;
    private int page;

    private List<WWMainIntegralMallModel> list;

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

    public List<WWMainIntegralMallModel> getList()
    {
        return list;
    }

    public void setList(List<WWMainIntegralMallModel> list)
    {
        this.list = list;
    }
}
