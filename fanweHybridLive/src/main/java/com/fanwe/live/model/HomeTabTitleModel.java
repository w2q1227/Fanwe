package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15.
 */
public class HomeTabTitleModel implements SDSelectManager.Selectable, Serializable
{
    static final long serialVersionUID = 0;

    private int classified_id; // 分类id
    private String title; // 分类名称

    // add
    private boolean selected;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getClassified_id()
    {
        return classified_id;
    }

    public void setClassified_id(int classified_id)
    {
        this.classified_id = classified_id;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!(obj instanceof HomeTabTitleModel))
        {
            return false;
        }
        HomeTabTitleModel other = (HomeTabTitleModel) obj;

        return other.getClassified_id() == classified_id;
    }

    @Override
    public String toString()
    {
        return title;
    }
}
