package com.fanwe.catchdoll.activity;

import android.app.Service;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.fanwe.catchdoll.business.WaWaLiveBusiness;
import com.fanwe.catchdoll.control.ILiveRoom;
import com.fanwe.catchdoll.control.LiveFWWaWaSDK;
import com.fanwe.catchdoll.control.ILiveWaWa;
import com.fanwe.catchdoll.control.ILiveWaWaCallback;
import com.fanwe.catchdoll.control.LiveRoomSDK;
import com.fanwe.catchdoll.control.LiveTXWaWaSDK;
import com.fanwe.catchdoll.control.WWBGMDownLoadManager;
import com.fanwe.catchdoll.control.WWLiveView;
import com.fanwe.catchdoll.event.WWEGiveUpQueue;
import com.fanwe.catchdoll.model.WWCustomMsgGame;
import com.fanwe.catchdoll.model.WWCustomMsgQueueUpdate;
import com.fanwe.catchdoll.model.WWQualityData;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.catchdoll.model.WWSettingsModel;
import com.fanwe.lib.dialog.ISDDialogConfirm;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.lib.dialog.impl.SDDialogConfirm;
import com.fanwe.lib.looper.impl.SDSimpleLooper;
import com.fanwe.lib.player.SDMediaPlayer;
import com.fanwe.catchdoll.model.WWSettingsModel;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.catchdoll.util.WWConstant;
import com.fanwe.lib.player.SDMediaPlayer;
import com.fanwe.library.holder.SDProxyHolder;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.LiveActivity;
import com.fanwe.live.control.IPlayerView;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.ELiveSongDownload;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.LivePlayConfig;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.sunday.eventbus.SDEventManager;
import com.tencent.TIMCallBack;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.tools.quality.ILiveQualityData;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * Created by CJ on 2017/11/13.
 */

public abstract class WWLiveActivity extends LiveActivity implements
        ILiveWaWaCallback,
        ILiveRoom.Callback,
        WaWaLiveBusiness.WaWaLiveBusinessCallback
{
    private ILiveWaWa waWaSDK;

    private ILiveRoom iLiveRoom;

    private IPlayerView mProxyPlayerView;

    private WaWaLiveBusiness mWaWaLiveBusiness;

    private AudioManager mAudioManager;
    private SDMediaPlayer mSoundEffectPlayer;

    private SDSimpleLooper qualityLooper;

    protected abstract WWLiveView.VideoHolder getVideoHolder();

    //展示观众视图
    protected abstract void onWatchView();

    //展示玩家视图
    protected abstract void onGameView();

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        mProxyPlayerView = SDProxyHolder.newProxyInstance(IPlayerView.class, new InvocationHandler()
        {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
            {
                method.invoke(getVideoHolder().getMainVideoView(), args);
                method.invoke(getVideoHolder().getSubVideoView(), args);
                return null;
            }
        });
    }

    @Override
    protected void initIM()
    {
        super.initIM();
        getViewerIM().joinGroup(getGroupId(), new TIMCallBack()
        {
            @Override
            public void onError(int i, String s)
            {
                getViewerIM().onErrorJoinGroup(getGroupId(), i, s);
                SDToast.showToast("加入房间群组失败 (" + s + ")");
                finish();
            }

            @Override
            public void onSuccess()
            {
                getViewerIM().onSuccessJoinGroup(getGroupId());
                sendViewerJoinMsg();
            }
        });
    }

    private void initSounds(String bgmPath)
    {
        mAudioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        //sound
        if (WWSettingsModel.get().isPlaySoundEffect())
        {
            mSoundEffectPlayer = new SDMediaPlayer();
            mSoundEffectPlayer.init();
            mSoundEffectPlayer.setLooping(false);
            mSoundEffectPlayer.setDataRawResId(R.raw.ww_click_sound_effect, getActivity());
            mSoundEffectPlayer.setVolume(1f, 1f);
        }

        //bgm
        if (WWSettingsModel.get().isPlayBGM())
        {
            SDMediaPlayer.getInstance().init();

            if (bgmPath == null || bgmPath.isEmpty())
            {
                SDMediaPlayer.getInstance().setDataRawResId(R.raw.ww_room_bgm, this);
                startBgm();
            } else
            {
                WWBGMDownLoadManager.getInstance().startLoad(bgmPath);
            }
        }
    }

    //启动直播间
    protected void begin()
    {
        WWLogHelper.getInstance().log("[BaseInfo] -> [roomId = " + getRoomId() + "][userId = " + UserModelDao.getUserId() + "]");
        showProgressDialog("正在加载...");
        getVideoHolder().getMainVideoView().setPlayConfig(LiveInformation.getInstance().getMainPlayerConfig());
        getVideoHolder().getSubVideoView().setPlayConfig(LiveInformation.getInstance().getSubPlayerConfig());
        getWaWaLiveBusiness().requestRoomInfo(getRoomId());
    }

    //观众模式
    protected void watchMode()
    {
        onWatchView();

        LiveInformation.getInstance().getMainPlayerConfig().setEnableAcc(false);
        LiveInformation.getInstance().getSubPlayerConfig().setEnableAcc(false);

        getVideoHolder().switchPlayerType(WWConstant.VIDEO_PLAYER.STEAM_PLAYER);

        getVideoHolder().resetPlayer();
        mProxyPlayerView.stopPlay();
        mProxyPlayerView.startPlay();
    }

    //玩家模式
    protected void gameMode()
    {
        onGameView();

        LiveInformation.getInstance().getMainPlayerConfig().setEnableAcc(true);
        LiveInformation.getInstance().getSubPlayerConfig().setEnableAcc(true);
        mProxyPlayerView.stopPlay();
        if (LiveInformation.getInstance().isLiveRoom())
        {
            getVideoHolder().switchPlayerType(WWConstant.VIDEO_PLAYER.ROOM_PLAYER);

        } else
        {
            getVideoHolder().switchPlayerType(WWConstant.VIDEO_PLAYER.STEAM_PLAYER);
            mProxyPlayerView.startPlay();
        }

        getVideoHolder().resetPlayer();
    }

    //开始游戏
    public void playGame()
    {
        getWaWaLiveBusiness().dealPlay(getRoomId());

    }

    //无视Doll_status，快速开始,
    public void quickStart()
    {
        getWaWaLiveBusiness().quickStart(getRoomId());
    }

    //点击推送消息处理，弹窗就好了
    public void pushStart()
    {
        getWaWaLiveBusiness().pushStart();
    }

    //销毁sdk
    public void quitSDK()
    {
        getWaWaSDK().quitGame();
    }

    public void quitRoom()
    {
        getLiveRoom().quitRoom();
    }

    //结束游戏，释放控制权
    public void finishGame()
    {
        getWaWaLiveBusiness().requestFinishGame(getRoomId());
    }

    //------------------------- WaWaLiveBusinessCallback start -----------------------------


    @Override
    public void onBsJoinRoom()
    {
        getLiveRoom().joinRoom(getRoomId());
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);
        dismissProgressDialog();
        initSounds(actModel.getDoll().getMusic());

        LivePlayConfig mainPlayerConfig = LiveInformation.getInstance().getMainPlayerConfig();
        LivePlayConfig subPlayerConfig = LiveInformation.getInstance().getSubPlayerConfig();

        mainPlayerConfig.setUrl(actModel.getDollRtmpUrl(0));
        mainPlayerConfig.setPlayAngle(actModel.getPlay_angle());
        mainPlayerConfig.setPushName(actModel.getDoll().getFront_push_user());

        subPlayerConfig.setUrl(actModel.getDollRtmpUrl(1));
        subPlayerConfig.setPlayAngle(actModel.getPlay_angle2());
        subPlayerConfig.setPushName(actModel.getDoll().getSide_push_user());


        //游戏中状态，直接切换，优先级最高
        boolean isProcess = getWaWaLiveBusiness().processStartGame(actModel.getGame_info());
        if (isProcess) return;

        //快速开始游戏模式
        boolean isQuickStart = getIntent().getBooleanExtra(WWLiveLayoutActivity.EXTRA_QUICK_START, false);
        if (isQuickStart)
        {
            quickStart();
        }

        //处理预约推送
        boolean isPushStart = getIntent().getBooleanExtra(WWLiveLayoutActivity.EXTRA_PUSH_START, false);
        if (isPushStart)
        {
            pushStart();
        }

        watchMode();

    }

    @Override
    public void onBsRequestRoomInfoFailed(String errMsg)
    {
        dismissProgressDialog();
        SDToast.showToast(errMsg);
        finish();
    }

    /*-------------------------------------------------------------------------------------------*/

    @Override
    public void onBsStartGameBegin()
    {
        showProgressDialog("游戏准备中...");
    }

    @Override
    public void onBsStartGameSuccess(int gameId, String playUrl)
    {
        getWaWaSDK().joinQueue(playUrl, gameId);
    }

    @Override
    public void onBsStartGameFailed(String errMsg)
    {
        dismissProgressDialog();
        SDToast.showToast(errMsg);
    }
    /*-------------------------------------------------------------------------------------------*/

    @Override
    public void onBsRequestEndGameSuccess(int countDown, boolean isGrab)
    {
        quitSDK();
        quitRoom();
    }

    @Override
    public void onBsRequestEndGameFailed(String errMsg, final int gameId, final boolean isGrab)
    {
        quitSDK();
        quitRoom();
        SDDialogConfirm resultFaildDialog = new SDDialogConfirm(this);
        resultFaildDialog.setTextTitle("提示" + errMsg);
        resultFaildDialog.setTextContent("获取游戏结果失败，请重试");
        resultFaildDialog.setTextConfirm("重试");
        resultFaildDialog.setCallback(new ISDDialogConfirm.Callback()
        {
            @Override
            public void onClickCancel(View view, SDDialogBase sdDialogBase)
            {
                watchMode();
            }

            @Override
            public void onClickConfirm(View view, SDDialogBase sdDialogBase)
            {
                getWaWaLiveBusiness().requestEndGame(gameId, getRoomId(), isGrab);
            }
        });
        resultFaildDialog.show();
    }

    @Override
    public void onBsRequestEndGameError(String msg)
    {
        SDToast.showToast(msg);
    }

    /*-------------------------------------------------------------------------------------------*/

    @Override
    public void onBsUpdateFastSteam(String mainUrl, String subUrl)
    {
        LiveInformation.getInstance().getMainPlayerConfig().setAccUrl(mainUrl);
        LiveInformation.getInstance().getSubPlayerConfig().setAccUrl(subUrl);
        gameMode();
    }

    @Override
    public void onBsUpdateSocketAddress(String url)
    {
        getWaWaSDK().setSocketAddress(url);
    }

    /*------------------------- end -------------------------------------------------------*/

    //------------------------- live message start-------------------------------------------
    @Override
    public void onMsgWWGameStart(WWCustomMsgGame wwCustomMsgGame)
    {
        getWaWaLiveBusiness().dealMsgGameStart();
    }

    @Override
    public void onMsgWWGameEnd(WWCustomMsgGame wwCustomMsgGame)
    {
        getWaWaLiveBusiness().dealMsgGameEnd();
    }

    @Override
    public void onMsgWWQueueUpdate(WWCustomMsgQueueUpdate wwCustomMsgGame)
    {
        if (wwCustomMsgGame.getRoom_id() == getRoomId())
            getWaWaLiveBusiness().requestMyReserve(getRoomId());
    }

    /*------------------------- end -------------------------------------------------------*/

    //------------------------- WaWaSDK callback start--------------------------------------
    @Override
    public void onQueue(final int queueSize)
    {
    }

    @Override
    public void onReady()
    {
        dismissProgressDialog();
        getWaWaSDK().startGame();

    }

    @Override
    public void onResult(final int gameId, final boolean isSuccess)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                quitSDK();
                quitRoom();
                getWaWaLiveBusiness().requestEndGame(gameId, getRoomId(), isSuccess);
            }
        });
    }

    @Override
    public void tryEndGame(final int gameId)
    {
        quitSDK();
        quitRoom();
        getWaWaLiveBusiness().requestEndGame(gameId, getRoomId(), false);
//        WWLogHelper.getInstance().upLoad();
    }

    @Override
    public void onTime(int time)
    {

    }

    @Override
    public WWLiveView.VideoPlayer getCurrentPlayer()
    {
        return getVideoHolder().getVideoPlayer();
    }

    /*------------------------- end -------------------------------------------------------*/

    @Override
    public void onJoinRoomSuccess()
    {

    }

    @Override
    public void onJoinRoomFailure()
    {

    }

    @Override
    public void onTraceQuality(boolean isTrace)
    {
        if (isTrace)
        {
            getQualityLooper().start(2000, new Runnable()
            {
                @Override
                public void run()
                {
                    ILiveQualityData qualityData = ILiveRoomManager.getInstance().getQualityData();
                    if (qualityData != null && qualityData.getStartTime() != 0)
                    {
                        WWQualityData wwQualityData = new WWQualityData(qualityData);
                        LiveInformation.getInstance().addQualityData(wwQualityData);
                        WWLogHelper.get().log(Level.INFO, "[Trace]Quality -> " + wwQualityData.toString());

                        if (wwQualityData.getRecvLossRate() > 400)
                        {
                            SDToast.showToast("网络波动较大");
                        }
                    }
                }
            });
        } else
        {
            destroyQualityLooper();
        }
    }

    public void onEventMainThread(WWEGiveUpQueue wweGiveUpQueue)
    {
        getWaWaLiveBusiness().dealGiveUpQueue();
    }

    protected void sendViewerJoinMsg()
    {
        if (!getViewerIM().isCanSendViewerJoinMsg())
        {
            return;
        }
        App_get_videoActModel actModel = getRoomInfo();
        if (actModel == null)
        {
            return;
        }
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            return;
        }

        boolean sendViewerJoinMsg = true;
        if (!user.isProUser() && actModel.getJoin_room_prompt() == 0)
        {
            sendViewerJoinMsg = false;
        }

        if (sendViewerJoinMsg)
        {
            CustomMsgViewerJoin joinMsg = new CustomMsgViewerJoin();
            joinMsg.setSortNumber(actModel.getSort_num());

            getViewerIM().sendViewerJoinMsg(joinMsg, null);
        }
    }


    protected WaWaLiveBusiness getWaWaLiveBusiness()
    {
        if (mWaWaLiveBusiness == null)
        {
            mWaWaLiveBusiness = new WaWaLiveBusiness();
            mWaWaLiveBusiness.setCallback(this);
        }
        return mWaWaLiveBusiness;
    }

    protected ILiveWaWa getWaWaSDK()
    {
        if (waWaSDK == null)
        {
            waWaSDK = LiveInformation.getInstance().isOwnerSDK() ? new LiveFWWaWaSDK() : new LiveTXWaWaSDK();
            waWaSDK.registerWaWaCallback(this);
        }

        return waWaSDK;
    }

    protected ILiveRoom getLiveRoom()
    {
        if (iLiveRoom == null)
        {
            iLiveRoom = new LiveRoomSDK();
            iLiveRoom.setCallback(this);
        }
        return iLiveRoom;
    }

    private SDSimpleLooper getQualityLooper()
    {
        if (qualityLooper == null)
        {
            qualityLooper = new SDSimpleLooper();
        }
        return qualityLooper;
    }

    private void destroyQualityLooper()
    {
        if (qualityLooper != null)
        {
            qualityLooper.stop();
        }
    }

    protected SDMediaPlayer getSoundEffectPlayer()
    {
        return mSoundEffectPlayer;
    }

    private void startBgm()
    {
        SDMediaPlayer.getInstance().setLooping(true);
        SDMediaPlayer.getInstance().setVolume(0.3f, 0.3f);
        SDMediaPlayer.getInstance().start();
    }

    public void onEvent(ELiveSongDownload eLiveSongDownload)
    {
        if (eLiveSongDownload.songModel.getAudio_id() != null && eLiveSongDownload.songModel.getAudio_id().startsWith(WWBGMDownLoadManager.AUDIO_ID))
        {
            if (eLiveSongDownload.songModel.getProgress() == 100)
            {
                SDMediaPlayer.getInstance().setDataPath(eLiveSongDownload.songModel.getMusicPath());
                startBgm();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                mAudioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mAudioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        getLiveRoom().onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getLiveRoom().onResume();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        SDMediaPlayer.getInstance().pause();
        if (mSoundEffectPlayer != null)
        {
            mSoundEffectPlayer.pause();
        }
        getVideoHolder().getMainVideoView().stopPlay();
        getVideoHolder().getSubVideoView().stopPlay();
        getViewerIM().destroyIM();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        SDMediaPlayer.getInstance().start();
        if (mSoundEffectPlayer != null)
        {
            mSoundEffectPlayer.start();
        }
        getVideoHolder().getMainVideoView().startPlay();
        getVideoHolder().getSubVideoView().startPlay();
        initIM();
    }

    @Override
    protected void onDestroy()
    {
        if (mSoundEffectPlayer != null)
        {
            mSoundEffectPlayer.release();
        }
        SDMediaPlayer.getInstance().release();
        destroyQualityLooper();
        getWaWaSDK().onDestroy();
        getVideoHolder().onDestroy();
        getViewerIM().destroyIM();
        getLiveRoom().onDestroy();
        super.onDestroy();
    }


}
