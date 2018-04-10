package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.live.model.PayItemModel;

import java.util.List;

/**
 * Created by yhz on 2017/11/15.
 */

public class WWUserDollDetailModel extends BaseActModel
{
    private WWDollDetailModel detail;
    private List<WWReceiveDollModel> detail_similar;
    private List<PayItemModel> pay_list;

    public WWDollDetailModel getDetail()
    {
        return detail;
    }

    public void setDetail(WWDollDetailModel detail)
    {
        this.detail = detail;
    }

    public List<WWReceiveDollModel> getDetail_similar()
    {
        return detail_similar;
    }

    public void setDetail_similar(List<WWReceiveDollModel> detail_similar)
    {
        this.detail_similar = detail_similar;
    }

    public List<PayItemModel> getPay_list()
    {
        return pay_list;
    }

    public void setPay_list(List<PayItemModel> pay_list)
    {
        this.pay_list = pay_list;
    }
}
