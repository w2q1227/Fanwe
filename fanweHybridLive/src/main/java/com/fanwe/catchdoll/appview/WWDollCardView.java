package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.utils.GlideUtil;

/**
 * Created by Administrator on 2017/12/7.
 */
public class WWDollCardView extends BaseAppView
{
    public WWDollCardView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWDollCardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWDollCardView(Context context)
    {
        super(context);
        init();
    }

    private ImageView iv_receive_img;
    private TextView tv_good_name;
    private TextView tv_good_time;
    private TextView tv_tab;
    private void init()
    {
        setContentView(R.layout.ww_view_doll_card);
        iv_receive_img = (ImageView) findViewById(R.id.iv_receive_img);
        tv_good_name = (TextView) findViewById(R.id.tv_good_name);
        tv_good_time = (TextView) findViewById(R.id.tv_good_time);
        tv_tab = (TextView) findViewById(R.id.tv_tab);
    }

    public void setData(String imgUrl, String dollName, String time)
    {
        GlideUtil.load(imgUrl).into(iv_receive_img);
        SDViewBinder.setTextView(tv_good_name, dollName);
        SDViewBinder.setTextView(tv_good_time, time);
    }

    public TextView getTv_tab()
    {
        return tv_tab;
    }
}
