package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * <商城兑换列表实物详情>
 * Created by wwb on 2017/12/26 15:46.
 */

public class WWExchangeThingDetailActModel extends BaseActModel
{
    private WWExchangeThingDetailModel exchange_thing;

    public WWExchangeThingDetailModel getExchange_thing()
    {
        return exchange_thing;
    }

    public void setExchange_thing(WWExchangeThingDetailModel exchange_thing)
    {
        this.exchange_thing = exchange_thing;
    }
}
