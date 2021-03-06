package com.tw.locker;

import com.tw.locker.enums.BagSize;
import com.tw.locker.enums.LockerType;
import com.tw.locker.exceptions.BagNotMatchException;
import com.tw.locker.exceptions.LockerNotMatchException;
import com.tw.locker.exceptions.NoStorageException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class PrimaryAbstractLockerRobot extends AbstractLockerRobot {
    public PrimaryAbstractLockerRobot(List<Locker> lockers) {
        if (lockers.stream().allMatch(locker -> locker.getType() == LockerType.M)) {
            this.lockers = lockers;
        } else {
            this.lockers = null;
            throw new LockerNotMatchException();
        }
    }

    @Override
    public Ticket saveBag(Bag bag) {
        if (bag.getSize() != BagSize.MEDIUM) {
            throw new BagNotMatchException();
        }

        Optional<Locker> target = this.lockers.stream().filter(l -> l.vacancyRate() > 0).findFirst();

        if (target.isPresent()) {
            return target.get().saveBag(bag);
        } else {
            throw new NoStorageException();
        }
    }

}
