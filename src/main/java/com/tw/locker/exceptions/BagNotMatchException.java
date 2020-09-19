package com.tw.locker.exceptions;

public class BagNotMatchException extends RuntimeException {
    public BagNotMatchException() {
        super("Bag Not Match");
    }
}
