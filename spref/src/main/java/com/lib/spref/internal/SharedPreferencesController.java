package com.lib.spref.internal;

import android.content.Context;
import android.content.SharedPreferences;

import com.lib.spref.Utils.MergeUtils;
import com.lib.spref.Utils.Utils;

import java.util.Set;

/**
 * Abstract implementation holding the common methods used in shared preferences
 * @author Luis Pereira
 */
public abstract class SharedPreferencesController {
	private final SharedPreferences mPreferences;

	/**
	 * Constructor for this abstract implementation
	 * @param context the application context
	 * @param name the name of the shared preferences
	 * @param resource Indicates the resource file to merge
	 */
	protected SharedPreferencesController(Context context, String name, int resource) {
		mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		if(resource != Utils.INVALID_ID) {
			MergeUtils.merge(context, resource, mPreferences);
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
			return Utils.INVALID_ID;
		}
		return mPreferences.getLong(key, Utils.INVALID_ID);
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
}
