package com.fanwe.catchdoll.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEUserInfoChange;
import com.fanwe.catchdoll.model.WWTreasureBoxActModel;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.lib.dialog.impl.SDDialogConfirm;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.UserModel;

import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * <积分宝箱页面>
 * Created by wwb on 2017/12/12 15:55.
 */

public class WWTreasureBoxActivity extends BaseActivity
{
    private RelativeLayout rl_back;
    private TextView tv_diamond;
    private TextView tv_user_score;
    private ImageView iv_treasure_box;
    private FrameLayout fl_open_box;
    private TextView tv_need_score;
    private TextView tv_left_num_info;
    private AnimationDrawable mAnimationDrawable;

    private long user_score;
    private long need_score;
    private int left_num;
    private SDDialogConfirm mDialogConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_treasure_box);

        initView();
        requestData();
    }

    private void initView()
    {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_diamond = (TextView) findViewById(R.id.tv_diamond);
        tv_user_score = (TextView) findViewById(R.id.tv_user_score);
        iv_treasure_box = (ImageView) findViewById(R.id.iv_treasure_box);
        fl_open_box = (FrameLayout) findViewById(R.id.fl_open_box);
        tv_need_score = (TextView) findViewById(R.id.tv_need_score);
        tv_left_num_info = (TextView) findViewById(R.id.tv_left_num_info);

        UserModel userModel = UserModelDao.query();
        user_score = userModel.getScore();
        SDViewBinder.setTextView(tv_diamond, String.valueOf(userModel.getDiamonds()));
        SDViewBinder.setTextView(tv_user_score, String.valueOf(userModel.getScore()));
        fl_open_box.setOnClickListener(this);
        rl_back.setOnClickListener(this);
    }

    private void requestData()
    {
        WWCommonInterface.requestTreasureBox(new AppRequestCallback<WWTreasureBoxActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    updateData(actModel);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    private void updateData(WWTreasureBoxActModel model)
    {
        SDViewBinder.setTextView(tv_left_num_info, model.getLeft_num_info());
        SDViewBinder.setTextView(tv_need_score, String.valueOf(model.getScore()));
        this.need_score = model.getScore();
        this.left_num = model.getLeft_num();
        enableOpen(left_num > 0 && UserModelDao.canScorePay(need_score));
    }

    private void enableOpen(boolean enable)
    {
        fl_open_box.setEnabled(enable);
    }


    /**
     * 请求打开宝箱接口
     */
    private void requestOpenBox()
    {
        WWCommonInterface.requestOpenTreasureBox(need_score, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    showResultDialog(actModel);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                requestData();
                recoveryFirst();
                notifyUserInfoChange();
                requestUserInfo();
            }
        });
    }

    private void openBox()
    {
        if (mAnimationDrawable == null)
        {
            mAnimationDrawable = (AnimationDrawable) iv_treasure_box.getDrawable();
        }
        startAnimation();
        //延迟一下等动画结束后调用
        Observable.timer(300, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>()
        {
            @Override
            public void accept(@NonNull Long aLong) throws Exception
            {
                requestOpenBox();
            }
        });
    }


    private void showResultDialog(BaseActModel model)
    {
        if (mDialogConfirm == null)
        {
            mDialogConfirm = new SDDialogConfirm(this);
            SDViewUtil.setGone(mDialogConfirm.tv_cancel);
            mDialogConfirm.setTextTitle("温馨提示")
                    .setTextConfirm("知道了");
        }
        mDialogConfirm.setTextContent(model.getError()).show();
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == fl_open_box)
        {
            openBox();
        } else if (v == rl_back)
        {
            finish();
        }
    }

    private void startAnimation()
    {
        if (!mAnimationDrawable.isRunning())
        {
            mAnimationDrawable.start();
        }
    }

    private void stopAnimation()
    {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning())
        {
            mAnimationDrawable.stop();
        }
    }

    /**
     * 帧动画恢复第一帧
     */
    private void recoveryFirst()
    {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning())
        {
            mAnimationDrawable.selectDrawable(0);
            mAnimationDrawable.stop();
        }
    }

    private void notifyUserInfoChange()
    {
        EventBus.getDefault().post(new WWEUserInfoChange());
    }

    private void requestUserInfo()
    {
        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    UserModelDao.insertOrUpdate(actModel.getUser());
                    UserModel userModel = actModel.getUser();
                    user_score = userModel.getScore();
                    SDViewBinder.setTextView(tv_diamond, String.valueOf(userModel.getDiamonds()));
                    SDViewBinder.setTextView(tv_user_score, String.valueOf(userModel.getScore()));
                    enableOpen(left_num > 0 && UserModelDao.canScorePay(need_score));
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }


}
