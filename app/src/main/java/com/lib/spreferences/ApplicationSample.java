package com.lib.spreferences;

import android.app.Application;

import com.lib.spref.SPref;

/**
 * @author lpereira on 08/01/2016.
 */
public class ApplicationSample extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SPref.init(this); //Initialize the SPref
        //SPref.init(this).provideDefaultResourceFile(R.raw.file); //With this way the SharedPreferences is initialized with a default resource file
    }
}
