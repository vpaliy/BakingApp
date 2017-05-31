package com.vpaliy.bakingapp.ui.bus.event;


public class OnChangeToolbarEvent {

    public final String text;

    private OnChangeToolbarEvent(String text){
        this.text=text;
    }


    public static OnChangeToolbarEvent change(String text){
        return new OnChangeToolbarEvent(text);
    }

}
