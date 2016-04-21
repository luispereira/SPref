package com.lib.spref.internal;

import android.content.SharedPreferences;
import android.sax.TextElementListener;

import org.xml.sax.Attributes;

/**
 * @author lpereira on 21/04/2016.
 */
public class CustomTextElementListener implements TextElementListener {
    private final String defaultFileAttr;
    private final SharedPreferences preferences;
    private String key;
    private String value;

    /**
     * Custom Listener
     * @param preferences preferences
     * @param defaultFileAttr default attr
     */
    public CustomTextElementListener(SharedPreferences preferences, String defaultFileAttr) {
        this.preferences = preferences;
        this.defaultFileAttr = defaultFileAttr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void end(String body) {
        if (!preferences.contains(key)) {
            value = body;
            addValue(key, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Attributes attributes) {
        key = attributes.getValue(defaultFileAttr);
    }

    /**
     * When the value and key are fetched from xml this method will be triggered
     * @param key key
     * @param value value
     * @param <T> generic value (can be string, float or integer)
     */
    public <T> void addValue(String key, T value) {
        //To be implemented by classes that instantiate this
    }
}
