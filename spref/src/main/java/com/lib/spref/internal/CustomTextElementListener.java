package com.lib.spref.internal;

import android.content.SharedPreferences;
import android.sax.TextElementListener;

import com.lib.spref.Utils.MergeUtils;

import org.xml.sax.Attributes;

/**
 * @author lpereira on 21/04/2016.
 */
public class CustomTextElementListener implements TextElementListener {
    private final SharedPreferences preferences;
    private final boolean shouldOverride;
    private String key;

    /**
     * Custom Listener
     * @param preferences preferences
     * @param shouldOverride if should override the values of the preferences
     */
    public CustomTextElementListener(SharedPreferences preferences, boolean shouldOverride) {
        this.preferences = preferences;
        this.shouldOverride = shouldOverride;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void end(String body) {
        if (shouldOverride || !preferences.contains(key)) {
            addValue(key, body);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Attributes attributes) {
        key = attributes.getValue(MergeUtils.DEFAULT_FILE_ATTR_NAME);
    }

    /**
     * When the value and key are fetched from xml this method will be triggered
     * @param key key
     * @param value value
     */
    public void addValue(String key, String value) {
        //To be implemented by classes that instantiate this
    }
}
