package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * des:个人中心项
 * Created by yangwb
 * on 2017/11/13.
 */

public class WWMeItemView extends BaseAppView
{
    public WWMeItemView(Context context)
    {
        super(context);
        init();
    }

    public WWMeItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMeItemView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private ImageView iv_item;
    private TextView tv_item_title;
    private TextView tv_right_title;
    private TextView tv_ticket;
    private ImageView iv_arrow_right;
    private View view_line;

    private void init()
    {
        setContentView(R.layout.ww_view_main_me_item);

        initView();
    }

    private void initView()
    {
        iv_item = (ImageView) findViewById(R.id.iv_item);
        tv_item_title = (TextView) findViewById(R.id.tv_item_title);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);
        tv_ticket = (TextView) findViewById(R.id.tv_ticket);
        iv_arrow_right = (ImageView) findViewById(R.id.iv_arrow_right);
        view_line = findViewById(R.id.view_line);
    }

    /**
     * 左边标题显示
     * @param res
     */
    public void setItemImage(int res)
    {
       iv_item.setImageResource(res);
    }

    /**
     * 标题显示
     * @param text
     */
    public void setItemTitle(String text)
    {
        SDViewBinder.setTextView(tv_item_title,text);
    }

    /**
     * 右边标题显示
     * @param text
     */
    public void setItemRightTitle(String text)
    {
        SDViewUtil.setVisible(tv_right_title);
        SDViewBinder.setTextView(tv_right_title,text);
    }

    /**
     * 右边印票标题显示
     * @param isShow
     */
    public void setIsShowItemRightTicketTitle(boolean isShow)
    {
        if (isShow)
        {
            SDViewUtil.setVisible(tv_ticket);
        }else
        {
            SDViewUtil.setGone(tv_ticket);
        }
    }

    /**
     * 横线是否显示
     * @param isShow
     */
    public void setIsShowLine(boolean isShow)
    {
        if (isShow)
        {
            SDViewUtil.setVisible(view_line);
        }else
        {
            SDViewUtil.setGone(view_line);
        }
    }

    /**
     * 箭头是否显示
     * @param isShow
     */
    public void setIsShowRightArrow(boolean isShow)
    {
        if (isShow)
        {
            SDViewUtil.setVisible(iv_arrow_right);
        }else
        {
            SDViewUtil.setInvisible(iv_arrow_right);
        }
    }
}
