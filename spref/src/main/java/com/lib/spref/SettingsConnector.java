package com.lib.spref;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lib.spref.internal.SharedPreferencesController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lpereira on 28/10/2015.
 */
@SuppressWarnings("unused")
public class SettingsConnector extends SharedPreferencesController {
    private static final String SHARED_PREF_NAME = "sp_settings";

    /**
     * Settings controller constructor method
     * @param context application context
     * @param resource the default file resource
     * @param preferencesName the name of the shared preferences
     */
    protected SettingsConnector(Context context, int resource, String preferencesName) {
        super(context, TextUtils.isEmpty(preferencesName) ? SHARED_PREF_NAME : preferencesName, MergeType.ALWAYS, resource);
    }

    /**
     * Retrieve string setting according to the settingKey
     * @param settingKey key
     * @return setting value
     * @since SDK 0.1.0
     */
    public String getSetting(String settingKey){
        return get(settingKey);
    }

    /**
     * Retrieve string setting according to the settingKey
     * @param settingKey key
     * @return setting value (return -1 if not found)
     * @since SDK 0.1.0
     */
    public int getIntSetting(String settingKey){
        return getInt(settingKey);
    }

    /**
     * Retrieve string setting according to the settingKey
     * @param settingKey key
     * @return setting value (return -1 if not found)
     * @since SDK 0.2.2
     */
    public float getFloatSetting(String settingKey){
        return getFloat(settingKey);
    }

    /**
     * Retrieve string setting according to the settingKey
     * @param settingKey key
     * @param defaultValue default value
     * @return setting value (return -1 if not found)
     * @since SDK 0.1.0
     */
    public boolean getBooleanSetting(String settingKey, boolean defaultValue){
        return getBoolean(settingKey, defaultValue);
    }

    /**
     * Retrieve list of settings according to the settingKey
     * @param settingKey key
     * @param <T> generic type
     * @return setting value (return -1 if not found)
     * @since SDK 0.1.1
     */
    public <T> List<T> getListSetting(String settingKey){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
        return gson.fromJson(getSetting(settingKey), listType);
    }

    /**
     * Save a string setting value according to the settingKey
     * @param settingKey key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, String settingValue){
        save(settingKey, settingValue);
    }

    /**
     * Save a boolean setting value according to the settingKey
     * @param settingKey key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, boolean settingValue){
        save(settingKey, settingValue);
    }

    /**
     * Save a string setting value according to the settingKey
     * @param settingKey key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, Integer settingValue){
        if(settingValue != null) {
            save(settingKey, settingValue);
        }
    }

    /**
     * Save a string setting value according to the settingKey
     * @param settingKey key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, Long settingValue){
        if(settingValue != null) {
            save(settingKey, settingValue);
        }
    }

    /**
     * Save a list of generic values according to the settingKey
     * @param settingKey key
     * @param settingValue values (may be null)
     * @param <T> generic type
     * @since SDK 0.1.1
     */
    public <T> void saveSetting(String settingKey, List<T> settingValue){
        Gson gson = new Gson();
        save(settingKey, gson.toJson(settingValue));
    }

    /**
     * Save a set of settings value according to the settingKey
     * @param settingKey key
     * @param settingValue values (may be null)
     * @since SDK 0.3.0
     */
    public void saveSetting(String settingKey, Set<String> settingValue){
        save(settingKey, settingValue);
    }

    /**
     * This removes a setting
     * @param settingKey the setting key
     * @since SDK 0.1.0
     */
    public void removeSetting(String settingKey){
        if(settingKey != null) {
            remove(settingKey);
        }
    }

    /**
     * This removes a setting
     * @param settingKey the setting key
     * @since SDK 0.1.0
     */
    public void removeBulkSetting(String... settingKey){
        if(settingKey != null) {
            for (String aSettingKey : settingKey) {
                remove(aSettingKey);
            }
        }
    }

    /**
     * This removes a setting
     * @since SDK 0.1.0
     */
    public void removeAllSetting(){
       clear();
    }
}
