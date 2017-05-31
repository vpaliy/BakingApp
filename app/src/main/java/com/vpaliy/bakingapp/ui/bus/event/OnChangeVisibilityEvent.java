package com.vpaliy.bakingapp.ui.bus.event;


public class OnChangeVisibilityEvent {

    public final boolean visible;

    private OnChangeVisibilityEvent(boolean visible){
        this.visible=visible;
    }

    public static OnChangeVisibilityEvent change(boolean visible){
        return new OnChangeVisibilityEvent(visible);
    }
}
