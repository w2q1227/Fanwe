package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.business.WaWaLiveBusiness;
import com.fanwe.catchdoll.util.PressedStateListDrawable;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.xianrou.util.ViewUtil;
import com.yalantis.ucrop.util.SelectedStateListDrawable;

/**
 * Created by Administrator on 2017/11/15.
 */

public class WWLiveWatcherPanel extends BaseAppView
{

    private LinearLayout llWwTalk;
    private LinearLayout llWwDetail;
    private FrameLayout flWwPlay;
    private TextView tvCost;
    private Button btnRecharge;
    private TextView tvCoins;
    private TextView tvGameInfo;

    private WatcherCallback callback;

    public interface WatcherCallback
    {
        void onClickWWDetail();

        void onClickPlay();

        void onClickRecharge();

        void onClickMsg();
    }

    public void setCostCoins(String num)
    {
        tvCost.setText(String.format("本次: %s%s", num, AppRuntimeWorker.getDiamondName()));
    }

    public void updateUserCoins(long coins)
    {
        SDViewBinder.setTextView(tvCoins, String.format("余额: %d%s", coins, AppRuntimeWorker.getDiamondName()));
    }

    public void setPlayButton(WaWaLiveBusiness.DOLL_STATUS doll_status, int reserveNum)
    {
        switch (doll_status)
        {
            case READY:
                setButtonDrawable(R.drawable.ww_ic_start_game);
                break;
            case OCCUPIED:
                setButtonDrawable(R.drawable.ww_ic_occupied);
                break;
            case MALFUNCTION:
                setButtonDrawable(R.drawable.ww_ic_wait_game);
                break;
            case RESERVE:
                setGameInfo(reserveNum);
                if (reserveNum == 0)
                {
                    setButtonDrawable(R.drawable.ww_ic_start_game);
                } else
                {
                    setButtonDrawable(R.drawable.ww_ic_reserve);
                }
                break;
            case RESERVING:
                setGameInfo(reserveNum);
                if (reserveNum == 0)
                {
                    setButtonDrawable(R.drawable.ww_ic_start_game);
                } else
                {
                    setButtonDrawable(R.drawable.ww_ic_cancel_reserve);

                }
                break;
        }
    }

    public void setButtonDrawable(@DrawableRes int drawable)
    {
        flWwPlay.setBackgroundDrawable(PressedStateListDrawable.get(drawable));
    }


    public void setGameInfo(int num)
    {
        if (num == 0)
        {
            SDViewUtil.setGone(tvGameInfo);
        } else
        {
            SDViewUtil.setVisible(tvGameInfo);
            tvGameInfo.setText(String.format("当前排队%d人", num));
        }
    }


    public void setCallback(WatcherCallback callback)
    {
        this.callback = callback;
    }

    public WWLiveWatcherPanel(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        setContentView(R.layout.ww_view_live_watcher_panel);

        llWwTalk = (LinearLayout) findViewById(R.id.ll_ww_talk);
        llWwDetail = (LinearLayout) findViewById(R.id.ll_ww_detail);
        flWwPlay = (FrameLayout) findViewById(R.id.fl_ww_play);
        tvCost = (TextView) findViewById(R.id.tv_cost);
        tvCoins = (TextView) findViewById(R.id.tv_coins);
        tvGameInfo = (TextView) findViewById(R.id.tv_game_info);
        ImageView ivMessage = (ImageView) findViewById(R.id.iv_message);
        ImageView ivDoll = (ImageView) findViewById(R.id.iv_doll);


        btnRecharge = (Button) findViewById(R.id.btn_recharge);

        llWwTalk.setOnClickListener(this);
        llWwDetail.setOnClickListener(this);
        flWwPlay.setOnClickListener(this);
        btnRecharge.setOnClickListener(this);

        ivMessage.setBackgroundDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_message));
        ivDoll.setBackgroundDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_doll));
        btnRecharge.setBackgroundDrawable(PressedStateListDrawable.get(R.drawable.ww_ic_live_recharge));

    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (callback == null) return;
        switch (v.getId())
        {
            case R.id.ll_ww_talk:
                callback.onClickMsg();
                break;
            case R.id.ll_ww_detail:
                callback.onClickWWDetail();
                break;
            case R.id.fl_ww_play:
                if (ViewUtil.isFastClick())
                {
                    SDToast.showToast("点得太快了，休息一下吧~");
                    return;
                }
                callback.onClickPlay();
                break;
            case R.id.btn_recharge:
                callback.onClickRecharge();
                break;
        }
    }

}
