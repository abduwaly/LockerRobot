package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PrimaryLockerRobot {
    private List<Locker> lockers;

    public Ticket saveBag(Bag bag) {
        return lockers.stream().filter(l -> l.getCapacity() > 0).findFirst().get().saveBag(bag);
    }
}
