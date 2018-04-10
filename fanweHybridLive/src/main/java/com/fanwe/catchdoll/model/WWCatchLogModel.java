package com.fanwe.catchdoll.model;

/**
 * Created by Administrator on 2017/11/14.
 */

public class WWCatchLogModel
{
    private String id;
    private String title;
    private String img;
    private String diamonds;
    private String create_time;

    public String getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }

    public String getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(String diamonds)
    {
        this.diamonds = diamonds;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
