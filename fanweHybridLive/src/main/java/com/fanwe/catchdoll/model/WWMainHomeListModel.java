package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActListModel;
import com.fanwe.live.model.LiveBannerModel;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14.
 */

public class WWMainHomeListModel extends BaseActListModel
{
    private List<WWMainHomeModel> list;
    private List<LiveBannerModel> banner;
    private int has_next;
    private int page;

    public List<WWMainHomeModel> getList()
    {
        return list;
    }

    public void setList(List<WWMainHomeModel> list)
    {
        this.list = list;
    }

    public List<LiveBannerModel> getBanner()
    {
        return banner;
    }

    public void setBanner(List<LiveBannerModel> banner)
    {
        this.banner = banner;
    }

    @Override
    public int getHas_next()
    {
        return has_next;
    }

    @Override
    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }

    @Override
    public int getPage()
    {
        return page;
    }

    @Override
    public void setPage(int page)
    {
        this.page = page;
    }
}
