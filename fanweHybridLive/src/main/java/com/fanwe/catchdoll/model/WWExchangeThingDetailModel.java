package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * <商城兑换列表实物详情>
 * Created by wwb on 2017/12/26 15:46.
 */

public class WWExchangeThingDetailModel extends BaseActModel
{
    private String id;
    private String content;
    private long score;
    private String url;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public long getScore()
    {
        return score;
    }

    public void setScore(long score)
    {
        this.score = score;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
