package com.fanwe.catchdoll.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.fanwe.catchdoll.appview.WWMallDetailWebView;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
/**
 * <实物详情>
 * Created by wwb on 2017/12/26 15:55.
 */
public class WWMallDetailWebActivity extends BaseTitleActivity
{
    private WWMallDetailWebView mDetailWebView;

    /**
     * 详情id
     */
    public static final String EXTRA_ID = "extra_id";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_mall_detail_web);

        initView();
        initData();
    }

    private void initView()
    {
        mTitle.setMiddleTextTop("实物详情");
        mDetailWebView = new WWMallDetailWebView(this);
        FrameLayout fl_root = (FrameLayout) findViewById(R.id.fl_root);
        SDViewUtil.addView(fl_root,mDetailWebView);
    }

    private void initData()
    {
        getIntentData();
        mDetailWebView.setId(id);
    }

    private void getIntentData()
    {
        id = getIntent().getIntExtra(EXTRA_ID, 0);
    }


}
