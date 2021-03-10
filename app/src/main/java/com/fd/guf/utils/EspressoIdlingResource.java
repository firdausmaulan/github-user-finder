package com.fd.guf.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.fd.guf.BuildConfig;

public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static final CountingIdlingResource mCountingIdlingResource = new CountingIdlingResource(RESOURCE);

    public static void increment() {
        if (BuildConfig.DEBUG) mCountingIdlingResource.increment();
    }

    public static void decrement() {
        if (BuildConfig.DEBUG) {
            new Handler(Looper.myLooper()).postDelayed(() -> {
                try {
                    mCountingIdlingResource.decrement();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }, 1000);
        }
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}