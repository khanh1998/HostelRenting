package org.avengers.capstone.hostelrenting.exception;

/**
 * @author duattt on 10/16/20
 * @created 16/10/2020 - 11:12
 * @project youthhostelapp
 */
public class PreCreationException extends RuntimeException {
    public PreCreationException(String message) {
        super(message);
    }

    public PreCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
