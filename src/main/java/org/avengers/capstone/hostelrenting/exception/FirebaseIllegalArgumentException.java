package org.avengers.capstone.hostelrenting.exception;

import com.google.firebase.auth.FirebaseAuthException;

/**
 * @author duattt on 11/4/20
 * @created 04/11/2020 - 10:37
 * @project youthhostelapp
 */
public class FirebaseIllegalArgumentException extends IllegalArgumentException {
    public FirebaseIllegalArgumentException() {
    }

    public FirebaseIllegalArgumentException(String s) {
        super(s);
    }

    public FirebaseIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FirebaseIllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
