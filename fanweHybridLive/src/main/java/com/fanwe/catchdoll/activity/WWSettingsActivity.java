package com.fanwe.catchdoll.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWSettingsModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.service.AppUpgradeHelper;
import com.fanwe.lib.switchbutton.ISDSwitchButton;
import com.fanwe.lib.switchbutton.SDSwitchButton;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.live.R;

/**
 * des:设置
 * Created by yangwb
 * on 2017/11/14.
 */

public class WWSettingsActivity extends BaseTitleActivity
{
    private SDSwitchButton btn_bgm;
    private SDSwitchButton btn_sound_effect;
    private RelativeLayout rl_update;
    private TextView tv_version;
    private TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_setting);

        initTitle();
        initView();
        initData();
        initListener();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("设置");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    private void initView()
    {
        btn_bgm = (SDSwitchButton) findViewById(R.id.btn_bgm);
        btn_sound_effect = (SDSwitchButton) findViewById(R.id.btn_sound_effect);
        rl_update = (RelativeLayout) findViewById(R.id.rl_update);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
    }

    private void initData()
    {
        tv_version.setText(SDPackageUtil.getVersionName());

        final WWSettingsModel model = WWSettingsModel.get();
        if (model.isPlayBGM())
        {
            btn_bgm.setChecked(true,false,false);
        }else
        {
            btn_bgm.setChecked(false,false,false);
        }
        if (model.isPlaySoundEffect())
        {
            btn_sound_effect.setChecked(true,false,false);
        }else
        {
            btn_sound_effect.setChecked(false,false,false);
        }
        btn_bgm.setOnCheckedChangedCallback(new ISDSwitchButton.OnCheckedChangedCallback()
        {
            @Override
            public void onCheckedChanged(boolean check, SDSwitchButton button)
            {
                model.setPlayBGM(check);
                model.save();
            }
        });
        btn_sound_effect.setOnCheckedChangedCallback(new ISDSwitchButton.OnCheckedChangedCallback()
        {
            @Override
            public void onCheckedChanged(boolean check, SDSwitchButton button)
            {
                model.setPlaySoundEffect(check);
                model.save();
            }
        });
    }

    private void initListener()
    {
        rl_update.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == rl_update)
        {
            checkVersion();
        }else if (v == tv_logout)
        {
            clickLogout();
        }
    }

    /**
     * 检查版本
     */
    private void checkVersion()
    {
        new AppUpgradeHelper(this).check(1);
    }

    /**
     * 退出登录
     */
    private void clickLogout()
    {
        App.getApplication().logout(true);
    }
}
