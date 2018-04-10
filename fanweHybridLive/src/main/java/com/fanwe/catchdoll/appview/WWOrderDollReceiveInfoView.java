package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.catchdoll.adapter.WWOrderDollReceiveInfoAdapter;
import com.fanwe.catchdoll.model.WWOrderDollReceiveInfoModel;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

import java.util.List;

/**
 * des:订单娃娃领取详情
 * Created by yangwb
 * on 2017/12/5.
 */

public class WWOrderDollReceiveInfoView extends BaseAppView
{
    public WWOrderDollReceiveInfoView(Context context)
    {
        super(context);
        init();
    }

    public WWOrderDollReceiveInfoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWOrderDollReceiveInfoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }
    private TextView tv_receive_info;
    private SDRecyclerView rv_content;
    private WWOrderDollReceiveInfoAdapter mAdapter;

    private void init()
    {
        setContentView(R.layout.ww_view_order_doll_receive_info);
        rv_content = (SDRecyclerView) findViewById(R.id.rv_content);
        tv_receive_info = (TextView) findViewById(R.id.tv_receive_info);

        mAdapter = new WWOrderDollReceiveInfoAdapter(null, getActivity());
        rv_content.setAdapter(mAdapter);
    }

    public void setData(List<WWOrderDollReceiveInfoModel> detail_similar)
    {
        mAdapter.updateData(detail_similar);
    }

    public TextView getTv_receive_info()
    {
        return tv_receive_info;
    }
}
