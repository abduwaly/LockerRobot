package com.tw.locker;

public class BagNotFoundException extends RuntimeException{
    public BagNotFoundException() {
        super("Bag Not Found");
    }
}
