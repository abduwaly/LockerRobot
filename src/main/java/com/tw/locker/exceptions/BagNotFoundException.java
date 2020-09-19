package com.tw.locker.exceptions;

public class BagNotFoundException extends RuntimeException {
    public BagNotFoundException() {
        super("Bag Not Found");
    }
}
