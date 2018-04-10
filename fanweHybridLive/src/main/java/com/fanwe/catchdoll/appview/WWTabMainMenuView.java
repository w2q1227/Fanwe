package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.lib.select.config.SDSelectImageViewConfig;
import com.fanwe.lib.select.config.SDSelectTextViewConfig;
import com.fanwe.lib.select.view.SDSelectView;
import com.fanwe.live.R;

/**
 * 首页底部菜单栏Item项
 * Created by LianCP on 2017/11/13.
 */
public class WWTabMainMenuView extends SDSelectView
{
    public WWTabMainMenuView(Context context)
    {
        super(context);
        init();
    }

    public WWTabMainMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWTabMainMenuView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView iv_tab_image;
    private TextView tv_tab_name;

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.ww_view_tab_main_menu, this, true);
        iv_tab_image = (ImageView) findViewById(R.id.iv_tab_image);
        tv_tab_name = (TextView) findViewById(R.id.tv_tab_name);
    }

    public SDSelectImageViewConfig configImage()
    {
        return configImage(iv_tab_image);
    }

    public SDSelectTextViewConfig configTextView()
    {
        return configText(tv_tab_name);
    }

    public TextView getTabNameTextView()
    {
        return tv_tab_name;
    }
}
