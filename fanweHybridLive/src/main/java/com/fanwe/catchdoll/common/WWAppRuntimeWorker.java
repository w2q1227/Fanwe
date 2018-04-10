package com.fanwe.catchdoll.common;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;

/**
 * des:
 * Created by yangwb
 * on 2017/12/6.
 */

public class WWAppRuntimeWorker
{
    /**
     * 账单明细是否显示
     *
     * @return
     */
    public static int getHas_billing()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getHas_billing();
        }
        return 0;
    }

    /**
     * 积分宝箱是否显示
     *
     * @return
     */
    public static int getHas_score()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getHas_score();
        }
        return 0;
    }

    /**
     * 邀请好友，邀请码是否显示
     *
     * @return
     */
    public static int getHas_invitation()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getHas_invitation();
        }
        return 0;
    }
}
