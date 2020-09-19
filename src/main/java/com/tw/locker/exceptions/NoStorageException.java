package com.tw.locker.exceptions;

public class NoStorageException extends RuntimeException {

    public NoStorageException() {
        super("NO_STORAGE");
    }
}
