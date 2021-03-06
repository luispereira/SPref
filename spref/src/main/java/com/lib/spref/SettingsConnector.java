package com.lib.spref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lib.spref.Utils.EncryptionUtils;
import com.lib.spref.Utils.MergeUtils;
import com.lib.spref.Utils.Utils;
import com.lib.spref.internal.EncryptionState;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lpereira on 28/10/2015.
 */
@SuppressWarnings("unused")
public class SettingsConnector {
    private static final String SHARED_PREF_NAME = "sp_settings";
    private static final String SPREF_ENCRYPTION_TAG = "#SPREF_ENCRYPTION_TAG#";

    private final SharedPreferences mPreferences;
    private final byte[] mEncrypt;

    /**
     * Settings controller constructor method
     *
     * @param context         application context
     * @param resource        the default file resource
     * @param preferencesName the name of the shared preferences
     * @param encrypt         if all settings should be encrypted
     * @param shouldOverride  if the user wants to override the existent values with the same value keys found
     * @param mode            mode which the shared preferences should be
     */
    SettingsConnector(Context context, int resource, String preferencesName, byte[] encrypt, boolean shouldOverride, int mode) {
        String value = preferencesName == null || preferencesName.isEmpty() ? SHARED_PREF_NAME : preferencesName;
        mPreferences = context.getSharedPreferences(value, mode == Utils.INVALID_ID ? Context.MODE_PRIVATE : mode);
        mEncrypt = encrypt;
        if (resource != Utils.INVALID_ID) {
            MergeUtils.merge(context, resource, mPreferences, shouldOverride);
        }
    }

    /**
     * Retrieve string setting according to the settingKey
     *
     * @param settingKey key
     * @return setting value
     * @since SDK 0.1.0
     */
    public String getSetting(String settingKey) {
        if (settingKey == null) {
            return null;
        }

        return mPreferences.getString(settingKey, null);
    }

    /**
     * Retrieve string setting according to the settingKey
     *
     * @param settingKey   key
     * @param defaultValue default value
     * @return setting value
     * @since SDK 0.5.5
     */
    public String getSetting(String settingKey, String defaultValue) {
        if (settingKey == null) {
            return null;
        }

        return mPreferences.getString(settingKey, defaultValue);
    }

    /**
     * Retrieve string setting according to the settingKey
     *
     * @param settingKey key
     * @return setting value or null if there was any problem trying to decrypt the value
     * @since SDK 0.4.2
     * @deprecated use {@link #getSafeEncryptedSetting(String)} instead
     */
    @Deprecated
    public String getEncryptedSetting(String settingKey) {
        String value = mPreferences.getString(settingKey, null);
        if (mEncrypt != null && value != null && !value.isEmpty()) {
            byte[] array = Base64.decode(value, Base64.URL_SAFE);
            return EncryptionUtils.decrypt(mEncrypt, array);
        }
        return null;
    }

    /**
     * Retrieve string setting, if is encrypted returned the values as {@link #getEncryptedSetting(String)}, if not, returns only the value as {@link #getSetting(String)}
     *
     * @param settingKey an object containing if the key was successfully decrypted and its value
     * @return setting value or null if there was any problem trying to decrypt the value
     * @since SDK 0.7.0
     */
    public SafeSetting getSafeEncryptedSetting(String settingKey) {
        if (isKeyEncrypted(settingKey)) {
            String value = mPreferences.getString(settingKey, null);
            if (mEncrypt != null && value != null && !value.isEmpty()) {
                byte[] array = Base64.decode(value, Base64.URL_SAFE);

                String decryptedValues = EncryptionUtils.decrypt(mEncrypt, array);
                if(decryptedValues != null){
                     return new SafeSetting(EncryptionState.SUCCESSFULLY_DECRYPTED, decryptedValues);
                }else{
                    return new SafeSetting(EncryptionState.ENCRYPTION_ERROR, null);
                }
            }else{
                return new SafeSetting(EncryptionState.INTERNAL_ERROR, null);
            }
        } else {
            return new SafeSetting(EncryptionState.SUCCESS, getSetting(settingKey));
        }
    }

    /**
     * Checks if a key is encrypted or not
     * @param settingKey key
     * @return if is encrypted or not
     * @since SDK 0.7.0
     */
    public boolean isKeyEncrypted(String settingKey) {
        return settingKey != null && mPreferences.getBoolean(SPREF_ENCRYPTION_TAG + settingKey, false);
    }

    /**
     * Retrieve string setting according to the settingKey
     *
     * @param settingKey key
     * @return setting value (return -1 if not found)
     * @since SDK 0.4.2
     */
    public int getIntSetting(String settingKey) {
        if (settingKey == null) {
            return Utils.INVALID_ID;
        }
        return mPreferences.getInt(settingKey, Utils.INVALID_ID);
    }

    /**
     * Retrieve string setting according to the settingKey
     *
     * @param settingKey key
     * @return setting value (return -1 if not found)
     * @since SDK 0.2.2
     */
    public float getFloatSetting(String settingKey) {
        if (settingKey == null) {
            return Utils.INVALID_FLOAT_ID;
        }
        return mPreferences.getFloat(settingKey, Utils.INVALID_FLOAT_ID);
    }

    /**
     * Retrieve string setting according to the settingKey
     *
     * @param settingKey key
     * @return setting value (return -1 if not found)
     * @since SDK 0.4.1
     */
    public long getLongSetting(String settingKey) {
        if (settingKey == null) {
            return Utils.INVALID_LONG_ID;
        }
        return mPreferences.getLong(settingKey, Utils.INVALID_LONG_ID);
    }

    /**
     * Retrieve string setting according to the settingKey
     *
     * @param settingKey   key
     * @param defaultValue default value
     * @return setting value (return -1 if not found)
     * @since SDK 0.1.0
     */
    public boolean getBooleanSetting(String settingKey, boolean defaultValue) {
        if (settingKey == null) {
            return defaultValue;
        }
        return mPreferences.getBoolean(settingKey, defaultValue);
    }

    /**
     * Retrieve list of settings according to the settingKey
     *
     * @param settingKey key
     * @param <T>        generic type
     * @return setting value (return -1 if not found)
     * @since SDK 0.1.1
     * @deprecated use {@link #getListSetting(String, Class)} instead
      */
    @Deprecated
    public <T> List<T> getListSetting(String settingKey) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
        return gson.fromJson(getSetting(settingKey), listType);
    }

    /**
     * Retrieve list of settings according to the settingKey and converts to a specific set of item
     * This solves the issue of LinkedTreeMap
     *
     * @param settingKey key
     * @param <T>        generic type
     * @return setting value (return -1 if not found)
     * @since SDK 0.7.2
     */
    public <T> List<T> getListSetting(String settingKey, Class clazz) {
        Gson gson = new Gson();
        Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        return gson.fromJson(getSetting(settingKey), listType);
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, String settingValue) {
        mPreferences.edit().putString(settingKey, settingValue).apply();
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.5.4
     */
    public void saveNonNullSetting(String settingKey, String settingValue) {
        if (settingValue != null) {
            saveSetting(settingKey, settingValue);
        }
    }

    /**
     * Encrypts a string and saves it on shared preferences (If there was an error or encryption key was not provided it will save null)
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.4.2
     * @deprecated use {@link #saveSafeEncryptedSetting(String, String)} instead
     */
    @Deprecated
    public void saveEncryptedSetting(String settingKey, String settingValue) {
        if (mEncrypt != null && settingValue != null) {
            byte[] resultValue = EncryptionUtils.encrypt(mEncrypt, settingValue);
            settingValue = Base64.encodeToString(resultValue, Base64.URL_SAFE);
            saveSetting(SPREF_ENCRYPTION_TAG + settingKey, true);
        } else {
            settingValue = null;
            saveSetting(SPREF_ENCRYPTION_TAG + settingKey, false);
        }
        mPreferences.edit().putString(settingKey, settingValue).apply();
    }

    /**
     * Encrypts a string and saves it on shared preferences
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.7.0
     */
    public void saveSafeEncryptedSetting(String settingKey, String settingValue) {
        if (mEncrypt != null && settingValue != null) {
            byte[] resultValue = EncryptionUtils.encrypt(mEncrypt, settingValue);
            settingValue = Base64.encodeToString(resultValue, Base64.URL_SAFE);
            saveSetting(SPREF_ENCRYPTION_TAG + settingKey, true);
        } else {
            saveSetting(SPREF_ENCRYPTION_TAG + settingKey, false);
        }
        mPreferences.edit().putString(settingKey, settingValue).apply();
    }

    /**
     * Save a boolean setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, boolean settingValue) {
        mPreferences.edit().putBoolean(settingKey, settingValue).apply();
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, Integer settingValue) {
        if (settingValue == null) {
            mPreferences.edit().putString(settingKey, null).apply();
        } else {
            mPreferences.edit().putInt(settingKey, settingValue).apply();
        }
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.5.4
     */
    public void saveNonNullSetting(String settingKey, Integer settingValue) {
        if (settingValue != null) {
            saveSetting(settingKey, settingValue);
        }
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.1.0
     */
    public void saveSetting(String settingKey, Long settingValue) {
        if (settingValue == null) {
            mPreferences.edit().putString(settingKey, null).apply();
        } else {
            mPreferences.edit().putLong(settingKey, settingValue).apply();
        }
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.5.4
     */
    public void saveNonNullSetting(String settingKey, Long settingValue) {
        if (settingValue != null) {
            saveSetting(settingKey, settingValue);
        }
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.4.1
     */
    public void saveSetting(String settingKey, Float settingValue) {
        if (settingValue == null) {
            mPreferences.edit().putString(settingKey, null).apply();
        } else {
            mPreferences.edit().putFloat(settingKey, settingValue).apply();
        }
    }

    /**
     * Save a string setting value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue value
     * @since SDK 0.5.4
     */
    public void saveNonNullSetting(String settingKey, Float settingValue) {
        if (settingValue != null) {
            saveSetting(settingKey, settingValue);
        }
    }

    /**
     * Save a list of generic values according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue values (may be null)
     * @param <T>          generic type
     * @since SDK 0.1.1
     */
    public <T> void saveSetting(String settingKey, List<T> settingValue) {
        Gson gson = new Gson();
        saveSetting(settingKey, gson.toJson(settingValue));
    }

    /**
     * Save a set of settings value according to the settingKey
     *
     * @param settingKey   key
     * @param settingValue values (may be null)
     * @since SDK 0.3.0
     */
    public void saveSetting(String settingKey, Set<String> settingValue) {
        mPreferences.edit().putStringSet(settingKey, settingValue).apply();
    }

    /**
     * This removes a setting
     *
     * @param settingKey the setting key
     * @since SDK 0.1.0
     */
    public void removeSetting(String settingKey) {
        if (settingKey != null) {
            mPreferences.edit().remove(settingKey).apply();
           //making sure there is not a encryption setting
            mPreferences.edit().remove(SPREF_ENCRYPTION_TAG + settingKey).apply();
        }
    }

    /**
     * This removes a setting
     *
     * @param settingKey the setting key
     * @since SDK 0.1.0
     */
    public void removeBulkSetting(String... settingKey) {
        if (settingKey != null) {
            for (String aSettingKey : settingKey) {
                removeSetting(aSettingKey);
            }
        }
    }

    /**
     * This will merge an xml file
     *
     * @param file           xml file
     * @param shouldOverride if the user wants to override the existent values with the same value keys found
     * @since SDK 0.4.2
     */
    public void mergeSettings(File file, boolean shouldOverride) {
        if (file != null && file.exists()) {
            MergeUtils.merge(file, mPreferences, shouldOverride);
        }
    }

    /**
     * This removes a setting
     *
     * @since SDK 0.1.0
     */
    public void removeAllSetting() {
        mPreferences.edit().clear().apply();
    }
}
