package com.fanwe.catchdoll.control;


import com.fanwe.live.control.IPlayerView;

/**
 * Created by Administrator on 2017/11/17.
 */

public interface WWLiveView
{

    enum VideoPlayer
    {
        MAIN, SUB
    }

    interface VideoHolder
    {
        void switchPlayerType(int type);

        IPlayerView getMainVideoView();
        IPlayerView getSubVideoView();

        void switchPlayer();
        void resetPlayer();
        VideoPlayer getVideoPlayer();

        void onDestroy();

    }
}
