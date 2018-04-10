package com.fanwe.catchdoll.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/*
 * Created by wwb on 2017/12/8 11:03.
 */

public class WWDollQuestionActModel extends BaseActModel
{
    private List<WWDollQuestionModel> list;

    public List<WWDollQuestionModel> getList()
    {
        return list;
    }

    public void setList(List<WWDollQuestionModel> list)
    {
        this.list = list;
    }
}
