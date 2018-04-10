package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * <签到数据>
 * Created by wwb on 2017/12/15 10:38.
 */

public class WWSignInfoActModel extends BaseActModel
{

    private String times;
    private String  conten;
    private int sign_in_btn;//是否可签到

    private WWSignInfoModel sign_in_info;


    public String getTimes()
    {
        return times;
    }

    public void setTimes(String times)
    {
        this.times = times;
    }

    public String getConten()
    {
        return conten;
    }

    public void setConten(String conten)
    {
        this.conten = conten;
    }

    public int getSign_in_btn()
    {
        return sign_in_btn;
    }

    public void setSign_in_btn(int sign_in_btn)
    {
        this.sign_in_btn = sign_in_btn;
    }

    public WWSignInfoModel getSign_in_info()
    {
        return sign_in_info;
    }

    public void setSign_in_info(WWSignInfoModel sign_in_info)
    {
        this.sign_in_info = sign_in_info;
    }
}
