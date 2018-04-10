package com.fanwe.catchdoll.appview;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.catchdoll.model.WWSignInfoModel;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <签到页面>
 * Created by wwb on 2017/12/15 10:07.
 */

public class WWMainSignView extends WWBaseAppView
{
    public WWMainSignView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public WWMainSignView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WWMainSignView(Context context)
    {
        super(context);
        init();
    }

    private WWSignInfoModel model;

    private WWSignView mSignView1;
    private WWSignView mSignView2;
    private WWSignView mSignView3;
    private WWSignView mSignView4;
    private WWSignView mSignView5;
    private WWSignView mSignView6;
    private WWSignView mSignView7;


    private void init()
    {
        setContentView(R.layout.ww_view_main_sign);

        mSignView1 = (WWSignView) findViewById(R.id.sv1);
        mSignView2 = (WWSignView) findViewById(R.id.sv2);
        mSignView3 = (WWSignView) findViewById(R.id.sv3);
        mSignView4 = (WWSignView) findViewById(R.id.sv4);
        mSignView5 = (WWSignView) findViewById(R.id.sv5);
        mSignView6 = (WWSignView) findViewById(R.id.sv6);
        mSignView7 = (WWSignView) findViewById(R.id.sv7);
    }


    public void setSignInfoMode(WWSignInfoModel model)
    {
        this.model = model;
        bindData(model);
    }

    private void bindData(WWSignInfoModel model)
    {
        bindSignView1(model);
        bindSignView2(model);
        bindSignView3(model);
        bindSignView4(model);
        bindSignView5(model);
        bindSignView6(model);
        bindSignView7(model);
    }

    private void bindSignView1(WWSignInfoModel model)
    {
        if (model.getDay1_diamonds() > 0)
        {
            mSignView1.setDiamondsOrScoreNumber(String.valueOf(model.getDay1_diamonds()));
        } else
        {
            mSignView1.setDiamondsOrScoreNumber(String.valueOf(model.getDay1_score()));
        }
        mSignView1.setBgDiamondOrScore(model.getDay1_diamonds() > 0);
        mSignView1.setSignedOrNot(model.getDay1() == 1);
    }


    private void bindSignView2(WWSignInfoModel model)
    {
        if (model.getDay2_diamonds() > 0)
        {
            mSignView2.setDiamondsOrScoreNumber(String.valueOf(model.getDay2_diamonds()));
        } else
        {
            mSignView2.setDiamondsOrScoreNumber(String.valueOf(model.getDay2_score()));
        }
        mSignView2.setBgDiamondOrScore(model.getDay2_diamonds() > 0);
        mSignView2.setSignedOrNot(model.getDay2() == 1);
    }

    private void bindSignView3(WWSignInfoModel model)
    {
        if (model.getDay3_diamonds() > 0)
        {
            mSignView3.setDiamondsOrScoreNumber(String.valueOf(model.getDay3_diamonds()));
        } else
        {
            mSignView3.setDiamondsOrScoreNumber(String.valueOf(model.getDay3_score()));
        }
        mSignView3.setBgDiamondOrScore(model.getDay3_diamonds() > 0);
        mSignView3.setSignedOrNot(model.getDay3() == 1);
    }

    private void bindSignView4(WWSignInfoModel model)
    {
        if (model.getDay4_diamonds() > 0)
        {
            mSignView4.setDiamondsOrScoreNumber(String.valueOf(model.getDay4_diamonds()));
        } else
        {
            mSignView4.setDiamondsOrScoreNumber(String.valueOf(model.getDay4_score()));
        }
        mSignView4.setBgDiamondOrScore(model.getDay4_diamonds() > 0);
        mSignView4.setSignedOrNot(model.getDay4() == 1);
    }

    private void bindSignView5(WWSignInfoModel model)
    {
        if (model.getDay5_diamonds() > 0)
        {
            mSignView5.setDiamondsOrScoreNumber(String.valueOf(model.getDay5_diamonds()));
        } else
        {
            mSignView5.setDiamondsOrScoreNumber(String.valueOf(model.getDay5_score()));
        }
        mSignView5.setBgDiamondOrScore(model.getDay5_diamonds() > 0);
        mSignView5.setSignedOrNot(model.getDay5() == 1);
    }

    private void bindSignView6(WWSignInfoModel model)
    {
        if (model.getDay6_diamonds() > 0)
        {
            mSignView6.setDiamondsOrScoreNumber(String.valueOf(model.getDay6_diamonds()));
        } else
        {
            mSignView6.setDiamondsOrScoreNumber(String.valueOf(model.getDay6_score()));
        }
        mSignView6.setBgDiamondOrScore(model.getDay6_diamonds() > 0);
        mSignView6.setSignedOrNot(model.getDay6() == 1);
    }

    private void bindSignView7(WWSignInfoModel model)
    {
        if (model.getDay7_diamonds() > 0)
        {
            mSignView7.setDiamondsOrScoreNumber(String.valueOf(model.getDay7_diamonds()));
        } else
        {
            mSignView7.setDiamondsOrScoreNumber(String.valueOf(model.getDay7_score()));
        }
        mSignView7.setBgDiamondOrScore(model.getDay7_diamonds() > 0);
        mSignView7.setSignedOrNot(model.getDay7() == 1);
    }

    public void refreshSignStatus(int day)
    {
        List<WWSignView> list = new ArrayList<>();
        list.clear();
        list.add(mSignView1);
        list.add(mSignView2);
        list.add(mSignView3);
        list.add(mSignView4);
        list.add(mSignView5);
        list.add(mSignView6);
        list.add(mSignView7);


        for (int i = 0; i < list.size(); i++)
        {
            if (SDCollectionUtil.isIndexLegal(list, day - 1))
            {
                list.get(day - 1).setSignedOrNot(true);
            }
        }
    }

}
