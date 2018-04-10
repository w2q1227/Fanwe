package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * <邀请码验证>
 * Created by wwb on 2017/12/13 17:12.
 */

public class WWValidateInviteCodeActModel extends BaseActModel
{
    private String inviter_bonuse;//邀请者奖励金币
    private String invited_user_bonuse;//被邀请者奖励金币

    public String getInviter_bonuse()
    {
        return inviter_bonuse;
    }

    public void setInviter_bonuse(String inviter_bonuse)
    {
        this.inviter_bonuse = inviter_bonuse;
    }

    public String getInvited_user_bonuse()
    {
        return invited_user_bonuse;
    }

    public void setInvited_user_bonuse(String invited_user_bonuse)
    {
        this.invited_user_bonuse = invited_user_bonuse;
    }
}
