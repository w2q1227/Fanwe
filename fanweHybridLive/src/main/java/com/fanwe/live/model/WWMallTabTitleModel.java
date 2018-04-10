package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

import java.io.Serializable;

/**
 * <商城tab>
 * Created by wwb on 2017/12/26 10:10.
 */

public class WWMallTabTitleModel implements SDSelectManager.Selectable, Serializable
{
    static final long serialVersionUID = 0;

    private int id;//分类id
    private String title;//分类名称

    private boolean selected;

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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!(obj instanceof WWMallTabTitleModel))
        {
            return false;
        }
        WWMallTabTitleModel other = (WWMallTabTitleModel) obj;

        return other.getId() == id;
    }

    @Override
    public String toString()
    {
        return title;
    }
}
