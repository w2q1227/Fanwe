package com.fanwe.catchdoll.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.fanwe.live.model.UserModel;

/**
 * Created by Administrator on 2017/11/17.
 */

public class WWAppDollModel
{

    private String id;
    private String name;
    private String img;
    private String room_id;
    private String play_user_id;
    private String create_time;
    private String front_push_user;
    private String side_push_user;
    private String sort;
    private String price;
    private String freight;
    private String round_time;
    private String status;
    private String content;
    private String monitor_time;
    private String url;
    private String play_head_image;
    private String play_nick_name;
    private String is_reserve;
    private String is_side;
    private int type;
    private String music;

    public String getIs_reserve()
    {
        return is_reserve;
    }

    public void setIs_reserve(String is_reserve)
    {
        this.is_reserve = is_reserve;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getImg()
    {
        return img;
    }

    public void setRoom_id(String room_id)
    {
        this.room_id = room_id;
    }

    public String getRoom_id()
    {
        return room_id;
    }

    public void setPlay_user_id(String play_user_id)
    {
        this.play_user_id = play_user_id;
    }

    public String getPlay_user_id()
    {
        return play_user_id;
    }

    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }

    public String getCreate_time()
    {
        return create_time;
    }

    public void setFront_push_user(String front_push_user)
    {
        this.front_push_user = front_push_user;
    }

    public String getFront_push_user()
    {
        return front_push_user;
    }

    public void setSide_push_user(String side_push_user)
    {
        this.side_push_user = side_push_user;
    }

    public String getSide_push_user()
    {
        return side_push_user;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public String getSort()
    {
        return sort;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getPrice()
    {
        return price;
    }

    public void setFreight(String freight)
    {
        this.freight = freight;
    }

    public String getFreight()
    {
        return freight;
    }

    public void setRound_time(String round_time)
    {
        this.round_time = round_time;
    }

    public String getRound_time()
    {
        return round_time;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setMonitor_time(String monitor_time)
    {
        this.monitor_time = monitor_time;
    }

    public String getMonitor_time()
    {
        return monitor_time;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    public void setPlay_head_image(String play_head_image)
    {
        this.play_head_image = play_head_image;
    }

    public String getPlay_head_image()
    {
        return play_head_image;
    }

    public String getPlay_nick_name()
    {
        return play_nick_name;
    }

    public void setPlay_nick_name(String play_nick_name)
    {
        this.play_nick_name = play_nick_name;
    }

    public String getIs_side()
    {
        return is_side;
    }

    public void setIs_side(String is_side)
    {
        this.is_side = is_side;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getMusic()
    {
        return music;
    }

    public void setMusic(String music)
    {
        this.music = music;
    }

    @JSONField(serialize = false)
    public UserModel getUser()
    {
        if (getPlay_user_id() == null || getPlay_user_id().equals("0"))
        {
            return null;
        }
        UserModel model = new UserModel();
        model.setNick_name(getPlay_nick_name());
        model.setHead_image(getPlay_head_image());
        model.setUser_id(getPlay_user_id());
        return model;
    }

    @JSONField(serialize = false)
    public boolean isServe()
    {
        try
        {
            if(getIs_reserve() == null) return false;

            int isServe = Integer.parseInt(getIs_reserve());
            if (isServe == 1)
            {
                return true;
            }else {
                return false;
            }
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
