package com.lib.spref.exceptions;

import android.content.Context;

public class SDKNotInitializedException extends RuntimeException {

    /**
     * Triggered when the user did not initialized the SDK with the method {@link com.lib.spref.SPref#init(Context)}
     */
    public SDKNotInitializedException() {
        super("The SPref SDK has not been initialized yet.");
    }
}
