package com.fanwe.catchdoll.model;

/**
 * 主页-首页数据模型
 * Created by LianCP on 2017/11/14.
 */
public class WWMainHomeModel
{

    /**
     * room_id : 311673
     * group_id : 311673
     * title : 彩色小猪
     * price : 10
     * live_in : 1
     * video_type : 1
     * create_type : 0
     * room_type : 3
     * watch_number : 24
     * live_image : http://liveimage.fanwe.net/public/attachment/201612/09/15/origin/1481240489519.jpg
     * status : 0
     * status_name : 空闲
     */
    private int room_id;
    private String group_id;
    private String title;
    private String price;
    private String live_in;
    private String video_type;
    private String create_type;
    private String room_type;
    private String watch_number;
    private String live_image;
    private int status;//int	状态 0-空闲、1-使用、2-维护
    private String status_name;//	string	空闲、使用、维护

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public String getGroup_id()
    {
        return group_id;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getLive_in()
    {
        return live_in;
    }

    public void setLive_in(String live_in)
    {
        this.live_in = live_in;
    }

    public String getVideo_type()
    {
        return video_type;
    }

    public void setVideo_type(String video_type)
    {
        this.video_type = video_type;
    }

    public String getCreate_type()
    {
        return create_type;
    }

    public void setCreate_type(String create_type)
    {
        this.create_type = create_type;
    }

    public String getRoom_type()
    {
        return room_type;
    }

    public void setRoom_type(String room_type)
    {
        this.room_type = room_type;
    }

    public String getWatch_number()
    {
        return watch_number;
    }

    public void setWatch_number(String watch_number)
    {
        this.watch_number = watch_number;
    }

    public String getLive_image()
    {
        return live_image;
    }

    public void setLive_image(String live_image)
    {
        this.live_image = live_image;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getStatus_name()
    {
        return status_name;
    }

    public void setStatus_name(String status_name)
    {
        this.status_name = status_name;
    }
}
