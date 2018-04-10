package com.fanwe.catchdoll.model;

import com.fanwe.lib.cache.SDDisk;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/13.
 */
public class WWSettingsModel implements Serializable
{
    static final long serialVersionUID = 0L;

    /**
     * 抓娃娃房间是否播放背景音乐
     */
    private boolean playBGM = true;
    /**
     * 抓娃娃房间是否播放点击音效
     */
    private boolean playSoundEffect = true;

    private WWSettingsModel()
    {
    }

    /**
     * 获得本地缓存的实体
     *
     * @return
     */
    public static WWSettingsModel get()
    {
        WWSettingsModel model = SDDisk.openInternal().getSerializable(WWSettingsModel.class);
        if (model == null)
        {
            model = new WWSettingsModel();
            model.save();
        }
        return model;
    }

    /**
     * 将实体保存到本地
     *
     * @return
     */
    public boolean save()
    {
        boolean result = SDDisk.openInternal().putSerializable(this);
        return result;
    }

    public boolean isPlayBGM()
    {
        return playBGM;
    }

    public void setPlayBGM(boolean playBGM)
    {
        this.playBGM = playBGM;
    }

    public boolean isPlaySoundEffect()
    {
        return playSoundEffect;
    }

    public void setPlaySoundEffect(boolean playSoundEffect)
    {
        this.playSoundEffect = playSoundEffect;
    }
}
