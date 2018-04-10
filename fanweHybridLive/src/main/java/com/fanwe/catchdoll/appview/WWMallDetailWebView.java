package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWMyExchangeThingsActivity;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEMallThingsRefresh;
import com.fanwe.catchdoll.model.WWExchangeThingDetailActModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.lib.dialog.ISDDialogConfirm;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.lib.dialog.impl.SDDialogConfirm;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.live.R;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

import de.greenrobot.event.EventBus;

/**
 * <商城实物详情>
 * Created by wwb on 2017/12/26 14:17.
 */

public class WWMallDetailWebView extends WWBaseAppView
{
    public WWMallDetailWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWMallDetailWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMallDetailWebView(Context context)
    {
        super(context);
        init();
    }

    private TextView tv_exchange;
    private CustomWebView web_view;

    private int id;
    private long score;

    private void init()
    {
        setContentView(R.layout.ww_view_mall_detail_web);

        tv_exchange = (TextView) findViewById(R.id.tv_exchange);
        tv_exchange.setOnClickListener(this);
        web_view = (CustomWebView) findViewById(R.id.web_view);

        getPullToRefreshViewWrapper().setModePullFromHeader();
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                requestData();
            }

            @Override
            public void onRefreshingFromFooter()
            {

            }
        });
    }

    public void setId(int id)
    {
        this.id = id;
        requestData();
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_exchange)
        {
            showConfirmDialog();
        }
    }


    private void requestData()
    {
        WWCommonInterface.requestExchangeThingDetail(id, new AppRequestCallback<WWExchangeThingDetailActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    web_view.loadUrl(actModel.getExchange_thing().getUrl());
                    score = actModel.getExchange_thing().getScore();
                    tv_exchange.setEnabled(true);
                } else if (actModel.getStatus() == 2)
                {
                    web_view.loadUrl(actModel.getExchange_thing().getUrl());
                    tv_exchange.setEnabled(false);
                    SDViewBinder.setTextView(tv_exchange, actModel.getError());
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                getPullToRefreshViewWrapper().stopRefreshing();
            }
        });
    }

    private void showConfirmDialog()
    {
        SDDialogConfirm dialog = new SDDialogConfirm(getActivity());
        dialog.setTextTitle("确认兑换").setTextContent("确认使用" + score + "积分兑换？");
        dialog.setCallback(new ISDDialogConfirm.Callback()
        {
            @Override
            public void onClickCancel(View view, SDDialogBase sdDialogBase)
            {

            }

            @Override
            public void onClickConfirm(View view, SDDialogBase sdDialogBase)
            {
                exchange();
            }
        });
        dialog.show();
    }


    /**
     * 兑换
     */
    private void exchange()
    {
        WWCommonInterface.requestSaveExchangeThing(id, score, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    SDToast.showToast("兑换成功");
                    refreshMallList();
                    Intent intent = new Intent(getActivity(), WWMyExchangeThingsActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    /**
     * 刷新列表数据
     */
    private void refreshMallList()
    {
        EventBus.getDefault().post(new WWEMallThingsRefresh());
    }

}
