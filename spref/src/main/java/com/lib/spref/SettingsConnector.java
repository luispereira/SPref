package com.lib.spref;

import android.content.Context;

import com.lib.spref.internal.AbstractSharedPreferencesController;

import java.util.Set;

/**
 * @author lpereira on 28/10/2015.
 */
@SuppressWarnings("unused")
public class SettingsConnector extends AbstractSharedPreferencesController {
    private static final String SHARED_PREF_NAME = "sp_settings";

    /**
     * Settings controller constructor method
     * @param context application context
     * @param resource the default file resource
     */
    protected SettingsConnector(Context context, int resource) {
        super(context, SHARED_PREF_NAME, MergeType.ALWAYS, resource);
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
     * Save a string setting value according to the settingKey
     * @param settingKey key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, String settingValue){
        save(settingKey, settingValue);
    }

    /**
     * Save a string setting value according to the settingKey
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
     * Save a string setting value according to the settingKey
     * @param settingKey key
     * @param settingValue a list of values
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, Set<String> settingValue){
        if(settingValue != null) {
            save(settingKey, settingValue);
        }
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
