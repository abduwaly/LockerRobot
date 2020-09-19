package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class LockerRobotManager {

    private List<Locker> lockers;

    public Ticket saveBag(Bag bag) {
        return lockers.stream().findFirst().get().saveBag(bag);
    }
}
