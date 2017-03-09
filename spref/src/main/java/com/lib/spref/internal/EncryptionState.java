package com.lib.spref.internal;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.lib.spref.internal.EncryptionState.ENCRYPTION_ERROR;
import static com.lib.spref.internal.EncryptionState.INTERNAL_ERROR;
import static com.lib.spref.internal.EncryptionState.SUCCESS;
import static com.lib.spref.internal.EncryptionState.SUCCESSFULLY_DECRYPTED;

/**
 * @author lpereira on 09/03/2017.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({SUCCESS, SUCCESSFULLY_DECRYPTED, ENCRYPTION_ERROR, INTERNAL_ERROR})
public @interface EncryptionState {
    //Success
    int SUCCESS = 0;
    int SUCCESSFULLY_DECRYPTED = 1;
    //Errors
    /**
     * There was a problem trying to decrypt
     */
    int ENCRYPTION_ERROR = 10;
    /**
     * Provably you did not config correctly the encryption feature
     */
    int INTERNAL_ERROR = 11;
}
