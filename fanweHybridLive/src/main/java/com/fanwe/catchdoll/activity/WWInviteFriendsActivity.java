package com.fanwe.catchdoll.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.adapter.WWInviteHistoryAdapter;
import com.fanwe.catchdoll.appview.WWEmptyView;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWInviteHistoryActModel;
import com.fanwe.catchdoll.model.WWShareModel;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.lib.pulltorefresh.SDPullToRefreshView;
import com.fanwe.lib.statelayout.SDStateLayout;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.model.FPageModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * <邀请好友>
 * Created by wwb on 2017/12/14 09:00.
 */

public class WWInviteFriendsActivity extends BaseActivity
{

    private SDRecyclerView mRecyclerView;
    private SDPullToRefreshView mPullToRefreshView;
    private FPageModel mPageModel = new FPageModel();
    private WWInviteHistoryAdapter mAdapter;

    private RelativeLayout rl_back;
    private TextView tv_invite_title;
    private TextView tv_bonuse_des;
    private TextView tv_invite_code;//我的邀请码
    private TextView tv_invite_number;//邀请了多少人
    private TextView tv_bonuse_total;//获得多少奖励
    private TextView tv_bonuse_number_limit_dsc;//邀请人数上限

    private TextView tv_invite;
    private WWShareModel mShareModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_invite_friends);

        init();

    }

    private void init()
    {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_invite_title = (TextView) findViewById(R.id.tv_invite_title);
        tv_bonuse_des = (TextView) findViewById(R.id.tv_bonuse_des);
        tv_invite_code = (TextView) findViewById(R.id.tv_invite_code);
        tv_invite_number = (TextView) findViewById(R.id.tv_invite_number);
        tv_bonuse_total = (TextView) findViewById(R.id.tv_bonuse_total);
        tv_bonuse_number_limit_dsc = (TextView) findViewById(R.id.tv_bonuse_number_limit_dsc);

        tv_invite = (TextView) findViewById(R.id.tv_invite);
        tv_invite.setOnClickListener(this);
        rl_back.setOnClickListener(this);

        mRecyclerView = (SDRecyclerView) findViewById(R.id.lv_content);
        mRecyclerView.setLinearVertical();

        mPullToRefreshView = (SDPullToRefreshView) findViewById(R.id.view_pull_to_refresh);

        setAdapter();

        getPullToRefreshViewWrapper().setPullToRefreshView(mPullToRefreshView);
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                requestData(false);
            }

            @Override
            public void onRefreshingFromFooter()
            {
                if (mPageModel.hasNextPage())
                {
                    requestData(true);
                } else
                {
                    SDToast.showToast("没有更多数据了");
                    getPullToRefreshViewWrapper().stopRefreshing();
                }
            }
        });

        requestData(false);
    }

    private void setAdapter()
    {
        mAdapter = new WWInviteHistoryAdapter(null, this);

        mRecyclerView.setAdapter(mAdapter);

        setStateLayout((SDStateLayout) findViewById(R.id.view_state_layout));
        getStateLayout().setAdapter(mAdapter);

        WWEmptyView view_empty = new WWEmptyView(this);
        SDViewUtil.setGone(view_empty.getImageHint());
        SDViewBinder.setTextView(view_empty.getTextHint(), "您暂时还没有邀请到好友");

        getStateLayout().getEmptyView().setContentView(view_empty);
        getStateLayout().setContentTop(false);
    }

    public void requestData(final boolean isLoadMore)
    {
        int page = mPageModel.getPageForRequest(isLoadMore);
        WWCommonInterface.requestInviteHistory(page, new AppRequestCallback<WWInviteHistoryActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    setWWInviteHistoryActModel(actModel);

                    mPageModel.updatePageOnSuccess(isLoadMore, getActModel().getHas_next());
                    if (isLoadMore)
                    {
                        mAdapter.appendData(actModel.getList());
                    } else
                    {
                        mAdapter.updateData(actModel.getList());
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                getPullToRefreshViewWrapper().stopRefreshing();
            }
        });
    }

    private void setWWInviteHistoryActModel(WWInviteHistoryActModel actModel)
    {
        this.mShareModel = actModel.getShare();

        SDViewBinder.setTextView(tv_invite_title, actModel.getTitle());
        SDViewBinder.setTextView(tv_bonuse_des, actModel.getBonuse_des());
        SDViewBinder.setTextView(tv_invite_code, "我的邀请码:" + actModel.getInvite_code());
        SDViewBinder.setTextView(tv_invite_number, actModel.getNum_format());
        SDViewBinder.setTextView(tv_bonuse_total, actModel.getBonuse_total_format());
        SDViewBinder.setTextView(tv_bonuse_number_limit_dsc, actModel.getBonuse_num_limit_des());
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_invite)
        {
            inviteFriend();
        } else if (v == rl_back)
        {
            finish();
        }
    }

    private void inviteFriend()
    {
        if (mShareModel != null)
        {
            openShare(mShareModel);
        }
    }

    private void openShare(WWShareModel model)
    {
        String title = model.getShare_title();
        String content = model.getDes();
        String imageUrl = model.getShare_img();
        String clickUrl = model.getDownload_url();

        UmengSocialManager.openShare(title, content, imageUrl, clickUrl, this, new UMShareListener()
        {

            @Override
            public void onStart(SHARE_MEDIA share_media)
            {

            }

            @Override
            public void onResult(SHARE_MEDIA platform)
            {
                SDToast.showToast(platform + " 分享成功啦");
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t)
            {
                SDToast.showToast(platform + " 分享失败啦");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform)
            {
                SDToast.showToast(platform + " 分享取消了");
            }
        });
    }
}
