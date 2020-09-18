package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class SuperLockerRobot {
    private List<Locker> lockers;

    public Ticket saveBag(Bag bag) {

        if (this.lockers.stream().noneMatch(l -> l.vacancyRate() > 0)) {
            throw new NoStorageException();
        }

        Optional<Locker> target = this.lockers.stream().max(Comparator.comparingDouble(Locker::vacancyRate));
        return target.get().saveBag(bag);
    }
}
