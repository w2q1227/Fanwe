package com.fanwe.catchdoll.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.appview.WWMainSignView;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEUserInfoChange;
import com.fanwe.catchdoll.model.WWSignInActModel;
import com.fanwe.catchdoll.model.WWSignInfoActModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * <签到弹窗>
 * Created by wwb on 2017/12/15 10:20.
 */

public class WWMainSignDialog extends SDDialogBase
{
    public WWMainSignDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_sign;
    private WWMainSignView signview;

    private void init()
    {
        setContentView(R.layout.ww_dlg_main_sign);
        paddingLeft(SDViewUtil.dp2px(35));
        paddingRight(SDViewUtil.dp2px(35));

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_sign = (TextView) findViewById(R.id.tv_sign);
        signview = (WWMainSignView) findViewById(R.id.sv);

        tv_sign.setOnClickListener(this);

        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_sign)
        {
            requestSign();
        }
    }

    public void setWWSignInfoActModel(WWSignInfoActModel actModel)
    {
        bindSignInfo(actModel);
    }

    private void bindSignInfo(WWSignInfoActModel actModel)
    {
        SDViewBinder.setTextView(tv_title, "第" + actModel.getTimes() + "日签到");
        SDViewBinder.setTextView(tv_content, actModel.getConten());
        tv_sign.setEnabled(actModel.getSign_in_btn() == 1);

        signview.setSignInfoMode(actModel.getSign_in_info());
    }


    private void requestSign()
    {
        WWCommonInterface.requestSignIn(new AppRequestCallback<WWSignInActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    signview.refreshSignStatus(actModel.getDays());
                    tv_sign.setEnabled(false);
                    EventBus.getDefault().post(new WWEUserInfoChange());
                    //延迟1s让用户看到效果后 消失
                    Observable.timer(1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>()
                            {
                                @Override
                                public void accept(@NonNull Long aLong) throws Exception
                                {
                                    dismiss();
                                }
                            });
                }
            }
        });

    }

}
