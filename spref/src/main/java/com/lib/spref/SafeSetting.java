package com.lib.spref;

import android.support.annotation.Nullable;
import com.lib.spref.internal.EncryptionState;

/**
 * @author lpereira on 09/03/2017.
 */

class SafeSetting {
    @EncryptionState
    private int wasSuccessful;
    private String value;

    SafeSetting(int wasSuccessful, String value) {
        this.wasSuccessful = wasSuccessful;
        this.value = value;
    }

    /**
     * Get the encrypted value, this might give a null if an error occurs
     * @return value of the encrypted/non-encrypted key
     */
    public @Nullable String getValue() {
        return value;
    }

    public @EncryptionState
    int getWasSuccessful() {
        return wasSuccessful;
    }
}
