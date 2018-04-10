package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.adapter.WWReceiveDollAdapter;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWReceiveDollModel;
import com.fanwe.catchdoll.model.WWTotalFreightModel;
import com.fanwe.catchdoll.view.WWListViewForScrollView;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class WWSelectDollListView extends BaseAppView implements SDItemClickCallback<WWReceiveDollModel>
{
    public WWSelectDollListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWSelectDollListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWSelectDollListView(Context context)
    {
        super(context);
        init();
    }

    private LinearLayout ll_tip;
    private FrameLayout fl_selected;
    private TextView tv_selected_text;
    private TextView tv_good_total_num;
    private TextView tv_good_freight;
    private TextView tv_same_group;

    private WWListViewForScrollView lv_content;
    private WWReceiveDollAdapter mAdapter;

    private boolean mSelectAll = false;

    private String mFreight = "";
    private String mTotalFreight = "";

    private void init()
    {
        setContentView(R.layout.ww_view_select_doll_list);
        ll_tip = (LinearLayout) findViewById(R.id.ll_tip);
        fl_selected = (FrameLayout) findViewById(R.id.fl_selected);
        fl_selected.setOnClickListener(this);
        tv_selected_text = (TextView) findViewById(R.id.tv_selected_text);
        lv_content = (WWListViewForScrollView) findViewById(R.id.lv_content);
        tv_good_total_num = (TextView) findViewById(R.id.tv_good_total_num);
        tv_good_freight = (TextView) findViewById(R.id.tv_good_freight);
        tv_same_group = (TextView) findViewById(R.id.tv_same_group);
        initAdapter();
    }

    private void initAdapter()
    {
        mAdapter = new WWReceiveDollAdapter(null, getActivity());
        lv_content.setAdapter(mAdapter);
        mAdapter.setItemClickCallback(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.fl_selected:
                clickSelect();
                break;
            default:
                break;
        }
    }

    private void clickSelect()
    {
        if (!mSelectAll)
        {
            selectAll();
        } else
        {
            noSelectAll();
        }
    }

    private void selectAll()
    {
        mSelectAll = true;
        mAdapter.getSelectManager().selectAll();
        tv_selected_text.setText("取消");
        setTotalNum();
    }

    private void noSelectAll()
    {
        mSelectAll = false;
        tv_selected_text.setText("全选");
        cancelSelectAll();
        setTotalNum();
    }

    private void setTotalNum()
    {
        int num = selectNum();
        tv_good_total_num.setText("共计"+ num +"件商品");
        requestTotalFreight(num, mFreight);
    }

    public int selectNum()
    {
        return mAdapter.getSelectManager().getSelectedItems().size() + 1;
    }

    @Override
    public void onItemClick(int position, WWReceiveDollModel wwReceiveDollModel, View view)
    {
        mAdapter.getSelectManager().performClick(wwReceiveDollModel);
        if (isSelectAll())
        {
            mSelectAll = true;
            tv_selected_text.setText("取消");
        } else
        {
            mSelectAll = false;
            tv_selected_text.setText("全选");
        }
        setTotalNum();
    }

    /**
     * 是否已经全选
     * @return true已全选，false未全选
     */
    private boolean isSelectAll()
    {
        return mAdapter.getSelectManager().getSelectedIndexs().size() == mAdapter.getData().size();
    }

    /**
     * 取消全选
     */
    private void cancelSelectAll()
    {
        List<WWReceiveDollModel> modelListReceiveDollModel = mAdapter.getData();
        if (null != modelListReceiveDollModel)
        {
            for (WWReceiveDollModel wwReceiveDollModel : modelListReceiveDollModel)
            {
                mAdapter.getSelectManager().setSelected(wwReceiveDollModel, false);
            }
        }
    }

    public String getTotalFreight()
    {
        return mTotalFreight;
    }

    /**
     * 获取选中的ID
     * @return
     */
    public String getSelectGoodsIds(String fristId)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(fristId);
        List<WWReceiveDollModel> mListReceiveDollModel =  mAdapter.getSelectManager().getSelectedItems();
        if (null != mListReceiveDollModel)
        {
            for (int i = 0; i < mListReceiveDollModel.size(); i++)
            {
                sb.append(",");
                sb.append(mListReceiveDollModel.get(i).getId());
            }
        }
        return sb.toString();
    }

    public void setDatas(List<WWReceiveDollModel> detail_similar, String freight)
    {
        mAdapter.updateData(detail_similar);
        mFreight = freight;
        selectAll();
    }

    public void setListViewVisible(boolean visible)
    {
        if (visible)
        {
            ll_tip.setVisibility(View.VISIBLE);
        } else
        {
            ll_tip.setVisibility(View.GONE);
        }
    }

    private void requestTotalFreight(int num, String freight)
    {
        WWCommonInterface.requestTotalFreight(num, freight, new AppRequestCallback<WWTotalFreightModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mTotalFreight = actModel.getTotal_freight();
                    SDViewBinder.setTextView(tv_good_freight,
                            "运费：￥" + mTotalFreight + "元");
                }
            }
        });
    }

    public TextView getTv_same_group()
    {
        return tv_same_group;
    }
}
