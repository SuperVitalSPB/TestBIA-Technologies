package ru.biatech.test.supervital;

import android.app.Application;

import ru.biatech.test.supervital.data.TracksDbHelper;
import ru.biatech.test.supervital.retrofit.RetrofitSingleton;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class ApplicationBIATech extends Application {
    private TracksDbHelper mDbHelper;

    public TracksDbHelper getmDbHelper() {
        return mDbHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitSingleton.initRetro();
        mDbHelper = new TracksDbHelper(this);
    }
}
