package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.control.IPlayerSDK;
import com.fanwe.live.control.IPlayerView;
import com.fanwe.live.control.LivePlayerSDK;
import com.fanwe.live.control.TPlayCallbackWrapper;
import com.fanwe.live.model.LivePlayConfig;
import com.fanwe.live.utils.GlideUtil;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Administrator on 2017/11/29.
 */

public class WWLiveVideoView extends RelativeLayout implements IPlayerView
{

    private IPlayerSDK player;

    private RelativeLayout relRoot;
    private ImageView ivBg;

    private LivePlayConfig playConfig;


    public WWLiveVideoView(Context context)
    {
        super(context);
        init(context);
    }

    public WWLiveVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        inflate(context, R.layout.ww_video_view, this);
        relRoot = (RelativeLayout) findViewById(R.id.rel_root);
        ivBg = (ImageView) findViewById(R.id.iv_bg);

    }

    @Override
    public void setPlayConfig(LivePlayConfig playConfig)
    {
        this.playConfig = playConfig;
    }

    @Override
    public void startPlay()
    {
        showCover();
        if (playConfig.isEnableAcc())
        {
            if (playConfig.getAccUrl() == null) return;
            getPlayer().setUrl(playConfig.getAccUrl());
            getPlayer().setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
        } else
        {
            if (playConfig.isIgnoreNonAcc())
            {
                return;
            }

            if (playConfig.getUrl() == null) return;
            getPlayer().setUrl(playConfig.getUrl());
            getPlayer().setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
        }

        getPlayer().startPlay();
        WWLogHelper.getInstance().log("[Video] startPlay -> [playConfig = " + playConfig.toString() + "]");
    }

    @Override
    public void stopPlay()
    {
        getPlayer().clearLastFrame();
        getPlayer().stopPlay();
        showCover();
    }

    public void setBackground(String url)
    {
        GlideUtil.load(url).bitmapTransform(new BlurTransformation(getContext(), 20)).into(ivBg);
    }

    @Override
    public void destroy()
    {
        getPlayer().onDestroy();
    }

    private void showCover()
    {
        SDViewUtil.setVisible(relRoot);
    }

    private void hideCover()
    {
        SDViewUtil.setGone(relRoot);
    }


    private void setPlayer(IPlayerSDK player)
    {
        player.init(findViewById(R.id.view_video));
        player.setRenderModeAdjustResolution();
        player.setRenderRotation(playConfig.getPlayAngle());
        player.setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
        player.enableHardwareDecode(false);
        player.setPlayCallback(mPlayCallbackWrapper);
        player.setMute(true);
        player.setPlayCallback(playCallback);
    }

    public IPlayerSDK getPlayer()
    {
        if (player == null)
        {
            player = new LivePlayerSDK();
            setPlayer(player);
        }
        return player;
    }


    private IPlayerSDK.PlayCallback playCallback = new IPlayerSDK.PlayCallback()
    {
        @Override
        public void onPlayBegin()
        {

        }

        @Override
        public void onPlayRecvFirstFrame()
        {
            hideCover();
        }

        @Override
        public void onPlayProgress(long total, long progress)
        {

        }

        @Override
        public void onPlayLoading()
        {

        }

        @Override
        public void onPlayEnd()
        {

        }
    };


    private TPlayCallbackWrapper mPlayCallbackWrapper = new TPlayCallbackWrapper()
    {

    };

    public void onDestroy()
    {
       getPlayer().onDestroy();
    }


}
