package com.lib.spref.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.lib.spref.Utils.EncryptionUtils;
import com.lib.spref.Utils.MergeUtils;
import com.lib.spref.Utils.Utils;

import java.util.Set;

/**
 * Abstract implementation holding the common methods used in shared preferences
 * @author Luis Pereira
 */
public abstract class SharedPreferencesController {
	private final SharedPreferences mPreferences;
	private final byte[] mEncrypt;

	/**
	 * Constructor for this abstract implementation
	 * @param context the application context
	 * @param name the name of the shared preferences
	 * @param resource Indicates the resource file to merge
	 * @param encrypt if all settings should be encrypted
	 * @param shouldOverride if the user wants to override the existent values with the same value keys found
	 */
	protected SharedPreferencesController(Context context, String name, int resource, byte[] encrypt, boolean shouldOverride) {
		mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		mEncrypt = encrypt;
		if(resource != Utils.INVALID_ID) {
			MergeUtils.merge(context, resource, mPreferences, shouldOverride);
		}
	}

	/**
	 * Returns the value for the given key
	 *
	 * @param key the key
	 * @return A string value; null if does not exists
	 */
	protected final String get(String key) {
		if (key == null) {
			return null;
		}

		return mPreferences.getString(key, null);
	}

	/**
	 * Returns a decrypted string
	 * @param key key
	 * @return decrypted string
     */
	protected String getEncryptedString(String key) {
		String value = mPreferences.getString(key, null);
		if(mEncrypt != null && !TextUtils.isEmpty(value)) {
			byte[] array = Base64.decode(value, Base64.NO_WRAP);
			return EncryptionUtils.decrypt(mEncrypt, array);
		}
		return null;
	}

	/**
	 * Returns the value for the given key
	 *
	 * @param key the key
	 * @return A string value; null if does not exists
	 */
	protected final int getInt(String key) {
		if (key == null) {
			return Utils.INVALID_ID;
		}
		return mPreferences.getInt(key, Utils.INVALID_ID);
	}

	/**
	 * Returns the value for the given key
	 *
	 * @param key the key
	 * @return A string value; null if does not exists
	 */
	protected final float getFloat(String key) {
		if (key == null) {
			return Utils.INVALID_FLOAT_ID;
		}
		return mPreferences.getFloat(key, Utils.INVALID_FLOAT_ID);
	}

	/**
	 * Returns the value for the given key
	 *
	 * @param key the key
	 * @return A string value; null if does not exists
	 */
	protected final long getLong(String key) {
		if (key == null) {
			return Utils.INVALID_LONG_ID;
		}
		return mPreferences.getLong(key, Utils.INVALID_LONG_ID);
	}

	/**
	 * Returns the value for the given key
	 *
	 * @param key the key
	 * @param defaultValue the default value in case of error or not found
	 * @return A string value; null if does not exists
	 */
	protected final boolean getBoolean(String key, boolean defaultValue) {
		if (key == null) {
			return defaultValue;
		}
		return mPreferences.getBoolean(key, defaultValue);
	}

	/**
	 * Clears all saved preferences
	 */
	protected final void clear() {
		mPreferences.edit().clear().apply();
	}

	/**
	 * Saves a single key/value pair
	 *
	 * @param key the key
	 * @param value the value
	 */
	protected void save(String key, String value) {
		mPreferences.edit().putString(key, value).apply();
	}

	/**
	 * Saves a single key/value pair
	 *
	 * @param key the key
	 * @param value the value
	 */
	protected void saveEncryption(String key, String value) {
		if(mEncrypt != null && value != null){
			byte[] resultValue = EncryptionUtils.encrypt(mEncrypt, value);
			value = Base64.encodeToString(resultValue, Base64.NO_WRAP);
		}else{
			value = null;
		}
		mPreferences.edit().putString(key, value).apply();
	}

	/**
	 * Saves a single key/value pair
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	protected void save(String key, Integer value) {
		if(value == null){
			mPreferences.edit().putString(key, null).apply();
		}else {
			mPreferences.edit().putInt(key, value).apply();
		}
	}

	/**
	 * Saves a single key/value pair
	 *
	 * @param key the key
	 * @param value the value
	 */
	protected void save(String key, Float value) {
		if(value == null){
			mPreferences.edit().putString(key, null).apply();
		}else {
			mPreferences.edit().putFloat(key, value).apply();
		}
	}

	/**
	 * Saves a single key/value pair
	 *
	 * @param key the key
	 * @param value the value
	 */
	protected void save(String key, Long value) {
		if(value == null){
			mPreferences.edit().putString(key, null).apply();
		}else {
			mPreferences.edit().putLong(key, value).apply();
		}
	}

	/**
	 * Saves a single key/value pair
	 *
	 * @param key the key
	 * @param value the value
	 */
	protected void save(String key, boolean value) {
		mPreferences.edit().putBoolean(key, value).apply();
	}

	/**
	 * Saves a key with multiple values
	 *
	 * @param key the key
	 * @param value the values
	 */
	protected void save(String key, Set<String> value) {
		mPreferences.edit().putStringSet(key, value).apply();
	}

	/**
	 * Removes a setting
	 * @param key the key setting
	 */
	protected void remove(String key) {
		mPreferences.edit().remove(key).apply();
	}

	protected SharedPreferences getPreferences() {
		return mPreferences;
	}
}
