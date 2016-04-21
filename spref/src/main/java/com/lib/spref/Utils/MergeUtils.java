package com.lib.spref.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.sax.Element;
import android.sax.RootElement;
import android.util.Xml;

import com.lib.spref.Utils.Utils;
import com.lib.spref.internal.CustomTextElementListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.lib.spref.internal.SharedPreferencesController.*;

/**
 * @author lpereira on 21/04/2016.
 */
public class MergeUtils {
    private static final String DEFAULT_FILE_ATTR_NAME = "name";
    private static final String DEFAULT_FILE_DEFAULT_ELEMENT = "default";
    private static final String DEFAULT_FILE_STRING_ELEMENT = "string";
    private static final String DEFAULT_FILE_INTEGER_ELEMENT = "integer";
    private static final String DEFAULT_FILE_FLOAT_ELEMENT = "float";

    public static void merge(Context context, MergeType mergeType, int resource, SharedPreferences preferences) {
        if (mergeType == MergeType.ALWAYS || (mergeType == MergeType.IF_EMPTY && isEmpty(preferences))) {
            mergeWithLocalFile(context, resource, preferences);
        }
    }

    private static void mergeWithLocalFile(Context context, int resource, SharedPreferences preferences) {
        InputStream defaultLanguageFileStream = null;
        try {
            defaultLanguageFileStream = context.getResources().openRawResource(resource);

            RootElement rootElement = new RootElement(DEFAULT_FILE_DEFAULT_ELEMENT);
            Element textElement = rootElement.getChild(DEFAULT_FILE_STRING_ELEMENT);
            Element integerElement = rootElement.getChild(DEFAULT_FILE_INTEGER_ELEMENT);
            Element floatElement = rootElement.getChild(DEFAULT_FILE_FLOAT_ELEMENT);

            final Map<String, String> stringsToAdd = new HashMap<>();
            final Map<String, Integer> integerToAdd = new HashMap<>();
            final Map<String, Float> floatToAdd = new HashMap<>();

            textElement.setTextElementListener(new CustomTextElementListener(preferences, DEFAULT_FILE_ATTR_NAME) {
                @Override
                public <T> void addValue(String key, T value) {
                    try {
                        stringsToAdd.put(key, String.valueOf(value));
                    }catch (Exception e) {
                        //Cast exception
                        stringsToAdd.put(key, null);
                    }
                }
            });

            floatElement.setTextElementListener(new CustomTextElementListener(preferences, DEFAULT_FILE_ATTR_NAME) {
                @Override
                public <T> void addValue(String key, T value) {
                    try {
                        floatToAdd.put(key, (Float) value);
                    }catch (Exception e){
                        floatToAdd.put(key, Utils.INVALID_FLOAT_ID);
                    }
                }
            });

            integerElement.setTextElementListener(
                    new CustomTextElementListener(preferences, DEFAULT_FILE_ATTR_NAME) {
                        @Override
                        public <T> void addValue(String key, T value) {
                            try {
                                integerToAdd.put(key, (Integer) value);
                            }catch (Exception e){
                                integerToAdd.put(key, Utils.INVALID_ID);
                            }
                        }
                    });

            Xml.parse(defaultLanguageFileStream, Xml.Encoding.UTF_8, rootElement.getContentHandler());

            if (!stringsToAdd.isEmpty()) {
                save(preferences, stringsToAdd);
            }

            if (!integerToAdd.isEmpty()) {
                saveInt(preferences, integerToAdd);
            }

            if (!floatToAdd.isEmpty()) {
                saveFloat(preferences, floatToAdd);
            }

        } catch (Exception e) {
            // Could not merge default shared preferences with the existing preferences
        } finally {
            try {
                if (defaultLanguageFileStream != null) {
                    defaultLanguageFileStream.close();
                }
            } catch (IOException ignored) {
                //ignored
            }
        }
    }

    /**
     * Checks if the shared preferences is empty
     * @param preferences shared preferences instance
     * @return True if empty; false otherwise
     */
    static boolean isEmpty(SharedPreferences preferences) {
        Map<String, ?> all = preferences.getAll();
        return all == null || all.isEmpty();
    }

    /**
     * Saves several values into the shared preferences
     * @param preferences shared preferences instance
     * @param keysValues the key/values combinations to saveRelationships
     */
    static void save(SharedPreferences preferences, Map<String, String> keysValues) {

        SharedPreferences.Editor editor = preferences.edit();

        for (Map.Entry<String, String> entry : keysValues.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }

        editor.apply();
    }

    /**
     * Saves several values into the shared preferences
     * @param preferences shared preferences instance
     * @param keysValues the key/values combinations to saveRelationships
     */
    static void saveInt(SharedPreferences preferences, Map<String, Integer> keysValues) {

        SharedPreferences.Editor editor = preferences.edit();

        for (Map.Entry<String, Integer> entry : keysValues.entrySet()) {
            editor.putInt(entry.getKey(), entry.getValue());
        }

        editor.apply();
    }

    /**
     * Saves several values into the shared preferences
     * @param preferences shared preferences instance
     * @param keysValues the key/values combinations to saveRelationships
     */
    static void saveFloat(SharedPreferences preferences, Map<String, Float> keysValues) {

        SharedPreferences.Editor editor = preferences.edit();

        for (Map.Entry<String, Float> entry : keysValues.entrySet()) {
            editor.putFloat(entry.getKey(), entry.getValue());
        }

        editor.apply();
    }
}
