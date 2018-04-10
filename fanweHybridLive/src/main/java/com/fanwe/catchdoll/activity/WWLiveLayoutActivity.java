package com.fanwe.catchdoll.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.fanwe.catchdoll.appview.WWBaseCatchResultView;
import com.fanwe.catchdoll.appview.WWCatchFailView;
import com.fanwe.catchdoll.appview.WWCatchSuccessView;
import com.fanwe.catchdoll.appview.WWLiveControlPanel;
import com.fanwe.catchdoll.appview.WWLiveTopPanel;
import com.fanwe.catchdoll.appview.videoHolder.WWLiveVideoHolderView;
import com.fanwe.catchdoll.appview.WWLiveWatcherPanel;
import com.fanwe.catchdoll.appview.WWRoomSendMsgView;
import com.fanwe.catchdoll.business.WaWaLiveBusiness;
import com.fanwe.catchdoll.control.WWLiveView;
import com.fanwe.catchdoll.dialog.WWGlobalStartGameDialog;
import com.fanwe.catchdoll.dialog.WWRepairDialog;
import com.fanwe.catchdoll.event.WWERechargeResult;
import com.fanwe.catchdoll.event.WWLiveException;
import com.fanwe.catchdoll.model.WWAppDollModel;
import com.fanwe.catchdoll.model.WWCustomMsgGame;
import com.fanwe.catchdoll.model.WWCustomMsgQueueUpdate;
import com.fanwe.catchdoll.model.WWDollQuestionActModel;
import com.fanwe.catchdoll.model.WWDollQuestionModel;
import com.fanwe.catchdoll.model.WWReserveInfo;
import com.fanwe.catchdoll.model.WWSettingsModel;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.lib.dialog.ISDDialogConfirm;
import com.fanwe.catchdoll.util.WWLogHelper;
import com.fanwe.lib.dialog.ISDDialogConfirm;
import com.fanwe.lib.dialog.ISDDialogMenu;
import com.fanwe.lib.dialog.impl.SDDialogBase;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.listener.SDSizeChangedCallback;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewSizeListener;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.dialog.common.AppDialogConfirm;
import com.fanwe.live.event.EImOnForceOffline;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.UserModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/11/17.
 */

public class WWLiveLayoutActivity extends WWLiveActivity implements
        WWLiveWatcherPanel.WatcherCallback,
        WWLiveTopPanel.Callback,
        WWLiveVideoHolderView.Callback,
        WWBaseCatchResultView.Callback
{
    public static final String EXTRA_QUICK_START = "quick_start";
    public static final String EXTRA_PUSH_START = "push_start";

    private WWRoomSendMsgView mRoomSendMsgView;

    private WWLiveTopPanel mTopPanel;
    private WWLiveVideoHolderView mWatcherView;
    private WWLiveControlPanel mControlPanel;
    private WWLiveWatcherPanel mWatcherPanel;

    private WWCatchSuccessView mCatchSuccessView;
    private WWCatchFailView mCatchFailView;

    private FrameLayout flRoot;
    private FrameLayout flVideoContent;
    private FrameLayout flTopInfo;
    private FrameLayout flBottomPanel;

    private SDViewSizeListener mViewSizeListener = new SDViewSizeListener()
    {
        private Rect mRect = new Rect();

        @Override
        protected int onGetHeight(View view)
        {
            view.getWindowVisibleDisplayFrame(mRect);
            return mRect.height();
        }
    };
    private WWRepairDialog repairDialog;
    private List<Object> listQuestion = new ArrayList<>();

    public static Intent startIntent(int roomId, String groupId, Context context)
    {
        Intent intent = new Intent(context, WWLiveLayoutActivity.class);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_ROOM_ID, roomId);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_GROUP_ID, groupId);
        return intent;
    }

    public static Intent quickStartIntent(int roomId, String groupId, Context context)
    {
        Intent intent = new Intent(context, WWLiveLayoutActivity.class);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_ROOM_ID, roomId);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_GROUP_ID, groupId);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_QUICK_START, true);
        return intent;
    }

    public static Intent pushStartIntent(int roomId, String groupId, WWReserveInfo reserveInfo, Context context)
    {
        LiveInformation.getInstance().setCurrentReserve(reserveInfo);
        Intent intent = new Intent(context, WWLiveLayoutActivity.class);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_ROOM_ID, roomId);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_GROUP_ID, groupId);
        intent.putExtra(WWLiveLayoutActivity.EXTRA_PUSH_START, true);
        return intent;
    }

    public static void resolveReserveInfo()
    {
        if (LiveInformation.getInstance().getCurrentReserve() != null && !LiveInformation.getInstance().isReserveConfirm())
        {
            WWGlobalStartGameDialog dialog = new WWGlobalStartGameDialog(SDActivityManager.getInstance().getLastActivity());
            dialog.updateReserve();
            dialog.show();
        }
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);

        setContentView(R.layout.ww_act_live);

        initWindows();
        initViews();
        begin();

    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        int oldRoomId = getRoomId();
        int newRoomId = intent.getIntExtra(EXTRA_ROOM_ID, 0);

        if (newRoomId == 0) return;
        if (newRoomId == oldRoomId)
        {
            boolean isPushStart = intent.getBooleanExtra(EXTRA_PUSH_START, false);

            if (isPushStart)
            {
                pushStart();
            } else
            {
                quickStart();
            }
        } else
        {
            getViewerIM().quitGroup(null);
            LiveInformation.getInstance().exitRoom();

            setIntent(intent);
            String groupId = getIntent().getStringExtra(EXTRA_GROUP_ID);
            String createrId = getIntent().getStringExtra(EXTRA_CREATER_ID);

            LiveInformation.getInstance().setRoomId(newRoomId);
            LiveInformation.getInstance().setGroupId(groupId);
            LiveInformation.getInstance().setCreaterId(createrId);
            initIM();
            begin();
        }
    }

    @Override
    protected WWLiveView.VideoHolder getVideoHolder()
    {
        return getVideoHolderView();
    }

    private void initWindows()
    {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initViews()
    {
        flRoot = (FrameLayout) findViewById(R.id.fl_root);
        flVideoContent = (FrameLayout) findViewById(R.id.fl_video_content);
        flTopInfo = (FrameLayout) findViewById(R.id.fl_top_info);
        flBottomPanel = (FrameLayout) findViewById(R.id.fl_bottom_panel);

        SDViewUtil.replaceView(flTopInfo, getTopPanel());
        SDViewUtil.replaceView(flVideoContent, getVideoHolderView());
        addRoomSendMsgView();

        mViewSizeListener.setView(findViewById(android.R.id.content)).setCallback(new SDSizeChangedCallback<View>()
        {
            @Override
            public void onWidthChanged(int i, int i1, View view)
            {

            }

            @Override
            public void onHeightChanged(int newHeight, int oldHeight, View view)
            {
                if (flRoot != null && newHeight != 0 && oldHeight != 0)
                {
                    int scrollBy = oldHeight - newHeight;
                    int statusHeight = SDViewUtil.getStatusBarHeight();
                    if (Math.abs(scrollBy) == Math.abs(statusHeight)) return;
                    flRoot.scrollBy(0, scrollBy);

                    if (flRoot.getScrollY() == 0)
                    {
                        showSendMsgView(false);
                    }

                }
            }
        });
    }


    @Override
    protected void onWatchView()
    {
        getVideoHolderView().onWatcherView();
        SDViewUtil.replaceView(flBottomPanel, getWatcherPanel());
        flVideoContent.requestLayout();
    }

    @Override
    protected void onGameView()
    {
        getVideoHolderView().onGameView();
        SDViewUtil.replaceView(flBottomPanel, getControlPanel());
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);
        getTopPanel().onLiveRefreshViewerList(actModel.getViewer());
        getLiveBusiness().setViewerNumber(actModel.getViewer_num());
        getWatcherPanel().updateUserCoins(actModel.getDiamonds());
        getVideoHolderView().setGamer(actModel.getDoll().getUser());
        getVideoHolderView().getMainVideoView().setBackground(actModel.getLive_image());
        getVideoHolderView().getSubVideoView().setBackground(actModel.getLive_image());
        WWAppDollModel doll = actModel.getDoll();
        sendViewerJoinMsg();
        if (doll != null)
        {
            getWatcherPanel().setCostCoins(doll.getPrice());
        }
    }

    @Override
    public void onBsRequestEndGameSuccess(int countDown, boolean isGrab)
    {
        super.onBsRequestEndGameSuccess(countDown, isGrab);
        coverResult(countDown, isGrab);
        getVideoHolderView().stopCountDown();
    }

    @Override
    public void onBsRequestEndGameFailed(String errMsg, int gameId, boolean isGrab)
    {
        super.onBsRequestEndGameFailed(errMsg, gameId, isGrab);
    }

    @Override
    public void onBsUpdateRoundTime(int roundTime)
    {
        getVideoHolderView().setRoundTime(roundTime);
    }

    @Override
    public void onReady()
    {
        super.onReady();
        getVideoHolderView().startCountDown();
    }

    @Override
    public void onBsRefreshViewerList(List<UserModel> listModel)
    {
        getTopPanel().onLiveRefreshViewerList(listModel);
    }

    @Override
    public void onBsViewerNumberChange(int viewerNumber)
    {
        getTopPanel().updateViewerNumber(viewerNumber);
    }

    @Override
    public void onBsUpdateCoin(long diamonds)
    {
        getWatcherPanel().updateUserCoins(diamonds);
    }


    @Override
    public void onBsUpdateDollStatus(WaWaLiveBusiness.DOLL_STATUS doll_status, int reserveNum)
    {
        getWatcherPanel().setPlayButton(doll_status, reserveNum);
    }

    @Override
    public void onMsgWWGameStart(WWCustomMsgGame wwCustomMsgGame)
    {
        super.onMsgWWGameStart(wwCustomMsgGame);
        getVideoHolderView().setGamer(wwCustomMsgGame.getSender());
    }

    @Override
    public void onMsgWWGameEnd(WWCustomMsgGame wwCustomMsgGame)
    {
        super.onMsgWWGameEnd(wwCustomMsgGame);
        getVideoHolderView().clearUser();
    }

    @Override
    public void onMsgWWQueueUpdate(WWCustomMsgQueueUpdate wwCustomMsgGame)
    {
        super.onMsgWWQueueUpdate(wwCustomMsgGame);
        if (wwCustomMsgGame.getNum() == 0)
        {
            getVideoHolderView().clearConfirmUser();
        }
    }

    @Override
    public void onMsgWWQueueWaitConfirm(WWCustomMsgGame wwCustomMsgGame)
    {
        super.onMsgWWQueueWaitConfirm(wwCustomMsgGame);
        getVideoHolderView().setAcceptor(wwCustomMsgGame.getSender());
    }

    public void onEventMainThread(WWERechargeResult event)
    {
        getWatcherPanel().updateUserCoins(event.balance);
    }

    @Override
    public void onEventMainThread(EImOnForceOffline event)
    {
        finish();
    }

    public void onEventMainThread(WWLiveException e)
    {
        dismissProgressDialog();
        switch (e.exceptionCode)
        {
            case WWLiveException.CLOSED_IN_GAME:
                SDToast.showToast("游戏异常");
                watchMode();
                break;
            case WWLiveException.START_GAME_FAILED:
                SDToast.showToast("开启游戏失败");
                watchMode();
                break;
        }
//        WWLogHelper.getInstance().upLoad();
    }

    /*------------------------- watcher callback start ------------------------------------*/
    @Override
    public void onClickPlay()
    {
        playGame();
    }

    @Override
    public void onClickWWDetail()
    {
        Intent intent = new Intent(this, WWDollDetailActivity.class);
        intent.putExtra(WWDollDetailActivity.EXTRA_ROOM_ID, getRoomId());
        intent.putExtra(WWDollDetailActivity.EXTRA_URL, getRoomInfo().getDoll().getUrl());
        startActivity(intent);
    }

    @Override
    public void onClickRecharge()
    {
        Intent intent = new Intent(getActivity(), WWRechargeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickMsg()
    {
        showSendMsgView(true);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    /*------------------------- live top callback start------------------------------------*/
    @Override
    public void onClickFinish()
    {
        finish();
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    /*------------------------- catch result callback start--------------------------------*/

    @Override
    public void onCountDownFinish(View view)
    {
        if (view instanceof WWBaseCatchResultView)
        {
            resetToWatch();
            finishGame();
        } else if (view instanceof WWLiveVideoHolderView)
        {
            getWaWaSDK().roundTimeFinish();
        }
    }

    @Override
    public void onClickClose()
    {
        resetToWatch();
        finishGame();
    }

    @Override
    public void onClickPlayAgain()
    {
        resetToWatch();
        quickStart();
    }

    @Override
    public void onClickReceive()
    {
        resetToWatch();
        finishGame();

        if (LiveInformation.getInstance().getDollId() == 0)
        {
            SDToast.showToast("娃娃领取订单还未生成，请稍后在我的娃娃列表中查看");
        } else
        {
            Intent intent = new Intent(this, WWReceiveDollActivity.class);
            intent.putExtra(WWReceiveDollActivity.EXTRA_ID, String.valueOf(LiveInformation.getInstance().getDollId()));
            startActivity(intent);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    //点击报障按钮
    @Override
    public void onClickRepair()
    {
        showProgressDialog("正在加载...");
        getWaWaLiveBusiness().requestDollQuestion();
    }

    @Override
    public void onClickUser(int userId)
    {
        Intent intent = new Intent(getActivity(), WWHisDollListActivity.class);
        intent.putExtra(WWHisDollListActivity.EXTRA_ROOM_ID, getRoomId());
        intent.putExtra(WWHisDollListActivity.EXTRA_USER_ID, userId);
        getActivity().startActivity(intent);
    }

    private void resetToWatch()
    {
        clearResult();
        watchMode();
    }

    private void coverResult(int countDown, boolean isGrab)
    {
        clearResult();
        WWBaseCatchResultView resultView = isGrab ? getCatchSuccessView() : getCatchFailView();
        resultView.startCountDown(countDown);
        SDViewUtil.addView(flRoot, resultView);
    }

    private void clearResult()
    {
        SDViewUtil.removeView(getCatchSuccessView());
        SDViewUtil.removeView(getCatchFailView());
    }

    protected void addRoomSendMsgView()
    {
        if (mRoomSendMsgView == null)
        {
            mRoomSendMsgView = new WWRoomSendMsgView(this);
            mRoomSendMsgView.addVisibilityCallback(new SDViewVisibilityCallback()
            {
                @Override
                public void onViewVisibilityChanged(View view, int visibility)
                {
                    if (View.VISIBLE == visibility)
                    {
                        flBottomPanel.setVisibility(View.GONE);
                    } else
                    {
                        flBottomPanel.setVisibility(View.VISIBLE);
                    }
                }
            });
            replaceView(R.id.fl_send_msg, mRoomSendMsgView);
        }
    }

    protected void showSendMsgView(boolean show)
    {
        if (show)
        {
            SDViewUtil.setVisible(mRoomSendMsgView);
        } else
        {
            SDViewUtil.setInvisible(mRoomSendMsgView);
        }
    }


    private WWLiveTopPanel getTopPanel()
    {
        if (mTopPanel == null)
        {
            mTopPanel = new WWLiveTopPanel(this);
            mTopPanel.setCallback(this);
        }
        return mTopPanel;
    }

    private WWLiveVideoHolderView getVideoHolderView()
    {
        if (mWatcherView == null)
        {
            mWatcherView = new WWLiveVideoHolderView(this);
            mWatcherView.setCallback(this);
        }
        return mWatcherView;
    }

    private WWLiveControlPanel getControlPanel()
    {
        if (mControlPanel == null)
        {
            mControlPanel = new WWLiveControlPanel(this);
            mControlPanel.setWaWaSDK(getWaWaSDK());
            mControlPanel.setSoundPlayer(getSoundEffectPlayer());
        }
        return mControlPanel;
    }

    private WWLiveWatcherPanel getWatcherPanel()
    {
        if (mWatcherPanel == null)
        {
            mWatcherPanel = new WWLiveWatcherPanel(this);
            mWatcherPanel.setCallback(this);
        }
        return mWatcherPanel;
    }

    private WWCatchSuccessView getCatchSuccessView()
    {
        if (mCatchSuccessView == null)
        {
            mCatchSuccessView = new WWCatchSuccessView(this);
            mCatchSuccessView.setCallBack(this);

        }
        return mCatchSuccessView;
    }

    private WWCatchFailView getCatchFailView()
    {
        if (mCatchFailView == null)
        {
            mCatchFailView = new WWCatchFailView(this);
            mCatchFailView.setCallBack(this);

        }
        return mCatchFailView;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    protected void onDestroy()
    {
        if (mWatcherView != null)
        {
            mWatcherView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBsRequestDollQuestionSuccess(WWDollQuestionActModel model)
    {
        dismissProgressDialog();
        showDollRequestionDialog(model);
    }

    private void showDollRequestionDialog(final WWDollQuestionActModel model)
    {
        if (repairDialog == null)
        {
            repairDialog = new WWRepairDialog(this);
        }
        listQuestion.clear();
        for (WWDollQuestionModel wwDollQuestionModel : model.getList())
        {
            listQuestion.add(wwDollQuestionModel.getQuestion());
        }
        repairDialog.setItems(listQuestion);
        repairDialog.setCallback(new ISDDialogMenu.Callback()
        {
            @Override
            public void onClickItem(View view, int i, SDDialogBase sdDialogBase)
            {
                repairDialog.dismiss();
                showProgressDialog("正在提交...");
                getWaWaLiveBusiness().requestSaveQuestion(getRoomId(), getWaWaSDK().getGameId(), getRoomInfo().getDollId(), model.getList().get(i).getId(), null);
            }

            @Override
            public void onClickCancel(View view, SDDialogBase sdDialogBase)
            {

            }
        });
        repairDialog.showBottom();
    }


    @Override
    public void onBsRequestDollQuestionFailed(String errMsg)
    {
        dismissProgressDialog();
        SDToast.showToast(errMsg);
    }

    @Override
    public void onBsRequestSaveQuestionSuccess()
    {
        dismissProgressDialog();
        SDToast.showToast("提交成功");
    }

    @Override
    public void onBsRequestSaveQuestionFailed(String errMsg)
    {
        dismissProgressDialog();
        SDToast.showToast(errMsg);
    }

    @Override
    public void onNetworkChanged(SDNetworkReceiver.NetworkType type)
    {
        if (type == SDNetworkReceiver.NetworkType.Mobile && !AppRuntimeWorker.isHideNetworkNotice())
        {
            AppDialogConfirm dialog = new AppDialogConfirm(this);
            dialog.setTextContent("当前处于数据网络下，会耗费较多流量，是否继续？").setTextCancel("否").setTextConfirm("是")
                    .setCallback(new ISDDialogConfirm.Callback()
                    {
                        @Override
                        public void onClickCancel(View v, SDDialogBase dialog)
                        {
                            finish();
                        }

                        @Override
                        public void onClickConfirm(View v, SDDialogBase dialog)
                        {

                        }
                    }).show();
        }
        super.onNetworkChanged(type);
    }
}
