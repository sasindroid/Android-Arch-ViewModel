package com.sasi.acviewmodel.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by sasikumar on 11/01/2018.
 */

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.create();
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((MyApplication)context.getApplicationContext()).applicationComponent;
    }
}
