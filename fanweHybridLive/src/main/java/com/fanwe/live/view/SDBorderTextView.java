package com.fanwe.live.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.fanwe.live.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/8/30.
 * 带边框的TextView
 */
public class SDBorderTextView extends AppCompatTextView
{
    public SDBorderTextView(Context context)
    {
        super(context);
        init(null);
    }

    public SDBorderTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public SDBorderTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public static final String TAG = SDBorderTextView.class.getSimpleName();

    private TextPaint mPaintBackup;
    private int mTextColorBackup;

    private int mBorderColor;
    private float mBorderWidth;

    /**
     * 设置边框颜色
     *
     * @param color
     */
    public void setBorderColor(@ColorInt int color)
    {
        mBorderColor = color;
    }

    /**
     * 设置边框宽度大小
     *
     * @param width
     */
    public void setBorderWidth(@Dimension float width)
    {
        mBorderWidth = width;
    }

    public int getBorderColor()
    {
        return mBorderColor;
    }

    public float getBorderWidth()
    {
        return mBorderWidth;
    }

    protected void init(AttributeSet attrs)
    {
        if (attrs == null)
        {
            return;
        }

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SDBorderTextView);
        int color = a.getColor(R.styleable.SDBorderTextView_borderColor, 0);
        int width = a.getDimensionPixelSize(R.styleable.SDBorderTextView_borderWidth, 0);

        a.recycle();

        if (color != 0)
        {
            setBorderColor(color);
        }
        if (width > 0)
        {
            setBorderWidth(width);
        }
    }

    private TextPaint getPaintBackup()
    {
        if (mPaintBackup == null)
        {
            mPaintBackup = new TextPaint();
        }
        return mPaintBackup;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        final long start = System.currentTimeMillis();

        if (mBorderColor != 0 && mBorderWidth > 0)
        {
            getPaintBackup().set(getPaint()); // 备份paint

            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(mBorderWidth);
            boolean needRestoreColor = false;
            if (mBorderColor != getCurrentTextColor())
            {
                setCurrentTextColor(mBorderColor, true);
                needRestoreColor = true;
            }
            super.onDraw(canvas);

            getPaint().set(getPaintBackup()); // 还原paint
            if (needRestoreColor)
            {
                setCurrentTextColor(mTextColorBackup, false);
            }
            super.onDraw(canvas);
        } else
        {
            super.onDraw(canvas);
        }

        Log.i(TAG, "onDraw:" + (System.currentTimeMillis() - start));
    }

    private void setCurrentTextColor(int color, boolean backup)
    {
        try
        {
            Field field = TextView.class.getDeclaredField("mCurTextColor");
            field.setAccessible(true);

            if (backup)
            {
                mTextColorBackup = getCurrentTextColor();
            }

            field.set(this, color);
            field.setAccessible(false);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
