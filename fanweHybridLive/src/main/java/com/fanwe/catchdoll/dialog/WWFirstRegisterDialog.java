package com.fanwe.catchdoll.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWRechargeActivity;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.live.R;

/**
 * <新用户注册送钻石>
 * Created by wwb on 2017/12/8 15:07.
 */

public class WWFirstRegisterDialog extends SDDialogBase
{
    public WWFirstRegisterDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private TextView tv_title;

    private void init()
    {
        setContentView(R.layout.ww_dlg_new_user_send_diamond);

        tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);
        TextView tvConfirm = (TextView) findViewById(R.id.tv_confirm);

        setFullScreen();

        tvConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getOwnerActivity(), WWRechargeActivity.class);
                getOwnerActivity().startActivity(i);
                dismiss();
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

    public TextView getTv_title()
    {
        return tv_title;
    }

}
