package ru.shmakova.hackathon;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import timber.log.Timber;

/**
 * Created by shmakova on 23.07.16.
 */

public class App extends Application {

    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
