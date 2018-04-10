package com.fanwe.live.control;

import com.fanwe.live.model.LivePlayConfig;

public interface IPlayerView
{
    void setPlayConfig(LivePlayConfig playConfig);

    void startPlay();
    void stopPlay();

    void destroy();
}
