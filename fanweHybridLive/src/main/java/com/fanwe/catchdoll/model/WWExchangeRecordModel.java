package com.fanwe.catchdoll.model;

/**
 * <兑换记录>
 * Created by wwb on 2017/12/13 09:46.
 */

public class WWExchangeRecordModel
{
    private String diamonds;
    private String score;
    private String diamonds_info;
    private String score_info;
    private String create_time;


    public String getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(String diamonds)
    {
        this.diamonds = diamonds;
    }

    public String getScore()
    {
        return score;
    }

    public void setScore(String score)
    {
        this.score = score;
    }

    public String getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }

    public String getDiamonds_info()
    {
        return diamonds_info;
    }

    public void setDiamonds_info(String diamonds_info)
    {
        this.diamonds_info = diamonds_info;
    }

    public String getScore_info()
    {
        return score_info;
    }

    public void setScore_info(String score_info)
    {
        this.score_info = score_info;
    }
}
