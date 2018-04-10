package com.fanwe.catchdoll.appview.videoHolder;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fanwe.catchdoll.control.WWLiveView;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.lib.log.FLogger;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.catchdoll.appview.WWBaseCountDownView;
import com.fanwe.catchdoll.appview.WWBaseVideoHolderView;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomMsgView;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/11/14.
 */

public class WWLiveVideoHolderView extends WWBaseVideoHolderView
{

    private RoomMsgView roomMsgView;
    private TextView tvCountDown;
    private TextView tvMsgControl;
    private TextView tvUserName;
    private TextView tvStatus;
    private Button btnCamera;
    private Button btnRepair;
    private RelativeLayout relUserInfo;
    private ImageView ivUserImg;

    private int roundTime;



    private Callback mCallback;

    public WWLiveVideoHolderView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        setContentView(R.layout.ww_live_video_holder);
        getSubVideoView().setVisibility(GONE);
        btnCamera = (Button) findViewById(R.id.btn_camera);
        btnRepair = (Button) findViewById(R.id.btn_repair);
        tvCountDown = (TextView) findViewById(R.id.tv_count_down);
        tvMsgControl = (TextView) findViewById(R.id.tv_msg_control);
        roomMsgView = (RoomMsgView) findViewById(R.id.view_msg);
        relUserInfo = (RelativeLayout) findViewById(R.id.rel_user_info);
        ivUserImg = (ImageView) findViewById(R.id.iv_user_img);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvStatus = (TextView) findViewById(R.id.tv_status);


        roomMsgView.setLayoutParams(new LinearLayout.LayoutParams(
                SDViewUtil.getScreenWidthPercent(0.784f),
                SDViewUtil.getScreenHeightPercent(0.254f)));
        btnCamera.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchPlayer();
            }
        });

        btnRepair.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mCallback != null)
                {
                    mCallback.onClickRepair();
                }
            }
        });

        btnRepair.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {

                Intent shareFileIntent = new Intent(Intent.ACTION_SEND);
                shareFileIntent.setPackage(SDPackageUtil.getPackageName());
                shareFileIntent.setType("*/*");
                shareFileIntent.putExtra(Intent.EXTRA_TEXT, "分享到微信的内容");
                v.getContext().startActivity(shareFileIntent);
                return true;
            }
        });

        tvMsgControl.setTag(true);
        tvMsgControl.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean isShow = (Boolean) v.getTag();
                if (isShow)
                {
                    tvMsgControl.setText("显示发言");
                    SDViewUtil.setGone(roomMsgView);
                    v.setTag(false);
                } else
                {
                    tvMsgControl.setText("隐藏发言");
                    SDViewUtil.setVisible(roomMsgView);
                    v.setTag(true);
                }
            }
        });

        relUserInfo.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int userId = SDTypeParseUtil.getInt((String) v.getTag());
                if (mCallback != null)
                {
                    mCallback.onClickUser(userId);
                }
            }
        });
    }


    public void setCallback(Callback callback)
    {
        mCallback = callback;
        setCallBack(mCallback);
    }

    public void setRoundTime(int roundTime)
    {
        this.roundTime = roundTime;
    }

    public void startCountDown()
    {
        startCountDown(roundTime);
        SDViewUtil.setVisible(tvCountDown);
    }

    @Override
    public void stopCountDown()
    {
        super.stopCountDown();
        SDViewUtil.setGone(tvCountDown);
    }

    public void setAcceptor(UserModel userModel)
    {
        setUser(userModel);
        tvStatus.setText("预约确认中");
    }

    public void setGamer(UserModel userModel)
    {
        setUser(userModel);
        tvStatus.setText("热抓中");
    }

    private void setUser(UserModel user)
    {
        if (user == null)
        {
            clearUser();
            return;
        }
        relUserInfo.setTag(user.getUser_id());
        SDViewUtil.setVisible(relUserInfo);
        tvUserName.setText(user.getNick_name());
        GlideUtil.loadHeadImage(user.getHead_image()).into(ivUserImg);
    }

    public void clearUser()
    {
        SDViewUtil.setGone(relUserInfo);
    }

    public void clearConfirmUser()
    {
        if (tvStatus.getText().equals("预约确认中"))
        {
            clearUser();
        }
    }

    public void onGameView()
    {
        SDViewUtil.setVisible(btnCamera);
    }

    public void onWatcherView()
    {
        if (LiveInformation.getInstance().getRoomInfo().getIsSide())
        {
            SDViewUtil.setVisible(btnCamera);
        } else
        {
            SDViewUtil.setGone(btnCamera);
            LiveInformation.getInstance().getSubPlayerConfig().setIgnoreNonAcc(true);
        }
    }

    @Override
    protected void onCount(long second)
    {
        SpannableString str = new SpannableString(String.format("%d秒", second));
        str.setSpan(new AbsoluteSizeSpan(12, true), str.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCountDown.setText(str);
    }

    @Override
    protected FrameLayout getVideoContent()
    {
        return (FrameLayout) findViewById(R.id.fl_video_content);
    }

    public interface Callback extends WWBaseCountDownView.Callback
    {
        void onClickRepair();

        void onClickUser(int userId);
    }
}
