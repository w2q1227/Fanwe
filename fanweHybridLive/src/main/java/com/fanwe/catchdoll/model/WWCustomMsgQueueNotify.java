package com.fanwe.catchdoll.model;

import com.fanwe.live.LiveConstant;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * Created by Administrator on 2017/12/7.
 */

public class WWCustomMsgQueueNotify extends CustomMsg
{

    private WWReserveInfo reserve_info;

    public WWReserveInfo getReserve_info()
    {
        return reserve_info;
    }

    public void setReserve_info(WWReserveInfo reserve_info)
    {
        this.reserve_info = reserve_info;
    }

}
