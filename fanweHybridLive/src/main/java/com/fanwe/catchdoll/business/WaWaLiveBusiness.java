package com.fanwe.catchdoll.business;

import com.fanwe.catchdoll.activity.WWLiveLayoutActivity;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.dialog.WWGlobalStartGameDialog;
import com.fanwe.catchdoll.model.WWAppDollModel;
import com.fanwe.catchdoll.model.WWAppGameEndModel;
import com.fanwe.catchdoll.model.WWAppGameStartModel;
import com.fanwe.catchdoll.model.WWAppJoinQueueModel;
import com.fanwe.catchdoll.model.WWAppMyReserveModel;
import com.fanwe.catchdoll.util.WWConstant;
import com.fanwe.catchdoll.model.WWDollQuestionActModel;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.business.BaseBusiness;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_get_videoActModel;
import com.tencent.ilivesdk.core.ILiveRoomManager;


/**
 * Created by Administrator on 2017/11/15.
 */

public class WaWaLiveBusiness extends BaseBusiness
{
    //娃娃机状态:
    public enum DOLL_STATUS
    {
        //开始游戏
        READY,
        //占用中
        OCCUPIED,
        //机器故障
        MALFUNCTION,
        //可预约
        RESERVE,
        //预约中
        RESERVING
    }

    private final int MAX_END_RETRY = 3;

    private WaWaLiveBusinessCallback mCallback;

    private int reserveNum;
    private int endGameRetry = 0; //获取游戏结果重试次数
    private DOLL_STATUS doll_status;

    public void setCallback(WaWaLiveBusinessCallback callback)
    {
        this.mCallback = callback;
    }

    @Override
    protected WaWaLiveBusinessCallback getBaseBusinessCallback()
    {
        return mCallback;
    }

    public interface WaWaLiveBusinessCallback extends BaseBusinessCallback
    {
        //roomInfo callback
        void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel);

        void onBsRequestRoomInfoFailed(String errMsg);

        //startGame callback
        void onBsStartGameBegin();

        void onBsStartGameSuccess(int gameId, String playUrl);

        void onBsStartGameFailed(String errMsg);

        //endGame callback
        void onBsRequestEndGameSuccess(int countDown, boolean isGrab);

        void onBsRequestEndGameFailed(String msg, int gameId, boolean isGrab);

        void onBsRequestEndGameError(String msg);

        //更新余额
        void onBsUpdateCoin(long diamonds);

        //更新游戏时长
        void onBsUpdateRoundTime(int roundTime);

        //更新快速拉流地址
        void onBsUpdateFastSteam(String mainUrl, String subUrl);

        //更新socket地址
        void onBsUpdateSocketAddress(String url);

        //更新按钮状态
        void onBsUpdateDollStatus(DOLL_STATUS doll_status, int reserveNum);

        void onBsRequestDollQuestionSuccess(WWDollQuestionActModel model);

        void onBsRequestDollQuestionFailed(String errMsg);

        void onBsRequestSaveQuestionSuccess();

        void onBsRequestSaveQuestionFailed(String errMsg);

        void onBsJoinRoom();
    }

    public void dealPlay(int roomId)
    {
        log("Process -> dealPlay");
        switch (doll_status)
        {
            case MALFUNCTION:
                break;
            case READY:
                buttonStart(roomId);
                break;
            case OCCUPIED:
                break;
            case RESERVE:
                buttonStart(roomId);
                break;
            case RESERVING:
                requestQuitQueue(roomId);
                break;
        }
    }

    public void buttonStart(int roomId)
    {
        processPlayGame(roomId);
    }

    public void quickStart(int roomId)
    {
        log("Process -> quickStart");
        processPlayGame(roomId);
    }

    public void pushStart()
    {
        log("Process -> pushStart");
        WWLiveLayoutActivity.resolveReserveInfo();
    }

    private void processPlayGame(int roomId)
    {
        requestStartGame(roomId);
    }

    public void requestRoomInfo(final int roomId)
    {

        WWCommonInterface.requestRoomInfo(roomId, new AppRequestCallback<App_get_videoActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                log("BaseInfo -> [roomId = " + roomId + "][userId = " + UserModelDao.getUserId() + "]");
                log(getAppRequestParams().getActInfo(), getAppRequestParams().parseToUrl());
            }

            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                LiveInformation.getInstance().setRoomInfo(getActModel());
                if (getActModel().isOk())
                {
                    initDollStatus(getActModel());
                    mCallback.onBsRequestRoomInfoSuccess(getActModel());

                } else
                {
                    mCallback.onBsRequestRoomInfoFailed(getActModel().getError());
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                mCallback.onBsRequestRoomInfoFailed(null);
                logError(resp.getThrowable().getMessage(), resp.getDecryptedResult());
            }
        });
    }


    public void requestJoinQueue(int roomId)
    {
        WWCommonInterface.requestJoinQueue(roomId, new AppRequestCallback<WWAppJoinQueueModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                log(getAppRequestParams().getActInfo(), getAppRequestParams().parseToUrl());
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (getActModel().getStatus() == 1)
                {
                    dealJoinQueue();
                }
            }
        });
    }


    public void requestQuitQueue(int roomId)
    {
        WWCommonInterface.requestQuitQueue(roomId, new AppRequestCallback<WWAppJoinQueueModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                log(getAppRequestParams().getActInfo(), getAppRequestParams().parseToUrl());
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (getActModel().getStatus() == 1)
                {
                    dealQuitQueue();
                }
            }
        });
    }

    public void requestDollQuestion()
    {
        WWCommonInterface.requestDollQuestion(new AppRequestCallback<WWDollQuestionActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (getActModel().isOk())
                {
                    mCallback.onBsRequestDollQuestionSuccess(getActModel());
                } else
                {
                    mCallback.onBsRequestDollQuestionFailed(getActModel().getError());
                }
            }
        });
    }


    public void requestSaveQuestion(int roomId, int gameId, int doll_id, int question_id, String log)
    {
        WWCommonInterface.requestSaveQuestion(roomId, gameId, question_id, doll_id, log, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (getActModel().isOk())
                {
                    mCallback.onBsRequestSaveQuestionSuccess();
                } else
                {
                    mCallback.onBsRequestSaveQuestionFailed(getActModel().getError());
                }
            }
        });
    }

    //请求游戏开始接口,若为需要预约，则自动预约
    public void requestStartGame(int roomId)
    {
        if (isBlockStatus) return;
        WWCommonInterface.requestGameStart(roomId, new AppRequestCallback<WWAppGameStartModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onStart()
            {
                super.onStart();
                log(getAppRequestParams().getActInfo(), getAppRequestParams().parseToUrl());
                getBaseBusinessCallback().onBsStartGameBegin();
                blockStatus();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                //开始游戏成功
                if (getActModel().getStatus() == 1)
                {
                    logSuccess();
                    getBaseBusinessCallback().onBsUpdateCoin(getActModel().getDiamonds());
                    processStartGame(getActModel());
                    //预约成功
                } else if (getActModel().getStatus() == 5)
                {
                    dealJoinQueue();
                    getBaseBusinessCallback().onBsStartGameFailed(null);
                } else
                {
                    getBaseBusinessCallback().onBsStartGameFailed(getActModel().getError());
                    logFailed(getActModel().getError());
                }

            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                getBaseBusinessCallback().onBsStartGameFailed("游戏启动失败");
                logError(resp.getThrowable().getMessage(), resp.getDecryptedResult());
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                releaseStatus();
            }
        });
    }

    public boolean processStartGame(WWAppGameStartModel startModel)
    {
        endGameRetry = 0;
        if (getBaseBusinessCallback() != null && startModel != null && startModel.getGame_id() != 0)
        {
            LiveInformation.getInstance().setVideoType(startModel.getVideo_type());
            if (LiveInformation.getInstance().isLiveRoom())
            {
                getBaseBusinessCallback().onBsJoinRoom();
            }

            LiveInformation.getInstance().getMainPlayerConfig().setCameraPosition(startModel.getPlay_position());
            LiveInformation.getInstance().getSubPlayerConfig().setCameraPosition(startModel.getPlay_position2());
            getBaseBusinessCallback().onBsUpdateFastSteam(startModel.getPlay_rtmp(), startModel.getPlay_rtmp2());
            getBaseBusinessCallback().onBsUpdateSocketAddress(startModel.getSocket_address());
            getBaseBusinessCallback().onBsUpdateRoundTime(startModel.getRound_time());
            getBaseBusinessCallback().onBsStartGameSuccess(startModel.getGame_id(), startModel.getPermission_name());
            return true;
        }
        return false;
    }


    public void requestEndGame(final int gameId, final int roomId, final boolean is_grab)
    {
        WWCommonInterface.requestGameEnd(gameId, roomId, is_grab, endGameRetry >= MAX_END_RETRY, LiveInformation.getInstance().fetchQualityData(), new AppRequestCallback<WWAppGameEndModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                log(getAppRequestParams().getActInfo(), getAppRequestParams().parseToUrl());
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (getActModel().getStatus() == 1)
                {
                    logSuccess();
                    LiveInformation.getInstance().setDollId(getActModel().getId());
                    getBaseBusinessCallback().onBsRequestEndGameSuccess(getActModel().getCount_down(), getActModel().getIs_grab() == 1 ? true : false);
                } else
                {
                    logFailed(getActModel().getError());
                    if (endGameRetry < MAX_END_RETRY)
                    {
                        endGameRetry++;
                        log("EndGameRetry -> [endGameRetry = " + endGameRetry + "]");
                        requestEndGame(gameId, roomId, is_grab);
                    } else
                    {
                        getBaseBusinessCallback().onBsRequestEndGameFailed(getActModel().getError(), gameId, is_grab);
                    }
                }
                if (LiveInformation.getInstance().fetchQualityData() != null)
                {
                    LiveInformation.getInstance().fetchQualityData().clear();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                getBaseBusinessCallback().onBsRequestEndGameError("获取游戏结果失败");
                logError(resp.getThrowable().getMessage(), resp.getDecryptedResult());
            }
        });
    }

    public void requestFinishGame(int roomId)
    {
        WWCommonInterface.requestGameFinish(roomId, new AppRequestCallback<WWAppGameEndModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                log(getAppRequestParams().getActInfo(), getAppRequestParams().parseToUrl());
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (getActModel().getStatus() == 1)
                {
                    dealFinishGame();
                    logSuccess();
                } else
                {
                    logFailed(getActModel().getError());
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                logError(resp.getThrowable().getMessage(), resp.getDecryptedResult());
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    private SDRequestHandler myReserveHandler;

    public void requestMyReserve(int roomId)
    {
        if (myReserveHandler != null)
            myReserveHandler.cancel();
        myReserveHandler = WWCommonInterface.requestMyReserve(roomId, new AppRequestCallback<WWAppMyReserveModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (getActModel().getHas_reserve() == 1)
                {
                    dealJoinQueue();
                } else
                {
                    dealQuitQueue();
                }
                updateReserveNum(getActModel().getMy_reserve());
            }
        });
    }

    private void initDollStatus(App_get_videoActModel liveModel)
    {
        if (liveModel.getDoll() != null)
        {
            WWAppDollModel dollModel = liveModel.getDoll();

            boolean isReserveRoom = dollModel.isServe();
            boolean isReserving = liveModel.getHas_reserve() == 1 ? true : false;

            int status = SDTypeParseUtil.getInt(dollModel.getStatus());
            if (status == WWConstant.DollStatus.MALFUNCTION)
            {
                doll_status = DOLL_STATUS.MALFUNCTION;
                return;
            }

            if (isReserveRoom)
            {
                doll_status = isReserving ? DOLL_STATUS.RESERVING : DOLL_STATUS.RESERVE;
            } else
            {
                doll_status = status == WWConstant.DollStatus.ENABLE_PLAY ? DOLL_STATUS.READY : DOLL_STATUS.OCCUPIED;
            }
        }
        this.reserveNum = liveModel.getReserve_count();
        notifyDollStatus();
    }

    public void dealMsgGameStart()
    {
        handleStatusChange(WWConstant.DollEvent.GAME_START);
    }

    public void dealMsgGameEnd()
    {
        handleStatusChange(WWConstant.DollEvent.GAME_END);
    }

    public void dealFinishGame()
    {
        handleStatusChange(WWConstant.DollEvent.GAME_FINISH);
    }

    public void dealJoinQueue()
    {
        handleStatusChange(WWConstant.DollEvent.QUEUE_JOIN);
    }

    public void dealQuitQueue()
    {
        handleStatusChange(WWConstant.DollEvent.QUEUE_QUIT);
    }

    public void dealGiveUpQueue()
    {
        handleStatusChange(WWConstant.DollEvent.QUEUE_GIVE_UP);
    }

    private void updateReserveNum(int num)
    {
        this.reserveNum = num;
        notifyDollStatus();
    }


    private void handleStatusChange(int event)
    {
        switch (event)
        {
            case WWConstant.DollEvent.GAME_START:
                if (doll_status == DOLL_STATUS.READY)
                {
                    doll_status = DOLL_STATUS.OCCUPIED;
                }
                break;
            case WWConstant.DollEvent.GAME_END:
                if (doll_status == DOLL_STATUS.OCCUPIED)
                {
                    doll_status = DOLL_STATUS.READY;
                }
                break;
            case WWConstant.DollEvent.GAME_FINISH:
                if (doll_status == DOLL_STATUS.RESERVING)
                {
                    doll_status = DOLL_STATUS.RESERVE;
                }
                break;
            case WWConstant.DollEvent.QUEUE_JOIN:
                if (doll_status == DOLL_STATUS.RESERVE)
                {
                    doll_status = DOLL_STATUS.RESERVING;
                }
                break;
            case WWConstant.DollEvent.QUEUE_QUIT:
            case WWConstant.DollEvent.QUEUE_GIVE_UP:
                if (doll_status == DOLL_STATUS.RESERVING)
                {
                    doll_status = DOLL_STATUS.RESERVE;
                }
                break;
        }

        notifyDollStatus();
    }

    private boolean isBlockStatus = false;

    private void notifyDollStatus()
    {
        if (isBlockStatus) return;
        mCallback.onBsUpdateDollStatus(doll_status, reserveNum);
        logStatus();
    }

    private void blockStatus()
    {
        isBlockStatus = true;
    }

    private void releaseStatus()
    {
        isBlockStatus = false;
    }

    private void logStatus()
    {
        String log = "DollStatus -> [dollStatus = " + doll_status + "][reserveNum = " + reserveNum + "]";
        log(log);
    }

    private void log(String actInfo, String params)
    {
        String log = "Requesting -> [" + actInfo + "]";
        log(log);
    }

    private void logSuccess()
    {
        String log = "Request Success";
        log(log);
    }

    private void logFailed(String msg)
    {
        String log = "Request Failed -> [msg = " + msg + "]";
        log(log);
    }

    private void logError(String errorMsg, String result)
    {
        String log = "Request error -> [errorMsg = " + errorMsg + "][result = " + result + "]";
        log(log);
    }

    private void log(String log)
    {
        WWLogHelper.getInstance().log("[Business]" + log);
    }
}
