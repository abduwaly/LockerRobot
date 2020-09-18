package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Locker {

    private String id;
    private LockerType type;
    private int capacity;

    public Ticker saveBag(Bag bag) {
        if(this.capacity <= 0){
            throw new NoStorageException();
        }
        return new Ticker(UUID.randomUUID(), bag.getId(), this.id);
    }
}
