package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class PrimaryLockerRobot {
    private List<Locker> lockers;

    public Ticket saveBag(Bag bag) {
        Optional<Locker> target = lockers.stream().filter(l -> l.getCapacity() > 0).findFirst();

        if(target.isPresent()) {
            return target.get().saveBag(bag);
        } else {
            throw new NoStorageException();
        }
    }
}
