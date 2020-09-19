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
    private List<PrimaryLockerRobot> primaryLockerRobots;

    public Ticket saveBag(Bag bag) {
        if(bag.getSize() == BagSize.SMALL){
            List<Locker> availableLockers = this.lockers.stream().filter(l-> l.vacancyRate() > 0).collect(Collectors.toList());

            if((long) availableLockers.size() > 0){
                return availableLockers.get(0).saveBag(bag);
            } else {
                throw new NoStorageException();
            }
        }

        if(bag.getSize() == BagSize.MEDIUM){
            List<Locker> availableLockers = this.primaryLockerRobots.get(0).getLockers().stream().filter(l -> l.vacancyRate()>0).collect(Collectors.toList());

            if((long) availableLockers.size() > 0){
                return availableLockers.get(0).saveBag(bag);
            } else {
                throw new NoStorageException();
            }
        }

        throw new RuntimeException();
    }
}
