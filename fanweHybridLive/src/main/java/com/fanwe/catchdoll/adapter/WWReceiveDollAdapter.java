package com.fanwe.catchdoll.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.catchdoll.model.WWReceiveDollModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */
public class WWReceiveDollAdapter  extends SDSimpleAdapter<WWReceiveDollModel>
{
    public WWReceiveDollAdapter(List<WWReceiveDollModel> listModel, Activity activity)
    {
        super(listModel, activity);
        //设置Item选择模式，单选、多选
        getSelectManager().setMode(SDSelectManager.Mode.MULTI);
    }

    @Override
    public int getLayoutId(int i, View view, ViewGroup viewGroup)
    {
        return R.layout.ww_item_receive_doll;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final WWReceiveDollModel wwReceiveDollModel)
    {
        LinearLayout ll_root = get(R.id.ll_root, convertView);
        View view_division = get(R.id.view_division, convertView);
        View view_line = get(R.id.view_line, convertView);
        ImageView iv_photo = get(R.id.iv_photo, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        TextView tv_time = get(R.id.tv_time, convertView);
        ImageView cb_checkbox = get(R.id.cb_checkbox, convertView);

        GlideUtil.load(wwReceiveDollModel.getImg()).into(iv_photo);
        tv_name.setText(wwReceiveDollModel.getDoll_name());
        tv_time.setText(wwReceiveDollModel.getGrab_time());
        if (position == 0)
        {
            view_division.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        } else
        {
            view_division.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
        }
        cb_checkbox.setSelected(wwReceiveDollModel.isSelected());

        ll_root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, wwReceiveDollModel, v);
            }
        });
    }

}
