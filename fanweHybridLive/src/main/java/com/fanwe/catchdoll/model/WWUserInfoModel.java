package com.fanwe.catchdoll.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/17.
 */

public class WWUserInfoModel implements Serializable
{
    private String id;
    private String nickname;
    private String head_img;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getHead_img()
    {
        return head_img;
    }

    public void setHead_img(String head_img)
    {
        this.head_img = head_img;
    }
}
