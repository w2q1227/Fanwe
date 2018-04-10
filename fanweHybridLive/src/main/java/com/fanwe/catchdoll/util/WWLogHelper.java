package com.fanwe.catchdoll.util;

import android.content.Context;

import com.fanwe.catchdoll.business.WaWaLiveBusiness;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.hybrid.app.App;
import com.fanwe.lib.log.FFileHandler;
import com.fanwe.lib.log.FLogger;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDDateUtil;
import com.tencent.ilivesdk.core.ILiveRoomManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import io.socket.client.IO;

/**
 * User: CJ(jin.chen0829@gmail.com)
 * Date: 2017-12-15
 * Time: 15:45
 * 娃娃相关日志汇总
 */
public class WWLogHelper extends FLogger
{
    private static WWLogHelper sInstance;
    public static final String TAG = "WWLog";

    public static Logger get()
    {
        return get(TAG);
    }

    public static WWLogHelper getInstance()
    {
        if (sInstance == null)
        {
            synchronized (WWLogHelper.class)
            {
                if (sInstance == null)
                {
                    sInstance = new WWLogHelper();
                }
            }
        }
        return sInstance;
    }

    public WWLogHelper()
    {
        try
        {
            get().addHandler(new FFileHandler(TAG+".log",15 * FFileHandler.MB,App.getApplication()));
        }catch (IOException e)
        {
            get().log(Level.SEVERE,"error on create FileHandler",e);
            e.printStackTrace();
        }
    }

    public void log(String log)
    {
        get().log(Level.INFO, log);
    }
}