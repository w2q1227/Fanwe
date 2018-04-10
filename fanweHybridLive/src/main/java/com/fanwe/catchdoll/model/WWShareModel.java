package com.fanwe.catchdoll.model;

/**
 * <分享内容>
 * Created by wwb on 2017/12/14 15:02.
 */

public class WWShareModel
{
    private String share_title;
    private String share_img;
    private String invite_code;
    private String download_url;
    private String des;

    public String getShare_title()
    {
        return share_title;
    }

    public void setShare_title(String share_title)
    {
        this.share_title = share_title;
    }

    public String getShare_img()
    {
        return share_img;
    }

    public void setShare_img(String share_img)
    {
        this.share_img = share_img;
    }

    public String getInvite_code()
    {
        return invite_code;
    }

    public void setInvite_code(String invite_code)
    {
        this.invite_code = invite_code;
    }

    public String getDownload_url()
    {
        return download_url;
    }

    public void setDownload_url(String download_url)
    {
        this.download_url = download_url;
    }

    public String getDes()
    {
        return des;
    }

    public void setDes(String des)
    {
        this.des = des;
    }
}
