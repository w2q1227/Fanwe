package com.fanwe.live.model;

/**
 * Created by Administrator on 2018/1/2.
 */

public class LivePlayConfig
{

    private String url;

    private String accUrl;

    private int playAngle = 0;

    private boolean enableAcc = true;

    private boolean ignoreNonAcc = false;

    private boolean needCover = true;

    private String pushName;

    private int cameraPosition;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getAccUrl()
    {
        return accUrl;
    }

    public void setAccUrl(String accUrl)
    {
        this.accUrl = accUrl;
    }

    public int getPlayAngle()
    {
        return playAngle;
    }

    public void setPlayAngle(int playAngle)
    {
        this.playAngle = 360 - playAngle;
    }

    public boolean isIgnoreNonAcc()
    {
        return ignoreNonAcc;
    }

    public void setIgnoreNonAcc(boolean ignoreNonAcc)
    {
        this.ignoreNonAcc = ignoreNonAcc;
    }

    public boolean isEnableAcc()
    {
        return enableAcc;
    }

    public void setEnableAcc(boolean enableAcc)
    {
        this.enableAcc = enableAcc;
    }

    public boolean isNeedCover()
    {
        return needCover;
    }

    public void setNeedCover(boolean needCover)
    {
        this.needCover = needCover;
    }

    public String getPushName()
    {
        return pushName;
    }

    public void setPushName(String pushName)
    {
        this.pushName = pushName;
    }

    public int getCameraPosition()
    {
        return cameraPosition;
    }

    public void setCameraPosition(int cameraPosition)
    {
        this.cameraPosition = cameraPosition;
    }

    @Override
    public String toString()
    {
        if (isEnableAcc())
        {
            return "accUrl:" + accUrl + "|angle:" + playAngle + "|pushName:" + pushName ;
        } else
        {
            return "url:" + url + "|angle:" + playAngle + "|pushName:" + pushName;
        }
    }
}
