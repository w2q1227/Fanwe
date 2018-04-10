package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.widget.FrameLayout;

import com.fanwe.catchdoll.control.WWLiveView;
import com.fanwe.catchdoll.util.WWConstant;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.event.ELiveMeasureVideo;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.ilivesdk.view.AVVideoView;
import com.tencent.ilivesdk.view.VideoListener;

/**
 * Created by Administrator on 2018/1/3.
 */

public abstract class WWBaseVideoHolderView extends WWBaseCountDownView implements WWLiveView.VideoHolder
{

    protected abstract FrameLayout getVideoContent();

    private WWLiveVideoView mainVideoView, subVideoView;
    private AVRootView mAVRootView;

    private WWLiveView.VideoPlayer videoPlayer;

    private int currentPlayType = WWConstant.VIDEO_PLAYER.NONE;

    public WWBaseVideoHolderView(Context context)
    {
        super(context);

        mainVideoView = new WWLiveVideoView(context);
        subVideoView = new WWLiveVideoView(context);
    }

    @Override
    public void switchPlayer()
    {
        if (videoPlayer == WWLiveView.VideoPlayer.MAIN)
        {
            if (currentPlayType == WWConstant.VIDEO_PLAYER.STEAM_PLAYER)
            {
                getMainVideoView().setVisibility(GONE);
                getSubVideoView().setVisibility(VISIBLE);
            } else
            {
                mAVRootView.swapVideoView(0, 1);
                mAVRootView.getViewByIndex(0).setRotation(LiveInformation.getInstance().getSubPlayerConfig().getPlayAngle());
            }
            videoPlayer = WWLiveView.VideoPlayer.SUB;
        } else
        {
            if (currentPlayType == WWConstant.VIDEO_PLAYER.STEAM_PLAYER)
            {
                getMainVideoView().setVisibility(VISIBLE);
                getSubVideoView().setVisibility(GONE);
            } else
            {
                mAVRootView.swapVideoView(0, 1);
                mAVRootView.getViewByIndex(0).setRotation(LiveInformation.getInstance().getMainPlayerConfig().getPlayAngle());
            }
            videoPlayer = WWLiveView.VideoPlayer.MAIN;
        }
    }

    public void onEvent(ELiveMeasureVideo eLiveMeasureVideo)
    {
        if (eLiveMeasureVideo.height < eLiveMeasureVideo.width)
        {
            eLiveMeasureVideo.width = eLiveMeasureVideo.height + eLiveMeasureVideo.width;
            eLiveMeasureVideo.height = eLiveMeasureVideo.width - eLiveMeasureVideo.height;
            eLiveMeasureVideo.width = eLiveMeasureVideo.width - eLiveMeasureVideo.height;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getVideoContent().getLayoutParams();
        layoutParams.height = (int) (eLiveMeasureVideo.height * 1.0 / eLiveMeasureVideo.width * getVideoContent().getWidth());
        WWLogHelper.getInstance().log("[Video]Resize -> measure [width = " + eLiveMeasureVideo.width + "|height =" + eLiveMeasureVideo.height + "]");
        WWLogHelper.getInstance().log("[Video]Resize -> solution [width = " + getVideoContent().getWidth() + "|height =" + layoutParams.height + "]");
        getVideoContent().requestLayout();
    }

    @Override
    public WWLiveVideoView getMainVideoView()
    {
        return mainVideoView;
    }

    @Override
    public WWLiveVideoView getSubVideoView()
    {
        return subVideoView;
    }

    public void resetPlayer()
    {
        if (currentPlayType == WWConstant.VIDEO_PLAYER.STEAM_PLAYER)
        {
            videoPlayer = WWLiveView.VideoPlayer.MAIN;
            getMainVideoView().setVisibility(VISIBLE);
            getSubVideoView().setVisibility(GONE);
        } else if (currentPlayType == WWConstant.VIDEO_PLAYER.ROOM_PLAYER)
        {

        }
    }


    @Override
    public WWLiveView.VideoPlayer getVideoPlayer()
    {
        return videoPlayer;
    }

    @Override
    public void switchPlayerType(int playerType)
    {
        FrameLayout videoContent = getVideoContent();
        if (videoContent == null || videoContent.getContext() == null) return;
        if (playerType == currentPlayType) return;

        currentPlayType = playerType;
        switch (currentPlayType)
        {
            case WWConstant.VIDEO_PLAYER.STEAM_PLAYER:
                SDViewUtil.replaceView(videoContent, mainVideoView);
                SDViewUtil.addView(videoContent, subVideoView);
                break;
            case WWConstant.VIDEO_PLAYER.ROOM_PLAYER:

                if (mAVRootView == null)
                {
                    mAVRootView = new AVRootView(videoContent.getContext());
                    ILiveRoomManager.getInstance().initAvRootView(mAVRootView);
                    ILiveRoomManager.getInstance().enableMic(false);
                    ILiveRoomManager.getInstance().enableSpeaker(false);
                    mAVRootView.setAutoOrientation(false);
                    mAVRootView.setSubCreatedListener(new AVRootView.onSubViewCreatedListener()
                    {
                        @Override
                        public void onSubViewCreated()
                        {
                            if (mAVRootView.getViewByIndex(0) != null)
                            {
                                mAVRootView.getViewByIndex(0).setRotate(true);
                                mAVRootView.getViewByIndex(0).setVideoListener(new VideoListener()
                                {
                                    @Override
                                    public void onFirstFrameRecved(int width, int height, int angle, String identifier)
                                    {

                                    }

                                    @Override
                                    public void onHasVideo(String identifier, int srcType)
                                    {
                                        String mainPushName = LiveInformation.getInstance().getMainPlayerConfig().getPushName();
                                        if (identifier != null && !identifier.equals(mainPushName))
                                        {
                                            mAVRootView.swapVideoView(0, 1);
                                            videoPlayer = WWLiveView.VideoPlayer.MAIN;
                                        }
                                        mAVRootView.getViewByIndex(0).setRotation(LiveInformation.getInstance().getMainPlayerConfig().getPlayAngle());
                                    }

                                    @Override
                                    public void onNoVideo(String identifier, int srcType)
                                    {

                                    }
                                });
                            }

                            for (int i = 1; i < ILiveConstants.MAX_AV_VIDEO_NUM; i++)
                            {
                                final AVVideoView subview = mAVRootView.getViewByIndex(i);
                                subview.setPosWidth(0);
                                subview.setPosHeight(0);
                            }
                        }
                    });
                }
                SDViewUtil.replaceView(videoContent, mAVRootView);
                break;
        }
    }

    @Override
    public void onDestroy()
    {
        WWLogHelper.getInstance().log("[Video]Process -> on destroy");
        getMainVideoView().onDestroy();
        getSubVideoView().onDestroy();
        if (mAVRootView != null)
            mAVRootView.onDestory();
    }
}
