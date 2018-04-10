package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.event.WWECatchDoll;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;

import de.greenrobot.event.EventBus;


/**
 * <我的娃娃空列表>
 * Created by wang on 2017/11/14 10:45.
 */

public class WWMainMyDollListEmptyView extends WWEmptyView
{

    private TextView tv_go_catch;

    public WWMainMyDollListEmptyView(Context context)
    {
        super(context);
        init();
    }

    public WWMainMyDollListEmptyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainMyDollListEmptyView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.ww_view_main_mydoll_empty;
    }

    private void init()
    {
        SDViewBinder.setTextView(getTextHint(),"空空如也～");
        tv_go_catch = (TextView) findViewById(R.id.tv_go_catch);
        tv_go_catch.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_go_catch)
        {
            launchToCatch();
        }
    }

    /**
     * 跳转到抓取页面(首页)
     */
    private void launchToCatch()
    {
        EventBus.getDefault().post(new WWECatchDoll());
    }


}
