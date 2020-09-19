package com.tw.locker;

import com.tw.locker.enums.BagSize;
import com.tw.locker.enums.LockerType;
import com.tw.locker.exceptions.BagNotMatchException;
import com.tw.locker.exceptions.LockerNotMatchException;
import com.tw.locker.exceptions.NoStorageException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class SuperLockerRobot extends LockerRobotBase {
    public SuperLockerRobot(List<Locker> lockers) {
        if(lockers.stream().allMatch(locker -> locker.getType() == LockerType.L)){
            this.lockers = lockers;
        } else {
            this.lockers = null;
            throw new LockerNotMatchException();
        }

    }

    @Override
    public Ticket saveBag(Bag bag) {
        if (bag.getSize() != BagSize.LARGE) {
            throw new BagNotMatchException();
        }

        if (this.lockers.stream().noneMatch(l -> l.vacancyRate() > 0)) {
            throw new NoStorageException();
        }

        Optional<Locker> target = this.lockers.stream().max(Comparator.comparingDouble(Locker::vacancyRate));
        return target.get().saveBag(bag);
    }
}
