package com.fanwe.catchdoll.control;

import io.socket.emitter.Emitter;

/**
 * Created by Administrator on 2017/12/1.
 */

public abstract class EmitterListenerWrapper implements Emitter.Listener
{
    private String event;

    public EmitterListenerWrapper(String event)
    {
        this.event = event;
    }

    @Override
    public void call(Object... objects)
    {
        call(event,objects);
    }

    protected abstract void call(String event,Object... objects);
}
