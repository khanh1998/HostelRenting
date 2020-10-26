package org.avengers.capstone.hostelrenting.exception;

/**
 * @author duattt on 10/11/20
 * @created 11/10/2020 - 11:06
 * @project youthhostelapp
 */
public class FileStorageException extends RuntimeException{
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
