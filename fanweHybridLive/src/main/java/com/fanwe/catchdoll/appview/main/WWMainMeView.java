package com.fanwe.catchdoll.appview.main;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWAccountLogActivity;
import com.fanwe.catchdoll.activity.WWInputInvitationCodeActivity;
import com.fanwe.catchdoll.activity.WWInviteFriendsActivity;
import com.fanwe.catchdoll.activity.WWMyExchangeThingsActivity;
import com.fanwe.catchdoll.activity.WWRechargeActivity;
import com.fanwe.catchdoll.activity.WWSettingsActivity;
import com.fanwe.catchdoll.activity.WWTreasureBoxActivity;
import com.fanwe.catchdoll.appview.WWMeItemView;
import com.fanwe.catchdoll.common.WWAppRuntimeWorker;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.dialog.WWMainSignDialog;
import com.fanwe.catchdoll.event.WWEUserInfoChange;
import com.fanwe.catchdoll.model.WWSignInfoActModel;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

/**
 * des:娃娃个人中心
 * Created by yangwb
 * on 2017/11/13.
 */

public class WWMainMeView extends BaseAppView
{
    public WWMainMeView(Context context)
    {
        super(context);
        init();
    }

    public WWMainMeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainMeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private CircleImageView iv_head;
    private CircleImageView iv_level;
    private TextView tv_user_name;
    private TextView tv_diamond;
    private TextView tv_score;
    private TextView tv_user_id;
    private WWMeItemView view_bill;
    private WWMeItemView view_recharge;
    private WWMeItemView view_my_things;
    private WWMeItemView view_point;
    private WWMeItemView view_invite_friend;
    private WWMeItemView view_invite_code;
    private WWMeItemView view_everyday_sign;
    private WWMeItemView view_about_us;
    private WWMeItemView view_setting;

    private void init()
    {
        setContentView(R.layout.ww_view_main_me_new);

        initView();
        initData();
        initListener();
    }

    private void initView()
    {
        iv_head = (CircleImageView) findViewById(R.id.iv_head);
        iv_level = (CircleImageView) findViewById(R.id.iv_level);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_diamond = (TextView) findViewById(R.id.tv_diamond);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_user_id = (TextView) findViewById(R.id.tv_user_id);
        view_bill = (WWMeItemView) findViewById(R.id.view_bill);
        view_recharge = (WWMeItemView) findViewById(R.id.view_recharge);
        view_my_things = (WWMeItemView) findViewById(R.id.view_my_things);
        view_point = (WWMeItemView) findViewById(R.id.view_point);
        view_invite_friend = (WWMeItemView) findViewById(R.id.view_invite_friend);
        view_invite_code = (WWMeItemView) findViewById(R.id.view_invite_code);
        view_everyday_sign = (WWMeItemView) findViewById(R.id.view_everyday_sign);
        view_about_us = (WWMeItemView) findViewById(R.id.view_about_us);
        view_setting = (WWMeItemView) findViewById(R.id.view_setting);
    }

    private void initData()
    {
        if (WWAppRuntimeWorker.getHas_billing() == 1)
        {
            SDViewUtil.setVisible(view_bill);
            view_bill.setItemImage(R.drawable.ww_ic_account_log);
            view_bill.setItemTitle("账单明细");
        } else
        {
            SDViewUtil.setGone(view_bill);
        }

        view_recharge.setItemImage(R.drawable.ww_ic_recharge);
        view_recharge.setItemTitle("我要充值");

        view_my_things.setItemImage(R.drawable.ww_ic_my_things);
        view_my_things.setItemTitle("我的实物");


        if (WWAppRuntimeWorker.getHas_score() == 1)
        {
            SDViewUtil.setVisible(view_point);
            view_point.setItemImage(R.drawable.ww_ic_point_box);
            view_point.setItemTitle("积分宝箱");
        } else
        {
            SDViewUtil.setGone(view_point);
        }

        if (WWAppRuntimeWorker.getHas_invitation() == 1)
        {
            SDViewUtil.setVisible(view_invite_friend);
            view_invite_friend.setItemImage(R.drawable.ww_ic_invite_friend);
            view_invite_friend.setItemTitle("邀请好友");

            SDViewUtil.setVisible(view_invite_code);
            view_invite_code.setItemImage(R.drawable.ww_ic_invite_code);
            view_invite_code.setItemTitle("输入邀请码");
        } else
        {
            SDViewUtil.setGone(view_invite_friend);
            SDViewUtil.setGone(view_invite_code);
        }

        view_everyday_sign.setItemImage(R.drawable.ww_ic_everyday_sign);
        view_everyday_sign.setItemTitle("每日签到");

        view_about_us.setItemImage(R.drawable.ww_ic_about_us);
        view_about_us.setItemTitle("关于我们");

        view_setting.setItemImage(R.drawable.ww_ic_setting);
        view_setting.setItemTitle("设置");

        refreshData();
    }

    private void initListener()
    {
        iv_head.setOnClickListener(this);
        view_bill.setOnClickListener(this);
        view_recharge.setOnClickListener(this);
        view_my_things.setOnClickListener(this);
        view_point.setOnClickListener(this);
        view_invite_friend.setOnClickListener(this);
        view_invite_code.setOnClickListener(this);
        view_everyday_sign.setOnClickListener(this);
        view_about_us.setOnClickListener(this);
        view_setting.setOnClickListener(this);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);
        if (this == changedView && visibility == View.VISIBLE)
        {
            refreshData();
        }
    }

    public void refreshData()
    {
        request();
    }

    private void request()
    {
        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    UserModelDao.insertOrUpdate(actModel.getUser());
                    changeUI();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    private void changeUI()
    {
        UserModel userModel = UserModelDao.query();
        GlideUtil.loadHeadImage(userModel.getHead_image()).into(iv_head);
//        if (!TextUtils.isEmpty(userModel.getV_icon()))
//        {
//            GlideUtil.load(userModel.getV_icon()).into(iv_level);
//            SDViewUtil.setVisible(iv_level);
//        } else
//        {
//            SDViewUtil.setGone(iv_level);
//        }

        SDViewBinder.setTextView(tv_user_name, userModel.getNick_name());
        SDViewBinder.setTextView(tv_diamond, String.valueOf(userModel.getDiamonds()));
        SDViewBinder.setTextView(tv_score, String.valueOf(userModel.getScore()));
        SDViewBinder.setTextView(tv_user_id, userModel.getShowId());
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == iv_head)
        {

        } else if (v == view_bill)
        {
            clickBill();
        } else if (v == view_recharge)
        {
            clickRecharge();
        } else if (v == view_my_things)
        {
            clickMyThings();
        } else if (v == view_point)
        {
            clickTreasureBox();
        } else if (v == view_invite_friend)
        {
            clickInviteFriend();
        } else if (v == view_invite_code)
        {
            clickInviteCode();
        } else if (v == view_everyday_sign)
        {
            clickSign();
        } else if (v == view_about_us)
        {
            openAboutUs();
        } else if (v == view_setting)
        {
            clickSetting();
        }
    }

    /**
     * 积分宝箱
     */
    private void clickTreasureBox()
    {
        Intent intent = new Intent(getActivity(), WWTreasureBoxActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 邀请好友
     */
    private void clickInviteFriend()
    {
        Intent intent = new Intent(getActivity(), WWInviteFriendsActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 每日签到
     */
    private void clickSign()
    {
        requestSignData();
    }

    /**
     * 输入邀请码
     */
    private void clickInviteCode()
    {
        Intent intent = new Intent(getActivity(), WWInputInvitationCodeActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 账单明细
     */
    private void clickBill()
    {
        Intent intent = new Intent(getActivity(), WWAccountLogActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 充值
     */
    private void clickRecharge()
    {
        Intent intent = new Intent(getActivity(), WWRechargeActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 我的实物
     */
    private void clickMyThings()
    {
        Intent intent = new Intent(getActivity(), WWMyExchangeThingsActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 关于我们
     */
    private void openAboutUs()
    {
        Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, AppRuntimeWorker.getUrl_about_us());
        getActivity().startActivity(intent);
    }

    /**
     * 设置
     */
    private void clickSetting()
    {
        Intent intent = new Intent(getActivity(), WWSettingsActivity.class);
        getActivity().startActivity(intent);
    }


    public void onEventMainThread(WWEUserInfoChange event)
    {
        refreshData();
    }

    private void requestSignData()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            if (model.getOpen_login_send_diamonds() != 1)
            {//首次登陆签到开关是否开启 1 开启 0 关闭
                return;
            }
        }

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

    private void showSignDialog(WWSignInfoActModel actModel)
    {
        WWMainSignDialog mainSignDialog = new WWMainSignDialog(getActivity());
        mainSignDialog.setWWSignInfoActModel(actModel);
        mainSignDialog.show();
    }

}
