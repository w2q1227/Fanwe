package com.fanwe.catchdoll.control;

import com.fanwe.live.LiveSongDownloadManager;
import com.fanwe.live.event.ELiveSongDownload;
import com.fanwe.live.model.LiveSongModel;
import com.sunday.eventbus.SDEventManager;

import java.io.File;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/12/28.
 */

public class WWBGMDownLoadManager extends LiveSongDownloadManager
{

    public static final String AUDIO_ID = "live_bgm_";

    private static WWBGMDownLoadManager sManager;

    public static WWBGMDownLoadManager getInstance()
    {
        if (sManager == null)
        {
            sManager = new WWBGMDownLoadManager();
        }
        return sManager;
    }

    public void startLoad(String bgmPath)
    {
        LiveSongModel model = new LiveSongModel();
        model.setAudio_id(AUDIO_ID+getFileName(bgmPath));
        model.setAudio_link(bgmPath);
        String path = model.getMusicPath();
        if (new File(path).exists())
        {
            model.setProgress(100);
            ELiveSongDownload eLiveSongDownload = new ELiveSongDownload();
            eLiveSongDownload.songModel = model;
            SDEventManager.post(eLiveSongDownload);
        }else
        {
            addTask(model);
        }
    }

    private String getFileName(String bgmPath)
    {
        return bgmPath.substring(bgmPath.lastIndexOf("/"),bgmPath.lastIndexOf("."));
    }
}
