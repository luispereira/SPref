package com.lib.spref;

import android.content.Context;

import com.lib.spref.Utils.EncryptionUtils;
import com.lib.spref.Utils.Utils;

/**
 * @author lpereira on 07/01/2016.
 */
public class SPref {
    private static SPref sInstance;
    private static byte[] mEncryptSeed = null;

    private String mPreferencesName;
    private Context mContext;
    private int mResource = Utils.INVALID_ID;
    private boolean mShouldOverride;

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
     * The initializer method of the SPref lib
     * @param preferencesName application context
     * @return the instance of SPref
     */
    public SPref name(String preferencesName){
        mPreferencesName = preferencesName;
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
    public SettingsConnector buildSettings(){
        return new SettingsConnector(getApplicationContext(), mResource, mPreferencesName, mEncryptSeed, mShouldOverride);
    }

    /**
     * Provide a default resource file to merge all managed settings
     * @param resource the resource file
     * @return instance of SPref
     * @deprecated use {@link #provideDefaultResourceFile(int, boolean)} instead, this method not not allow to override preferences values
     */
    @SuppressWarnings("unused")
    @Deprecated
    public SPref provideDefaultResourceFile(int resource){
        mResource = resource;
        mShouldOverride = false;
        return sInstance;
    }

    /**
     * Provide a default resource file to merge all managed settings
     * @param resource the resource file
     * @param shouldOverride if every field found should override the preferences already written
     * @return instance of SPref
     */
    @SuppressWarnings("unused")
    public SPref provideDefaultResourceFile(int resource, boolean shouldOverride){
        mResource = resource;
        mShouldOverride = shouldOverride;
        return sInstance;
    }


    /**
     * Encrypt configurations providing a key this key should have at least 128bits
     * Remember that if you change this key the values that were written before are no longer accessible
     * @return key
     */
    @SuppressWarnings("unused")
    public SPref encrypt(byte[] key){
        mEncryptSeed = key;
        return sInstance;
    }

    /**
     * Encrypt configurations providing a key
     * Remember that if you change this key the values that were written before are no longer accessible
     * @return key to encrypt
     */
    @SuppressWarnings("unused")
    public SPref encrypt(String key){
        mEncryptSeed = EncryptionUtils.generateKey(key);
        return sInstance;
    }

    /**
     * Builds shared preference in order to access, save and remove them <br>
     * Without using {@link #init(Context)}, {@link #name(String)} and {@link #provideDefaultResourceFile(int, boolean)}
     * @param context the application context
     * @return the controller to manage shared preferences
     */
    @SuppressWarnings("unused")
    public static SettingsConnector buildSettings(Context context){
        return new SettingsConnector(context, Utils.INVALID_ID, null, mEncryptSeed, false);
    }
}
