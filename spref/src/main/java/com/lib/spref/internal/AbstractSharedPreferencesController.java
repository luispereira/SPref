package com.lib.spref.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.sax.Element;
import android.sax.RootElement;
import android.sax.TextElementListener;
import android.util.Xml;

import com.lib.spref.Utils.Utils;

import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation holding the common methods used in shared preferences
 * @author Luis Pereira
 */
public abstract class AbstractSharedPreferencesController {
	public enum MergeType {
		ALWAYS, IF_EMPTY
	}

	private static final String DEFAULT_FILE_ATTR_NAME = "name";
	private static final String DEFAULT_FILE_ROOT_ELEMENT = "root";
	private static final String DEFAULT_FILE_TEXT_ELEMENT = "value";
	private static final String DEFAULT_FILE_INTEGER_ELEMENT = "integer";
	private static final String DEFAULT_FILE_FLOAT_ELEMENT = "float";

	private final Context mContext;
	private final SharedPreferences mPreferences;

	/**
	 * Constructor for this abstract implementation
	 * @param context the application context
	 * @param name the name of the shared preferences
	 * @param mergeType Indicates when the local file must be merged
	 * @param resource Indicates the resource file to merge
	 */
	protected AbstractSharedPreferencesController(Context context, String name, MergeType mergeType, int resource) {
		mContext = context;
		mPreferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
		if(resource != Utils.INVALID_ID) {
			merge(mergeType, resource);
		}
	}

	private void merge(MergeType mergeType, int resource) {
		if (mergeType == MergeType.ALWAYS || (mergeType == MergeType.IF_EMPTY && isEmpty())) {
			mergeWithLocalFile(resource);
		}
	}

	private void mergeWithLocalFile(int resource) {
		InputStream defaultLanguageFileStream = null;
		try {
			defaultLanguageFileStream = mContext.getResources().openRawResource(resource);

			RootElement rootElement = new RootElement(DEFAULT_FILE_ROOT_ELEMENT);
			Element textElement = rootElement.getChild(DEFAULT_FILE_TEXT_ELEMENT);
			Element integerElement = rootElement.getChild(DEFAULT_FILE_INTEGER_ELEMENT);
			Element floatElement = rootElement.getChild(DEFAULT_FILE_FLOAT_ELEMENT);

			final Map<String, String> textsToAdd = new HashMap<>();
			final Map<String, Integer> integerToAdd = new HashMap<>();
			final Map<String, Float> floatToAdd = new HashMap<>();

			textElement.setTextElementListener(new TextElementListener() {

				private String key;
				private String value;

				@Override
				public void start(Attributes attributes) {
					key = attributes.getValue(DEFAULT_FILE_ATTR_NAME);
				}

				@Override
				public void end(String body) {
					value = body;

					if (!mPreferences.contains(key)) {
						textsToAdd.put(key, value);
					}
				}
			});

			floatElement.setTextElementListener(new TextElementListener() {

				private String key;
				private float value;

				@Override
				public void start(Attributes attributes) {
					key = attributes.getValue(DEFAULT_FILE_ATTR_NAME);
				}

				@Override
				public void end(String body) {
					try {
						value = Float.valueOf(body);

						if (!mPreferences.contains(key)) {
							floatToAdd.put(key, value);
						}
					}catch (Exception e){
						floatToAdd.put(key, Utils.INVALID_FLOAT_ID);
					}
				}
			});

			integerElement.setTextElementListener(new TextElementListener() {

				private String key;
				private int value;

				@Override
				public void start(Attributes attributes) {
					key = attributes.getValue(DEFAULT_FILE_ATTR_NAME);
				}

				@Override
				public void end(String body) {
					try {
						value = Integer.valueOf(body);

						if (!mPreferences.contains(key)) {
							integerToAdd.put(key, value);
						}
					}catch (Exception e){
						integerToAdd.put(key, Utils.INVALID_ID);
					}
				}
			});

			Xml.parse(defaultLanguageFileStream, Xml.Encoding.UTF_8, rootElement.getContentHandler());

			if (!textsToAdd.isEmpty()) {
				save(textsToAdd);
			}

			if (!integerToAdd.isEmpty()) {
				saveInt(integerToAdd);
			}

			if (!floatToAdd.isEmpty()) {
				saveFloat(floatToAdd);
			}

		} catch (Exception e) {
			// L.e(getClass(), "Could not merge file with existing preferences", e);

		} finally {
			try {
				if (defaultLanguageFileStream != null) {
					defaultLanguageFileStream.close();
				}
			} catch (IOException ignored) {
			}
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
	 * Saves several values into the shared preferences
	 *
	 * @param keysValues the key/values combinations to saveRelationships
	 */
	void save(Map<String, String> keysValues) {

		SharedPreferences.Editor editor = mPreferences.edit();

		for (Map.Entry<String, String> entry : keysValues.entrySet()) {
			editor.putString(entry.getKey(), entry.getValue());
		}

		editor.apply();
	}

	/**
	 * Saves several values into the shared preferences
	 *
	 * @param keysValues the key/values combinations to saveRelationships
	 */
	void saveInt(Map<String, Integer> keysValues) {

		SharedPreferences.Editor editor = mPreferences.edit();

		for (Map.Entry<String, Integer> entry : keysValues.entrySet()) {
			editor.putInt(entry.getKey(), entry.getValue());
		}

		editor.apply();
	}

	/**
	 * Saves several values into the shared preferences
	 *
	 * @param keysValues the key/values combinations to saveRelationships
	 */
	void saveFloat(Map<String, Float> keysValues) {

		SharedPreferences.Editor editor = mPreferences.edit();

		for (Map.Entry<String, Float> entry : keysValues.entrySet()) {
			editor.putFloat(entry.getKey(), entry.getValue());
		}

		editor.apply();
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
	 * Checks if the shared preferences is empty
	 *
	 * @return True if empty; false otherwise
	 */
	protected boolean isEmpty() {
		Map<String, ?> all = mPreferences.getAll();
		return all == null || all.isEmpty();
	}

	/**
	 * Removes a setting
	 * @param key the key setting
     */
	protected void remove(String key) {
		mPreferences.edit().remove(key).apply();
	}
}
