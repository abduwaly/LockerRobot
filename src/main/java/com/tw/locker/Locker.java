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

    public Ticker saveBag(Bag bag) {
        return new Ticker(UUID.randomUUID(), bag.getId(), this.id);
    }
}
