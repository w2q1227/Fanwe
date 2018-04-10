package com.fanwe.catchdoll.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWMainIntegralMallModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;

/**
 * <兑换钻石>
 * Created by wwb on 2017/12/8 15:07.
 */

public class WWExchangeDiamondsDialog extends SDDialogBase
{
    public WWExchangeDiamondsDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private TextView tv_content;
    private WWMainIntegralMallModel mModel;


    private void init()
    {
        setContentView(R.layout.ww_dlg_exchange_diamond);

        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);
        TextView tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tv_content = (TextView) findViewById(R.id.tv_content);

        setFullScreen();

        tvConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                requestData();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }


    public void setModel(WWMainIntegralMallModel model)
    {
        this.mModel = model;

        String content = "确认使用"+model.getScore_info()+model.getDiamonds_info();

        SDViewBinder.setTextView(tv_content, content);
    }


    private void requestData()
    {
        ((SDBaseActivity) getOwnerActivity()).showProgressDialog("正在兑换...");
        WWCommonInterface.requestSaveExchangeLog(mModel.getId(), mModel.getScore(), mModel.getDiamonds(), new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    SDToast.showToast("兑换成功");
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                ((SDBaseActivity) getOwnerActivity()).dismissProgressDialog();
            }
        });

    }


}
