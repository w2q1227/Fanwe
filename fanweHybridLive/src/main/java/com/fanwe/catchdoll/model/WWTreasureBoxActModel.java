package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * <积分宝箱>
 * Created by wwb on 2017/12/14 11:53.
 */

public class WWTreasureBoxActModel extends BaseActModel
{
    private long score;//需要的积分
    private int left_num;//还剩几次
    private String left_num_info;

    public long getScore()
    {
        return score;
    }

    public void setScore(long score)
    {
        this.score = score;
    }

    public int getLeft_num()
    {
        return left_num;
    }

    public void setLeft_num(int left_num)
    {
        this.left_num = left_num;
    }

    public String getLeft_num_info()
    {
        return left_num_info;
    }

    public void setLeft_num_info(String left_num_info)
    {
        this.left_num_info = left_num_info;
    }
}
