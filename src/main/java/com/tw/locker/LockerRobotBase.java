package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public abstract class LockerRobotBase {
    protected List<Locker> lockers;

    public abstract Ticket saveBag(Bag bag);

    public abstract Bag takeBag(Ticket ticket);
}
