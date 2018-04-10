package com.fanwe.catchdoll.util;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;

/**
 * Created by Administrator on 2017/12/12.
 */

public class PressedStateListDrawable extends StateListDrawable
{
    public static PressedStateListDrawable get(@DrawableRes int normal){
        PressedStateListDrawable stateListDrawable = new PressedStateListDrawable(normal, R.color.state_press_cover);
        return stateListDrawable;
    }

    private int mSelectionColor;

    public PressedStateListDrawable(@DrawableRes int res,@ColorRes int selectionColor) {
        super();
        this.mSelectionColor = SDResourcesUtil.getColor(selectionColor);
        Drawable drawable = SDResourcesUtil.getDrawable(res);
        addState(new int[]{android.R.attr.state_pressed}, drawable);
        addState(new int[]{}, drawable);
    }

    @Override
    protected boolean onStateChange(int[] states) {
        boolean isStatePressedInArray = false;
        for (int state : states) {
            if (state == android.R.attr.state_pressed) {
                isStatePressedInArray = true;
            }
        }
        if (isStatePressedInArray) {
            super.setColorFilter(mSelectionColor, PorterDuff.Mode.SRC_ATOP);
        } else {
            super.clearColorFilter();
        }
        return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
        return true;
    }
}
