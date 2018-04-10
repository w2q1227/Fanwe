package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * <每日签到>
 * Created by wwb on 2017/12/15 09:22.
 */

public class WWSignView extends WWBaseAppView
{
    public WWSignView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWSignView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWSignView(Context context)
    {
        super(context);
        init();
    }

    private ImageView iv_bg;
    private TextView tv_diamonds;
    private ImageView iv_sign;


    private void init()
    {
        setContentView(R.layout.ww_view_sign);

        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        tv_diamonds = (TextView) findViewById(R.id.tv_diamonds);
        iv_sign = (ImageView) findViewById(R.id.iv_sign);
    }


    /**
     * 设置是否签到
     */
    public void setSignedOrNot(boolean isSigned)
    {
        if (isSigned)
        {
            iv_bg.setAlpha(0.3f);
            tv_diamonds.setAlpha(0.3f);
            SDViewUtil.setVisible(iv_sign);
        } else
        {
            iv_bg.setAlpha(1f);
            tv_diamonds.setAlpha(1f);
            SDViewUtil.setGone(iv_sign);
        }
    }

    /**
     * 设置钻石数量或者积分数量
     */
    public void setDiamondsOrScoreNumber(String number)
    {
        SDViewBinder.setTextView(tv_diamonds, number);
    }


    public void setBgDiamondOrScore(boolean isDiamond)
    {
        if (isDiamond)
        {
            iv_bg.setImageResource(R.drawable.ww_ic_sign_bg_diamond);
        } else
        {
            iv_bg.setImageResource(R.drawable.ww_ic_sign_bg_score);
        }
    }

}
