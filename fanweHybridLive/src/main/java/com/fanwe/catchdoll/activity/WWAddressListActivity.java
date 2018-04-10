package com.fanwe.catchdoll.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.catchdoll.adapter.WWAddressListAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWESelectAddress;
import com.fanwe.catchdoll.model.WWAddressListModel;
import com.fanwe.catchdoll.model.WWAddressModel;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.lib.pulltorefresh.SDPullToRefreshView;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 配送地址列表
 * Created by LianCP on 2017/11/13.
 */
public class WWAddressListActivity extends BaseActivity implements SDItemClickCallback<WWAddressModel>
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_address_list);
        findView();
        initValue();
    }

    private FrameLayout mFlTitleLeft;
    private TextView mTvTitleName;
    private FrameLayout mFlTitleRight;
    private TextView mTvTitleRight;
    private ListView mListView;
    private TextView mTvSetDefaultAddress;
    private TextView mTvAddReceiveAddress;

    private WWAddressListAdapter mAdapter;

    protected int mPage = 1;
    private int mHasNext = 0;
    private boolean mDeleteAddressFlag = false;

    private void findView()
    {
        mFlTitleLeft = (FrameLayout) findViewById(R.id.fl_title_left);
        mTvTitleName = (TextView) findViewById(R.id.tv_title_name);
        mFlTitleRight = (FrameLayout) findViewById(R.id.fl_title_right);
        mTvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        mListView = (ListView) findViewById(R.id.lv_content);
        mTvSetDefaultAddress = (TextView) findViewById(R.id.tv_set_default_address);
        mTvAddReceiveAddress = (TextView) findViewById(R.id.tv_add_receive_address);

        setBtnOnClick();
    }

    private void setBtnOnClick()
    {
        mFlTitleLeft.setOnClickListener(this);
        mFlTitleRight.setOnClickListener(this);
        mTvSetDefaultAddress.setOnClickListener(this);
        mTvAddReceiveAddress.setOnClickListener(this);
    }

    private void initValue()
    {
        mAdapter = new WWAddressListAdapter(null, getActivity());
        mListView.setAdapter(mAdapter);
        getPullToRefreshViewWrapper().setPullToRefreshView((SDPullToRefreshView) findViewById(R.id.view_pull_to_refresh));
        getPullToRefreshViewWrapper().setOnRefreshCallbackWrapper(new IPullToRefreshViewWrapper.OnRefreshCallbackWrapper()
        {
            @Override
            public void onRefreshingFromHeader()
            {
                refreshViewer();
            }

            @Override
            public void onRefreshingFromFooter()
            {
                loadMoreViewer();
            }
        });
        mAdapter.setItemClickCallback(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refreshViewer();
    }

    @Override
    public void onItemClick(int position, WWAddressModel wwAddressModel, View view)
    {
        if (mDeleteAddressFlag)
        {
            mAdapter.getSelectManager().performClick(wwAddressModel);
        } else
        {
            WWESelectAddress evet = new WWESelectAddress();
            evet.mSelectAddress = wwAddressModel;
            EventBus.getDefault().post(evet);
            finish();
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.fl_title_left:
                finish();
                break;
            case R.id.fl_title_right:
                if (!mDeleteAddressFlag)
                {
                    mDeleteAddressFlag = true;
                    mTvTitleRight.setText("取消");
                    mAdapter.setDeleteAddress(true);
                    mTvAddReceiveAddress.setText("删除地址");
                    mTvTitleName.setText("地址管理");
                    mTvSetDefaultAddress.setVisibility(View.VISIBLE);
                } else
                {
                    mDeleteAddressFlag = false;
                    mTvTitleRight.setText("编辑");
                    mAdapter.setDeleteAddress(false);
                    mTvAddReceiveAddress.setText("新增地址");
                    mTvTitleName.setText("选择收货地址");
                    mTvSetDefaultAddress.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_set_default_address:
                setDefaultAddress(getSetDefaultAddressId());
                break;
            case R.id.tv_add_receive_address:
                if (mDeleteAddressFlag)
                {
                    deleteAddress(getDeleteAddressIds());
                } else
                {
                    Intent intent = new Intent(getActivity(), WWAddAddressActivity.class);
                    intent.putExtra(WWAddAddressActivity.EXTRA_JUMP_TYPE, 0);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private String getSetDefaultAddressId()
    {
        StringBuffer sb = new StringBuffer();
        List<WWAddressModel> mListAddressModel =  mAdapter.getSelectManager().getSelectedItems();
        if (null != mListAddressModel)
        {
            switch (mListAddressModel.size())
            {
                case 0:
                    //未选择要设置成默认地址的地址
                    sb.append("");
                    break;
                case 1:
                    sb.append(mListAddressModel.get(0).getId());
                    break;
                default:
                    //选择了多条地址要设置成默认地址
                    sb.append("-1");
                    break;
            }
        }
        return sb.toString();
    }

    private String getDeleteAddressIds()
    {
        StringBuffer sb = new StringBuffer();
        List<WWAddressModel> mListAddressModel =  mAdapter.getSelectManager().getSelectedItems();
        if (null != mListAddressModel)
        {
            for (int i = 0; i < mListAddressModel.size(); i++)
            {
                if (i != 0)
                {
                    sb.append(",");
                }
                sb.append(mListAddressModel.get(i).getId());
            }
        }
        return sb.toString();
    }

    private void refreshViewer()
    {
        mPage = 1;
        requestData(false);
    }

    private void loadMoreViewer()
    {
        if (mHasNext == 1)
        {
            mPage++;
            requestData(true);
        } else
        {
            onRefreshComplete();
            SDToast.showToast("没有更多数据");
        }
    }

    private void setDefaultAddress(String id)
    {
        if (TextUtils.isEmpty(id))
        {
            SDToast.showToast("请先选择要设置的默认地址！");
            return;
        }
        if (TextUtils.equals("-1", id))
        {
            SDToast.showToast("默认地址只能设置一条，请去掉多余的地址！");
            return;
        }
        WWCommonInterface.requestSetDefaultAddress(id, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    refreshViewer();
                }
            }
        });
    }

    protected void deleteAddress(String ids)
    {
        if (TextUtils.isEmpty(ids))
        {
            SDToast.showToast("请先选择要删除的地址！");
            return;
        }
        WWCommonInterface.requestDeleteAddress(ids, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    for (WWAddressModel addressModel : mAdapter.getSelectManager().getSelectedItems())
                    {
                        mAdapter.removeData(addressModel);
                    }
                }
            }
        });
    }

    /**
     * 请求数据
     */
    protected void requestData(final boolean isLoadMore)
    {
        WWCommonInterface.requestAddressList(mPage, new AppRequestCallback<WWAddressListModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    if (isLoadMore)
                    {
                        mAdapter.appendData(actModel.getConsignee_list());
                    } else
                    {
                        mAdapter.updateData(actModel.getConsignee_list());
                    }
                    mHasNext = actModel.getHas_next();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                onRefreshComplete();
                super.onFinish(resp);
            }
        });
    }

    protected void onRefreshComplete()
    {
        if (mListView != null)
        {
            getPullToRefreshViewWrapper().stopRefreshing();
        }
    }
}
