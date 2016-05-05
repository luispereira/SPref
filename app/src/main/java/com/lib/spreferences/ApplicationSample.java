package com.lib.spreferences;

import android.app.Application;

import com.lib.spref.SPref;
import com.lib.spref.SettingsConnector;

/**
 * @author lpereira on 08/01/2016.
 */
public class ApplicationSample extends Application {
    private static ApplicationSample sInstance;
    private SettingsConnector mSettingsConnector;

    @Override
    public void onCreate() {
        super.onCreate();
        mSettingsConnector = SPref.init(this)
                .provideDefaultResourceFile(R.raw.default_settings)
                .encrypt(com.lib.spreferences.BuildConfig.PASSWORD_KEY)
                .buildSettings(); //With this way the SharedPreferences is initialized with a default resource file
        sInstance = this;
    }

    public static ApplicationSample getInstance(){
        return sInstance;
    }

    public SettingsConnector getSPref() {
        return mSettingsConnector;
    }
}
