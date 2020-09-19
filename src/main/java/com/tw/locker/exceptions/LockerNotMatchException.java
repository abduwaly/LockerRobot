package com.tw.locker.exceptions;

public class LockerNotMatchException extends RuntimeException {
    public LockerNotMatchException() {
        super("Locker Not Match");
    }
}
