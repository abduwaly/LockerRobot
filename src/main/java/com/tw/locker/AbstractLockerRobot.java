package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractLockerRobot {
    protected List<Locker> lockers;

    public abstract Ticket saveBag(Bag bag);

    public Bag takeBag(Ticket ticket) {
        Locker correspondingLocker = lockers.stream().filter(l -> l.getId().equals(ticket.getLockerId())).findFirst().get();
        return correspondingLocker.takeBag(ticket);
    }
}
