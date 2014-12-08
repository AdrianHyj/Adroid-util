package com.universal.tools;

import android.content.Context;

public abstract class Command {

    protected Context context;
    
    public Command(Context context) {
        this.context = context;
    }
    
    public abstract void execute();
}
