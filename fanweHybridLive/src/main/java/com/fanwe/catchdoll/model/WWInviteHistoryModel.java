package com.fanwe.catchdoll.model;

/**
 * <邀请记录>
 * Created by wwb on 2017/12/14 10:06.
 */

public class WWInviteHistoryModel
{
    private String id;
    private String invited_id;
    private String inviter_bonuse;
    private String time;
    private String nick_name;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getInvited_id()
    {
        return invited_id;
    }

    public void setInvited_id(String invited_id)
    {
        this.invited_id = invited_id;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getInviter_bonuse()
    {
        return inviter_bonuse;
    }

    public void setInviter_bonuse(String inviter_bonuse)
    {
        this.inviter_bonuse = inviter_bonuse;
    }
}
