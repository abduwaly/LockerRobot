package com.tw.locker;

public class NoStorageException extends RuntimeException {

    public NoStorageException() {
        super("NO_STORAGE");
    }
}
