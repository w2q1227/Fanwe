package com.fanwe.catchdoll.push;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.fanwe.catchdoll.activity.WWLiveLayoutActivity;
import com.fanwe.catchdoll.model.WWReserveInfo;
import com.fanwe.catchdoll.push.msg.WWReserveMsg;
import com.fanwe.hybrid.push.PushRunnable;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.umeng.message.entity.UMessage;

/**
 * Created by Administrator on 2017/12/20.
 */

public class WWReserveRunnable extends PushRunnable
{
    protected WWReserveMsg msg;

    public WWReserveRunnable(Context context, UMessage msg)
    {
        super(context, msg);
    }

    @Override
    public void run()
    {
        try
        {
            msg = parseObject(WWReserveMsg.class);
            Intent intent = WWLiveLayoutActivity.pushStartIntent(SDTypeParseUtil.getInt(msg.getRoom_id()), msg.getGroup_id(), JSON.parseObject(msg.getReserve_info(), WWReserveInfo.class), context);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
