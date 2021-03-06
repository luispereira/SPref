package com.lib.spref;

import android.content.Context;

import com.lib.spref.Utils.EncryptionUtils;
import com.lib.spref.Utils.Utils;

import java.lang.ref.WeakReference;

/**
 * @author lpereira on 07/01/2016.
 */
public class SPref {
    private static WeakReference<SPref> sInstance;
    private static byte[] mEncryptSeed = null;

    private String mPreferencesName;
    private Context mContext;
    private int mResource = Utils.INVALID_ID;
    private int mMode = Utils.INVALID_ID;
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
        sInstance = new WeakReference<>(new SPref(context));
        return sInstance.get();
    }

    /**
     * The initializer method of the SPref lib
     * @param preferencesName application context
     * @return the instance of SPref
     */
    public SPref name(String preferencesName){
        mPreferencesName = preferencesName;
        return sInstance.get();
    }

    /**
     * The application context
     * @return the context
     */
    private Context getApplicationContext(){
        return mContext;
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
        return sInstance.get();
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
        return sInstance.get();
    }


    /**
     * Encrypt configurations providing a key this key should have at least 128bits
     * Remember that if you change this key the values that were written before are no longer accessible
     * @param key the key in byte[] with at least 128bits
     * @return the SPref instance
     */
    @SuppressWarnings("unused")
    public SPref encrypt(byte[] key){
        mEncryptSeed = key;
        return sInstance.get();
    }

    /**
     * Encrypt configurations providing a key
     * Remember that if you change this key the values that were written before are no longer accessible
     * @param key the key hexadecimal to encrypt
     * @return the SPref instance
     */
    @SuppressWarnings("unused")
    public SPref encrypt(String key){
        mEncryptSeed = EncryptionUtils.generateKey(key);
        return sInstance.get();
    }

    /**
     * Change the mode of the SPref (MODE_PRIVATE by default)
     * The modes can be:
     * - {@link Context#MODE_PRIVATE}  //This is set by default
     * - {@link Context#MODE_APPEND}
     * - {@link Context#MODE_MULTI_PROCESS}
     * - {@link Context#MODE_WORLD_READABLE}
     * - {@link Context#MODE_WORLD_WRITEABLE}
     *
     * @param mode mode of the shared preferences
     * @return SPref instance
     */
    @SuppressWarnings("unused")
    public SPref mode(int mode){
        mMode = mode;
        return sInstance.get();
    }

    /**
     * Builds shared preference in order to access, save and remove  them
     * @return the controller to manage shared preferences
     */
    public SettingsConnector buildSettings(){
        return new SettingsConnector(getApplicationContext(), mResource, mPreferencesName, mEncryptSeed, mShouldOverride, mMode);
    }

    /**
     * Builds shared preference in order to access, save and remove them <br>
     * Without using {@link #init(Context)}, {@link #name(String)} and {@link #provideDefaultResourceFile(int, boolean)}
     * @param context the application context
     * @return the controller to manage shared preferences
     */
    @SuppressWarnings("unused")
    public static SettingsConnector buildSettings(Context context){
        return new SettingsConnector(context, Utils.INVALID_ID, null, mEncryptSeed, false, Utils.INVALID_ID);
    }
}
