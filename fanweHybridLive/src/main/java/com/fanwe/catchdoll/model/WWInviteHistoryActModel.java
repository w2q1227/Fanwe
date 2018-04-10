package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * <邀请记录>
 * Created by wwb on 2017/12/14 10:04.
 */

public class WWInviteHistoryActModel extends BaseActModel
{
    private int has_next;
    private int page;
    private String invite_code;//邀请码
    private String inviter_bonuse;//得到的奖励
    private String bonuse_des;//描述 （成功邀请好友即得30钻石）
    private String title;//标题  （卖友求钻石）
    private int num;
    private String num_format;
    private int bonuse_total;
    private String bonuse_total_format;

    private String bonuse_num_limit_des;
    private String bonuse_num_limit;
    private String bonuse_diamonds_limit_des;
    private String bonuse_diamonds_limit;


    private List<WWInviteHistoryModel> list;

    private WWShareModel share;

    public int getHas_next()
    {
        return has_next;
    }

    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public String getInvite_code()
    {
        return invite_code;
    }

    public void setInvite_code(String invite_code)
    {
        this.invite_code = invite_code;
    }

    public String getInviter_bonuse()
    {
        return inviter_bonuse;
    }

    public void setInviter_bonuse(String inviter_bonuse)
    {
        this.inviter_bonuse = inviter_bonuse;
    }

    public String getBonuse_des()
    {
        return bonuse_des;
    }

    public void setBonuse_des(String bonuse_des)
    {
        this.bonuse_des = bonuse_des;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<WWInviteHistoryModel> getList()
    {
        return list;
    }

    public void setList(List<WWInviteHistoryModel> list)
    {
        this.list = list;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public String getNum_format()
    {
        return num_format;
    }

    public void setNum_format(String num_format)
    {
        this.num_format = num_format;
    }

    public int getBonuse_total()
    {
        return bonuse_total;
    }

    public void setBonuse_total(int bonuse_total)
    {
        this.bonuse_total = bonuse_total;
    }

    public String getBonuse_total_format()
    {
        return bonuse_total_format;
    }

    public void setBonuse_total_format(String bonuse_total_format)
    {
        this.bonuse_total_format = bonuse_total_format;
    }

    public WWShareModel getShare()
    {
        return share;
    }

    public void setShare(WWShareModel share)
    {
        this.share = share;
    }

    public String getBonuse_num_limit_des()
    {
        return bonuse_num_limit_des;
    }

    public void setBonuse_num_limit_des(String bonuse_num_limit_des)
    {
        this.bonuse_num_limit_des = bonuse_num_limit_des;
    }

    public String getBonuse_num_limit()
    {
        return bonuse_num_limit;
    }

    public void setBonuse_num_limit(String bonuse_num_limit)
    {
        this.bonuse_num_limit = bonuse_num_limit;
    }

    public String getBonuse_diamonds_limit_des()
    {
        return bonuse_diamonds_limit_des;
    }

    public void setBonuse_diamonds_limit_des(String bonuse_diamonds_limit_des)
    {
        this.bonuse_diamonds_limit_des = bonuse_diamonds_limit_des;
    }

    public String getBonuse_diamonds_limit()
    {
        return bonuse_diamonds_limit;
    }

    public void setBonuse_diamonds_limit(String bonuse_diamonds_limit)
    {
        this.bonuse_diamonds_limit = bonuse_diamonds_limit;
    }
}
