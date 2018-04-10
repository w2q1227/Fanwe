package com.fanwe.catchdoll.control;

/**
 * Created by Administrator on 2018/1/5.
 */

public class DirectionHelper
{
    public interface Callback
    {
        void left();

        void right();

        void forward();

        void backwards();
    }

    //    01234 正右后左顶
    public static void translate(ILiveWaWa.DIRECTION direction, int position,Callback callback)
    {
        if (position == 0)
        {
            switch (direction)
            {
                case LEFT:
                    callback.left();
                    break;
                case RIGHT:
                    callback.right();
                    break;
                case UP:
                    callback.forward();
                    break;
                case DOWN:
                    callback.backwards();
                    break;
            }
        } else if (position == 1)
        {
            switch (direction)
            {
                case LEFT:
                    callback.backwards();
                    break;
                case RIGHT:
                    callback.forward();
                    break;
                case UP:
                    callback.left();
                    break;
                case DOWN:
                    callback.right();
                    break;
            }
        } else if (position == 4)
        {
            switch (direction)
            {
                case LEFT:
                    callback.left();
                    break;
                case RIGHT:
                    callback.right();
                    break;
                case UP:
                    callback.forward();
                    break;
                case DOWN:
                    callback.backwards();
                    break;
            }
        }
    }
}
