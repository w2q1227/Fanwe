package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.activity.WWAddAddressActivity;
import com.fanwe.catchdoll.model.WWAddressModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.live.R;

import java.util.List;

/**
 * 配送地址列表适配
 * Created by LianCP on 2017/11/13.
 */
public class WWAddressListAdapter extends SDSimpleAdapter<WWAddressModel>
{
    public WWAddressListAdapter(List<WWAddressModel> listModel, Activity activity)
    {
        super(listModel, activity);
        //设置Item选择模式，单选、多选
        getSelectManager().setMode(SDSelectManager.Mode.MULTI);
    }

    @Override
    public int getLayoutId(int i, View view, ViewGroup viewGroup)
    {
        return R.layout.ww_item_address_list;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final WWAddressModel wwAddressModel)
    {
        LinearLayout ll_root = get(R.id.ll_root, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        TextView tv_phone = get(R.id.tv_phone, convertView);
        ImageView iv_set_default_address = get(R.id.iv_set_default_address, convertView);
        TextView tv_address = get(R.id.tv_address, convertView);
        FrameLayout fl_edit = get(R.id.fl_edit, convertView);
        FrameLayout fl_cb_checkbox = get(R.id.fl_cb_checkbox, convertView);
        ImageView cb_checkbox = get(R.id.cb_checkbox, convertView);

        tv_name.setText(wwAddressModel.getConsignee());
        tv_phone.setText(wwAddressModel.getMobile());
        tv_address.setText(wwAddressModel.getDetailedAddress());

        if (wwAddressModel.getIs_default() == 1)
        {
            iv_set_default_address.setVisibility(View.VISIBLE);
        } else
        {
            iv_set_default_address.setVisibility(View.GONE);
        }

        if (mIsDeleteAddress)
        {
            fl_edit.setVisibility(View.GONE);
            fl_cb_checkbox.setVisibility(View.VISIBLE);
        } else
        {
            fl_cb_checkbox.setVisibility(View.GONE);
            fl_edit.setVisibility(View.VISIBLE);
        }
        cb_checkbox.setSelected(wwAddressModel.isSelected());

        ll_root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, wwAddressModel, v);
            }
        });

        fl_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), WWAddAddressActivity.class);
                intent.putExtra(WWAddAddressActivity.EXTRA_JUMP_TYPE, 1);
                intent.putExtra(WWAddAddressActivity.EXTRA_JUMP_DATA, wwAddressModel);
                getActivity().startActivity(intent);
            }
        });
    }

    private boolean mIsDeleteAddress = false;

    public void setDeleteAddress(boolean mIsDeleteAddress)
    {
        this.mIsDeleteAddress = mIsDeleteAddress;
        notifyDataSetChanged();
    }

}
