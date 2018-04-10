package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * des:
 * Created by yangwb
 * on 2017/12/6.
 */

public class WWOrderDetailActModel extends BaseActModel
{
    private WWOrderDetailLogisticsModel detail;
    private List<WWOrderDollReceiveInfoModel> detail_similar;

    public WWOrderDetailLogisticsModel getDetail()
    {
        return detail;
    }

    public void setDetail(WWOrderDetailLogisticsModel detail)
    {
        this.detail = detail;
    }

    public List<WWOrderDollReceiveInfoModel> getDetail_similar()
    {
        return detail_similar;
    }

    public void setDetail_similar(List<WWOrderDollReceiveInfoModel> detail_similar)
    {
        this.detail_similar = detail_similar;
    }
}
