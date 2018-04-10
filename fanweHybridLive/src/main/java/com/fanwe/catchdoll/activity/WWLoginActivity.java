package com.fanwe.catchdoll.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.common.CommonOpenLoginSDK;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.event.ERetryInitSuccess;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.lib.blocker.SDDurationBlocker;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveLoginBindMobileActivity;
import com.fanwe.live.activity.LiveMobielRegisterActivity;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.EFirstLoginNewLevel;
import com.fanwe.live.model.App_do_updateActModel;
import com.fanwe.live.model.UserModel;
import com.sunday.eventbus.SDEventManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by yhz on 2017/11/14. 抓娃娃登录页面
 */

public class WWLoginActivity extends BaseActivity
{
    private FrameLayout fl_mobile;
    private ImageView iv_mobile;
    private FrameLayout fl_qq;
    private ImageView iv_qq;
    private FrameLayout fl_weixin;
    private ImageView iv_weixin;
    private FrameLayout fl_sina;
    private ImageView iv_sina;

    private SDDurationBlocker blocker = new SDDurationBlocker(2000);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mIsExitApp = true;
        setFullScreen(true);
        setContentView(R.layout.ww_act_login);
        init( );
    }

    private void init()
    {
        initView( );
        initLoginIcon( );
    }

    private void initView()
    {
        fl_mobile = (FrameLayout) findViewById(R.id.fl_mobile);
        iv_mobile = (ImageView) findViewById(R.id.iv_mobile);
        fl_qq = (FrameLayout) findViewById(R.id.fl_qq);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        fl_weixin = (FrameLayout) findViewById(R.id.fl_weixin);
        iv_weixin = (ImageView) findViewById(R.id.iv_weixin);
        fl_sina = (FrameLayout) findViewById(R.id.fl_sina);
        iv_sina = (ImageView) findViewById(R.id.iv_sina);

        iv_mobile.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_sina.setOnClickListener(this);
    }

    private void initLoginIcon()
    {
        InitActModel model = InitActModelDao.query( );
        if (model != null)
        {
            //手机登录
            int has_mobile_login = model.getHas_mobile_login();
            if (has_mobile_login == 1)
            {
                SDViewUtil.setVisible(fl_mobile);
            } else
            {
                SDViewUtil.setGone(fl_mobile);
            }

            //QQ
            int has_qq_login = model.getHas_qq_login( );
            if (has_qq_login == 1)
            {
                SDViewUtil.setVisible(fl_qq);
            } else
            {
                SDViewUtil.setGone(fl_qq);
            }

            //微信
            int has_wx_login = model.getHas_wx_login( );
            if (has_wx_login == 1)
            {
                SDViewUtil.setVisible(fl_weixin);
            } else
            {
                SDViewUtil.setGone(fl_weixin);
            }

            //新浪微博
            int has_sina_login = model.getHas_sina_login();
            if (has_sina_login == 1)
            {
                SDViewUtil.setVisible(fl_sina);
            } else
            {
                SDViewUtil.setGone(fl_sina);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (blocker.block( ))
        {
            return;
        }

        switch (v.getId())
        {
            case R.id.iv_mobile:
                clickLoginShouJi();
                break;
            case R.id.iv_qq:
                clickLoginQQ();
                break;
            case R.id.iv_weixin:
                clickLoginWeiXing();
                break;
            case R.id.iv_sina:
                clickLoginSina();
                break;
            default:
                break;
        }
    }

    private void enableClickLogin(boolean enable)
    {
        iv_mobile.setClickable(enable);
        iv_weixin.setClickable(enable);
        iv_qq.setClickable(enable);
        iv_sina.setClickable(enable);
    }

    private void clickLoginShouJi()
    {
        Intent intent = new Intent(this, LiveMobielRegisterActivity.class);
        startActivity(intent);
    }

    private void clickLoginWeiXing()
    {
        CommonOpenLoginSDK.loginWx(this, wxListener);
    }

    /**
     * 微信授权监听
     */
    private UMAuthListener wxListener = new UMAuthListener( )
    {

        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map)
        {
            SDToast.showToast("授权成功");
            String openid = map.get("openid");
            String access_token = map.get("access_token");
            requestWeiXinLogin(openid, access_token);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable)
        {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i)
        {

        }
    };

    private void clickLoginQQ()
    {
        CommonOpenLoginSDK.umQQlogin(this, qqListener);
    }

    /**
     * qq授权监听
     */
    private UMAuthListener qqListener = new UMAuthListener( )
    {
        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data)
        {
            SDToast.showToast("授权成功");
            String openid = data.get("openid");
            String access_token = data.get("access_token");
            requestQQ(openid, access_token);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t)
        {
            SDToast.showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action)
        {
            SDToast.showToast("授权取消");
        }
    };

    private void clickLoginSina()
    {
        CommonOpenLoginSDK.umSinalogin(this, sinaListener);
    }

    /**
     * 新浪授权监听
     */
    private UMAuthListener sinaListener = new UMAuthListener()
    {
        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data)
        {
            SDToast.showToast("授权成功");
            String access_token = data.get("access_token");
            String uid = data.get("uid");
            requestSinaLogin(access_token, uid);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t)
        {
            SDToast.showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action)
        {
            SDToast.showToast("授权取消");
        }
    };

    private void requestWeiXinLogin(final String openid, final String access_token)
    {
        CommonInterface.requestWxLogin(openid, access_token, new AppRequestCallback<App_do_updateActModel>( )
        {
            @Override
            protected void onStart()
            {
                super.onStart( );
                enableClickLogin(false);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                enableClickLogin(true);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus( ) == 1)
                {
                    if (actModel.getNeed_bind_mobile( ) == 1)
                    {
                        startBindMobileActivity(WWLoginActivity.LoginType.WX_LOGIN, openid, access_token);
                    } else
                    {
                        startMainActivity(actModel);
                    }
                    setFirstLoginAndNewLevel(actModel);
                }
            }
        });
    }

    private void requestQQ(final String openid, final String access_token)
    {
        CommonInterface.requestQqLogin(openid, access_token, new AppRequestCallback<App_do_updateActModel>( )
        {
            @Override
            protected void onStart()
            {
                super.onStart( );
                enableClickLogin(false);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                enableClickLogin(true);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus( ) == 1)
                {
                    if (actModel.getNeed_bind_mobile( ) == 1)
                    {
                        startBindMobileActivity(WWLoginActivity.LoginType.QQ_LOGIN, openid, access_token);
                    } else
                    {
                        startMainActivity(actModel);
                    }
                    setFirstLoginAndNewLevel(actModel);
                }
            }
        });
    }

    private void requestSinaLogin(final String access_token, final String uid)
    {
        CommonInterface.requestSinaLogin(access_token, uid, new AppRequestCallback<App_do_updateActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                enableClickLogin(false);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                enableClickLogin(true);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    if (actModel.getNeed_bind_mobile() == 1)
                    {
                        startBindMobileActivity(LoginType.SINA_LOGIN, uid, access_token);
                    } else
                    {
                        startMainActivity(actModel);
                    }
                    setFirstLoginAndNewLevel(actModel);
                }
            }
        });
    }

    private void setFirstLoginAndNewLevel(App_do_updateActModel actModel)
    {
        InitActModel initActModel = InitActModelDao.query( );
        initActModel.setFirst_login(actModel.getFirst_login( ));
        initActModel.setNew_level(actModel.getNew_level( ));
        InitActModelDao.insertOrUpdate(initActModel);
        //发送事件首次登陆升级
        EFirstLoginNewLevel event = new EFirstLoginNewLevel( );
        SDEventManager.post(event);
    }

    private void startMainActivity(App_do_updateActModel actModel)
    {
        UserModel user = actModel.getUser_info( );
        if (user != null)
        {
            if (UserModel.dealLoginSuccess(user, true))
            {
                InitBusiness.startMainActivity(WWLoginActivity.this);
            }
        }
    }

    public void onEventMainThread(ERetryInitSuccess event)
    {
        initLoginIcon( );
    }

    private void startBindMobileActivity(String loginType, String openid, String access_token)
    {
        Intent intent = new Intent(getActivity( ), LiveLoginBindMobileActivity.class);
        intent.putExtra(LiveLoginBindMobileActivity.EXTRA_LOGIN_TYPE, loginType);
        intent.putExtra(LiveLoginBindMobileActivity.EXTRA_OPEN_ID, openid);
        intent.putExtra(LiveLoginBindMobileActivity.EXTRA_ACCESS_TOKEN, access_token);
        startActivity(intent);
    }


    public static final class LoginType
    {
        private static final String QQ_LOGIN = "qq_login";
        private static final String WX_LOGIN = "wx_login";
        private static final String SINA_LOGIN = "sina_login";
    }
}
