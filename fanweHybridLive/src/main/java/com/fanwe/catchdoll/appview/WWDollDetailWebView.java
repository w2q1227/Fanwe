package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.webview.CustomWebView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

/**
 * des:娃娃详情
 * Created by yangwb
 * on 2017/12/7.
 */

public class WWDollDetailWebView extends BaseAppView
{
    public WWDollDetailWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWDollDetailWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWDollDetailWebView(Context context)
    {
        super(context);
        init();
    }

    private CustomWebView web_view;

    private String mUrl;

    public void setUrl(String url)
    {
        this.mUrl = url;
        loadUrl();
    }

    private void init()
    {
        setContentView(R.layout.ww_view_doll_detail_web);

        web_view = (CustomWebView) findViewById(R.id.web_view);

        getPullToRefreshViewWrapper().setModePullFromHeader();
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                loadUrl();
            }

            @Override
            public void onRefreshingFromFooter()
            {

            }
        });
    }

    private void loadUrl()
    {
        web_view.loadUrl(mUrl);
        getPullToRefreshViewWrapper().stopRefreshing();
    }
}
