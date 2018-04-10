package com.fanwe.catchdoll.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.catchdoll.appview.WWMainDiscoveryView;
import com.fanwe.catchdoll.appview.WWMainMallView;
import com.fanwe.catchdoll.appview.WWMainMyDollView;
import com.fanwe.catchdoll.appview.main.WWMainBottomNavigationView;
import com.fanwe.catchdoll.appview.main.WWMainHomeView;
import com.fanwe.catchdoll.appview.main.WWMainMeView;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.dialog.WWFirstRegisterDialog;
import com.fanwe.catchdoll.dialog.WWMainSignDialog;
import com.fanwe.catchdoll.event.WWECatchDoll;
import com.fanwe.catchdoll.event.WWEMainMyDoll;
import com.fanwe.catchdoll.model.WWFirstRegisterSendGiftActModel;
import com.fanwe.catchdoll.model.WWSignInfoActModel;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.service.AppUpgradeHelper;
import com.fanwe.lib.dialog.ISDDialogConfirm;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dialog.common.AppDialogConfirm;
import com.fanwe.live.event.EIMLoginError;
import com.fanwe.live.event.EImOnForceOffline;
import com.fanwe.live.event.EReSelectTabLiveBottom;

import de.greenrobot.event.EventBus;

/**
 * 主页
 * Created by LianCP on 2017/11/13.
 */
public class WWMainActivity extends BaseActivity
{
    @Override
    protected int onCreateContentView()
    {
        return R.layout.ww_act_main;
    }

    private FrameLayout fl_main_content;
    private WWMainHomeView mMainHomeView;
    private WWMainMeView mMainMeView;
    private WWMainMyDollView mMainMyDollView;
    private WWMainDiscoveryView mMainDiscoveryView;
    private WWMainMallView mMainMallView;
    private WWMainBottomNavigationView mBottomNavigationView;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        fl_main_content = (FrameLayout) findViewById(R.id.fl_main_content);
        mIsExitApp = true;
        checkUpdate();
        AppRuntimeWorker.startContext();
        CommonInterface.requestUser_apns(null);
        CommonInterface.requestMyUserInfo(null);

        initTabs();
        initLoginFirstSignDialog();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
    }

    private void checkUpdate()
    {
        new AppUpgradeHelper(this).check(0);
    }

    private void initTabs()
    {
        mBottomNavigationView = (WWMainBottomNavigationView) findViewById(R.id.view_bottom_navigation);

        mBottomNavigationView.showOrHideTabRecharge(!isOpenScoreShop());
        mBottomNavigationView.showOrHideTabShop(isOpenScoreShop());

        mBottomNavigationView.setCallback(new WWMainBottomNavigationView.Callback()
        {
            @Override
            public void onTabSelected(int index)
            {
                switch (index)
                {
                    case WWMainBottomNavigationView.INDEX_HOME:
                        onSelectTabHome();
                        break;
                    case WWMainBottomNavigationView.INDEX_MY_DOLL:
                        onSelectTabMyDoll();
                        break;
                    case WWMainBottomNavigationView.INDEX_SCORE_SHOP:
                        onSelectTabShop();
                        break;
                    case WWMainBottomNavigationView.INDEX_FIND:
                        onSelectTabFind();
                        break;
                    case WWMainBottomNavigationView.INDEX_ME:
                        onSelectTabMe();
                        break;
                }
            }

            @Override
            public void onTabReselected(int index)
            {
                EReSelectTabLiveBottom event = new EReSelectTabLiveBottom();
                event.index = index;
                EventBus.getDefault().post(event);
            }

            @Override
            public void onClickRecharge(View view)
            {
                WWMainActivity.this.onClickRecharge();
            }
        });

        mBottomNavigationView.selectTab(WWMainBottomNavigationView.INDEX_HOME);
    }

    /**
     * 主页
     */
    protected void onSelectTabHome()
    {
        SDViewUtil.toggleView(fl_main_content, getMainHomeView());
    }

    /**
     * 发现
     */
    protected void onSelectTabFind()
    {
        SDViewUtil.toggleView(fl_main_content, getMainDiscoveryView());
    }

    /**
     * 积分商城
     */
    protected void onSelectTabShop()
    {
        SDViewUtil.toggleView(fl_main_content, getMainMallView());
    }

    /**
     * 我的娃
     */
    protected void onSelectTabMyDoll()
    {
        SDViewUtil.toggleView(fl_main_content, getMainMyDollView());
    }

    /**
     * 个人中心
     */
    protected void onSelectTabMe()
    {
        SDViewUtil.toggleView(fl_main_content, getMainMeView());
    }

    /**
     * 点击充值
     */
    private void onClickRecharge()
    {
        //跳转到充值界面
        Intent intent = new Intent(getActivity(), WWRechargeActivity.class);
        startActivity(intent);
    }

    public WWMainHomeView getMainHomeView()
    {
        if (mMainHomeView == null)
        {
            mMainHomeView = new WWMainHomeView(this);
        }
        return mMainHomeView;
    }

    public WWMainMeView getMainMeView()
    {
        if (mMainMeView == null)
        {
            mMainMeView = new WWMainMeView(this);
        }
        return mMainMeView;
    }


    public WWMainMyDollView getMainMyDollView()
    {
        if (mMainMyDollView == null)
        {
            mMainMyDollView = new WWMainMyDollView(this);
        }
        return mMainMyDollView;
    }

    public WWMainDiscoveryView getMainDiscoveryView()
    {
        if (mMainDiscoveryView == null)
        {
            mMainDiscoveryView = new WWMainDiscoveryView(this);
        }
        return mMainDiscoveryView;
    }

    public WWMainMallView getMainMallView()
    {
        if (mMainMallView == null)
        {
            mMainMallView = new WWMainMallView(this);
        }
        return mMainMallView;
    }


    public void onEventMainThread(EIMLoginError event)
    {
        AppDialogConfirm dialog = new AppDialogConfirm(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        String content = "登录IM失败";
        if (!TextUtils.isEmpty(event.errMsg))
        {
            content = content + (event.errCode + event.errMsg);
        }
        dialog.setTextContent(content).setTextCancel("退出").setTextConfirm("重试");
        dialog.setCallback(new ISDDialogConfirm.Callback()
        {
            @Override
            public void onClickCancel(View v, SDDialogBase dialog)
            {
                App.getApplication().logout(false);
            }

            @Override
            public void onClickConfirm(View v, SDDialogBase dialog)
            {
                AppRuntimeWorker.startContext();
            }
        }).show();
    }

    /**
     * 异地登录
     *
     * @param event
     */
    public void onEventMainThread(EImOnForceOffline event)
    {
        AppDialogConfirm dialog = new AppDialogConfirm(this);
        dialog.setTextContent("你的帐号在另一台手机上登录");
        dialog.setTextCancel("退出");
        dialog.setTextConfirm("重新登录");
        dialog.setCallback(new ISDDialogConfirm.Callback()
        {
            @Override
            public void onClickCancel(View v, SDDialogBase dialog)
            {
                App.getApplication().logout(true);
            }

            @Override
            public void onClickConfirm(View v, SDDialogBase dialog)
            {
                AppRuntimeWorker.startContext();
            }
        }).show();
    }


    public void onEventMainThread(WWECatchDoll model)
    {
        mBottomNavigationView.selectTab(WWMainBottomNavigationView.INDEX_HOME);
    }

    public void onEventMainThread(WWEMainMyDoll model)
    {
        mBottomNavigationView.selectTab(WWMainBottomNavigationView.INDEX_MY_DOLL);
    }


    //首次登陆奖励
    private void initLoginFirstSignDialog()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            //首次登陆签到开关是否开启;是否是每日首次登录
            if (model.getOpen_login_send_diamonds() == 1 && model.getFirst_login() == 1)
            {
                requestSignData();
                model.setFirst_login(0);
                InitActModelDao.insertOrUpdate(model);
            } else
            {
                initRegisterFirstSignDialog();
            }
        }
    }

    private void requestSignData()
    {
        WWCommonInterface.requestSignInfo(new AppRequestCallback<WWSignInfoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    showSignDialog(actModel);
                }
            }
        });
    }

    private void showSignDialog(final WWSignInfoActModel actModel)
    {
        WWMainSignDialog mainSignDialog = new WWMainSignDialog(WWMainActivity.this);
        mainSignDialog.setWWSignInfoActModel(actModel);
        mainSignDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                initRegisterFirstSignDialog();
            }
        });
        mainSignDialog.show();
    }


    private boolean isOpenScoreShop()
    {
        return InitActModelDao.query().getOpen_score_shop() == 1;
    }


    //首次注册奖励
    private void initRegisterFirstSignDialog()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            if (model.getOpen_register_gift() == 1)
            {   //首次注册送礼物
                requestFirstRegisterSendGift();
            }
        }
    }

    //首次注册奖励
    private void requestFirstRegisterSendGift()
    {
        WWCommonInterface.requestFirstRegisterSendGift(new AppRequestCallback<WWFirstRegisterSendGiftActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk() && actModel.getFirst_register() == 1)
                {
                    showFirstRegisterDialog();
                }
            }
        });
    }

    //首次注册奖励
    private void showFirstRegisterDialog()
    {
        InitActModel model = InitActModelDao.query();
        WWFirstRegisterDialog dialog = new WWFirstRegisterDialog(this);
        SDViewBinder.setTextView(dialog.getTv_title(), "新用户注册即送" + model.getRegister_gift_diamonds());
        dialog.show();
    }


}
