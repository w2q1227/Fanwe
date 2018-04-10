package com.fanwe.catchdoll.model;

/**
 * <积分商城>
 * Created by wwb on 2017/12/13 09:46.
 */

public class WWMainIntegralMallModel
{
    private int id;
    private int diamonds;
    private int score;
    private String image;
    private String diamonds_info;
    private String score_info;
    private String number_info;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(int diamonds)
    {
        this.diamonds = diamonds;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
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

    public String getNumber_info()
    {
        return number_info;
    }

    public void setNumber_info(String number_info)
    {
        this.number_info = number_info;
    }
}
