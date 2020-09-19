package com.tw.locker;

import com.tw.locker.enums.BagSize;
import com.tw.locker.exceptions.NoStorageException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class LockerRobotManager {

    private List<Locker> lockers;

    public Ticket saveBag(Bag bag) {
//        if(bag.getSize() == BagSize.SMALL){
//
//        }

        List<Locker> availableLockers = lockers.stream().filter(l-> l.vacancyRate() > 0).collect(Collectors.toList());

        if(availableLockers.stream().count() > 0){
            return availableLockers.get(0).saveBag(bag);
        } else {
            throw new NoStorageException();
        }
    }
}
