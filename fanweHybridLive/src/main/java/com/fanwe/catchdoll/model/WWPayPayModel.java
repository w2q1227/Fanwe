package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.live.model.PayConfig;
import com.fanwe.live.model.PayModel;

/**
 * Created by yhz on 2017/11/16.
 */

public class WWPayPayModel extends BaseActModel
{
    private int need_pay;
    private PayModel pay;

    public PayModel getPay()
    {
        return pay;
    }

    public void setPay(PayModel pay)
    {
        this.pay = pay;
    }

    public int getNeed_pay()
    {
        return need_pay;
    }

    public void setNeed_pay(int need_pay)
    {
        this.need_pay = need_pay;
    }
}
