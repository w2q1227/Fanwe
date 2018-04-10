package com.fanwe.catchdoll.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEUserInfoChange;
import com.fanwe.catchdoll.model.WWValidateInviteCodeActModel;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;

import de.greenrobot.event.EventBus;

/**
 * <输入邀请码>
 * Created by wwb on 2017/12/13 16:30.
 */

public class WWInputInvitationCodeActivity extends BaseActivity
{
    private EditText et_invite_code;
    private TextView tv_submit;
    private RelativeLayout rl_back;
    private String inviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_input_invitation_code);

        initView();
    }

    private void initView()
    {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        et_invite_code = (EditText) findViewById(R.id.et_invite_code);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        rl_back.setOnClickListener(this);

        UserModel userModel = UserModelDao.query();

        tv_submit.setEnabled(TextUtils.isEmpty(userModel.getInvite_code_written()));
        et_invite_code.setEnabled(TextUtils.isEmpty(userModel.getInvite_code_written()));

        if (!TextUtils.isEmpty(userModel.getInvite_code_written()))
        {
            SDViewBinder.setTextView(et_invite_code, "您已填写邀请码:" + userModel.getInvite_code_written());
        }
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_submit)
        {
            submitInviteCode();
        } else if (v == rl_back)
        {
            finish();
        }
    }


    private void submitInviteCode()
    {
        inviteCode = et_invite_code.getText().toString().trim();

        if (TextUtils.isEmpty(inviteCode))
        {
            SDToast.showToast("请输入邀请码");
            return;
        }

        showProgressDialog("");
        WWCommonInterface.requestInviteCode(inviteCode, new AppRequestCallback<WWValidateInviteCodeActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    inviteSuccess();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    private void inviteSuccess()
    {
        SDToast.showToast("验证成功");
        tv_submit.setEnabled(false);
        et_invite_code.setEnabled(false);
        SDViewBinder.setTextView(et_invite_code, "您已填写邀请码:" + inviteCode);
        notifyUserInfoChange();
    }

    private void notifyUserInfoChange()
    {
        EventBus.getDefault().post(new WWEUserInfoChange());
    }


}
