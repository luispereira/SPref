package com.lib.spref;

import android.content.Context;

import com.lib.spref.Utils.Utils;
import com.lib.spref.exceptions.SDKNotInitializedException;

/**
 * @author lpereira on 07/01/2016.
 */
public class SPref {
    private static SPref sInstance;

    private Context mContext;
    private int mResource = Utils.INVALID_ID;

    /**
     * Instance of the Spref lib
     * @return the instance of Spref lib
     */
    private static synchronized SPref getInstance()  {
        if (sInstance == null) {
            throw new SDKNotInitializedException();
        }
        return sInstance;
    }

    /**
     * Constructor
     * @param context the application context
     */
    private SPref(Context context){
        mContext = context;
    }

    /**
     * Empty constructor
     */
    @SuppressWarnings("unused")
    private SPref(){
    }

    /**
     * The initializer method of the SPref lib
     * @param context application context
     * @return the instance of SPref
     */
    public static SPref init(Context context){
        sInstance = new SPref(context);
        return sInstance;
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
     * Builds shared preference in order to access, save and remove them <br>
     * Without using {@link #init(Context)} and without any provided resource
     * @param context the application context
     * @return the controller to manage shared preferences
     */
    @SuppressWarnings("unused")
    public static SettingsConnector buildSettings(Context context){
        return new SettingsConnector(context, Utils.INVALID_ID);
    }

    /**
     * Provide a default resource file to merge all managed settings
     * @param resource the resource file
     * @return instance of SPref
     */
    @SuppressWarnings("unused")
    public SPref provideDefaultResourceFile(int resource){
        mResource = resource;
        return sInstance;
    }
}
