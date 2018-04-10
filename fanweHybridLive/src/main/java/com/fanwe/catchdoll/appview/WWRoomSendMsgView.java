package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomSendMsgView;

/**
 * Created by Administrator on 2017/12/6.
 */

public class WWRoomSendMsgView extends RoomSendMsgView
{

    public WWRoomSendMsgView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public WWRoomSendMsgView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WWRoomSendMsgView(Context context)
    {
        super(context);
    }

    @Override
    protected void init()
    {
        super.init();
        setBackgroundColor(SDResourcesUtil.getColor(R.color.white));
        et_content.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() > MAX_INPUT_LENGTH)
                {
                    s.delete(MAX_INPUT_LENGTH,s.length());
                }
            }
        });
    }

    @Override
    protected void initSDSlidingButton()
    {
        SDViewUtil.setGone(sb_pop);
    }
}
