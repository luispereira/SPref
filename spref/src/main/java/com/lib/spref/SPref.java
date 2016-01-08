package com.lib.spref;

import android.content.Context;

import com.lib.spref.exceptions.SDKNotInitializedException;

/**
 * @author lpereira on 07/01/2016.
 */
public class SPref {
    private static SPref sInstance;

    private Context mContext;
    private int mResource;

    /**
     * Instance of the Spref lib
     * @return the instance of Spref lib
     */
    public static synchronized  SPref getInstance()  {
        if (sInstance == null) {
            throw new SDKNotInitializedException();
        }
        return sInstance;
    }

    /**
     * Check if SPref was initialized
     * @return if was initialized
     */
    protected static boolean isSPrefInit(){
        return sInstance != null;
    }

    /**
     * Constructor
     * @param context the application context
     */
    private SPref(Context context){
        mContext = context;
        sInstance = this;
    }

    /**
     * Empty constructor
     */
    private SPref(){
    }

    /**
     * The initializer method of the SPref lib
     * @param context the context
     */
    public static SPref init(Context context){
        return new SPref(context);
    }

    /**
     * The application context
     * @return the context
     */
    protected Context getApplicationContext(){
        return mContext;
    }

    /**
     * Builds shared preference in order to access, save and remove  them
     * @return the controller to manage shared preferences
     */
    public static SettingsConnector buildSettings(){
        return new SettingsConnector(getInstance().getApplicationContext(), getInstance().mResource);
    }

    /**
     * Provide a default resource file to merge all managed settings
     * @param resource the resource file
     */
    public SPref provideDefaultResourceFile(int resource){
        mResource = resource;
        return sInstance;
    }
}
