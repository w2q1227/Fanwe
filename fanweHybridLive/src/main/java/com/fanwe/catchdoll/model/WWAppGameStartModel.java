package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/17.
 */

public class WWAppGameStartModel extends BaseActModel implements Serializable
{
    private int round_time;
    private int game_id;
    private long diamonds;
    private String play_url;
    private String play_rtmp;
    private String play_rtmp2;
    private String permission_name;
    private String socket_address;
    private int play_position;
    private int play_position2;
    private int video_type;

    public long getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(long diamonds)
    {
        this.diamonds = diamonds;
    }

    public int getRound_time()
    {
        return round_time;
    }

    public void setRound_time(int round_time)
    {
        this.round_time = round_time;
    }

    public String getPlay_url()
    {
        return play_url;
    }

    public void setPlay_url(String play_url)
    {
        this.play_url = play_url;
    }

    public String getPlay_rtmp()
    {
        return play_rtmp;
    }

    public void setPlay_rtmp(String play_rtmp)
    {
        this.play_rtmp = play_rtmp;
    }

    public String getPlay_rtmp2()
    {
        return play_rtmp2;
    }

    public void setPlay_rtmp2(String play_rtmp2)
    {
        this.play_rtmp2 = play_rtmp2;
    }

    public int getGame_id()
    {
        return game_id;
    }

    public void setGame_id(int game_id)
    {
        this.game_id = game_id;
    }

    public String getPermission_name()
    {
        return permission_name;
    }

    public void setPermission_name(String permission_name)
    {
        this.permission_name = permission_name;
    }

    public String getSocket_address()
    {
        return socket_address;
    }

    public void setSocket_address(String socket_address)
    {
        this.socket_address = socket_address;
    }

    public int getPlay_position()
    {
        return play_position;
    }

    public void setPlay_position(int play_position)
    {
        this.play_position = play_position;
    }

    public int getPlay_position2()
    {
        return play_position2;
    }

    public void setPlay_position2(int play_position2)
    {
        this.play_position2 = play_position2;
    }

    public int getVideo_type()
    {
        return video_type;
    }

    public void setVideo_type(int video_type)
    {
        this.video_type = video_type;
    }
}
