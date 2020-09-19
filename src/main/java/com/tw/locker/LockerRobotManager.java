package com.tw.locker;

import com.tw.locker.enums.BagSize;
import com.tw.locker.enums.LockerType;
import com.tw.locker.exceptions.BagNotFoundException;
import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.LockerNotMatchException;
import com.tw.locker.exceptions.NoStorageException;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class LockerRobotManager {

    private List<Locker> lockers;
    private List<AbstractLockerRobot> abstractLockerRobots;
    private List<SuperAbstractLockerRobot> superLockerRobots;

    public LockerRobotManager(List<Locker> lockers, List<AbstractLockerRobot> abstractLockerRobots, List<SuperAbstractLockerRobot> superLockerRobots) {
        if (lockers != null) {
            if (lockers.stream().allMatch(l -> l.getType() == LockerType.S)) {
                this.lockers = lockers;
            } else {
                throw new LockerNotMatchException();
            }
        }

        this.abstractLockerRobots = abstractLockerRobots;
        this.superLockerRobots = superLockerRobots;
    }

    public Ticket saveBag(Bag bag) {
        if (bag.getSize() == BagSize.SMALL) {
            List<Locker> availableLockers = this.lockers.stream().filter(l -> l.vacancyRate() > 0).collect(Collectors.toList());

            if ((long) availableLockers.size() > 0) {
                return availableLockers.get(0).saveBag(bag);
            } else {
                throw new NoStorageException();
            }
        }

        if (bag.getSize() == BagSize.MEDIUM) {
            return this.abstractLockerRobots.get(0).saveBag(bag);
        }

        if (bag.getSize() == BagSize.LARGE) {
            return this.superLockerRobots.get(0).saveBag(bag);
        }

        throw new RuntimeException();
    }

    public Bag takeBag(Ticket ticket) {
        if (ticket.getBagSize() == BagSize.SMALL) {

            if (this.lockers.stream().noneMatch(l -> l.isTicketValid(ticket))) {
                throw new FakeTicketException();
            }

            Optional<Locker> correspondingLocker = this.lockers.stream().filter(l -> l.getId().equals(ticket.getLockerId())).findFirst();
            if (correspondingLocker.isPresent()) {
                return correspondingLocker.get().takeBag(ticket);
            }
        }

        if (ticket.getBagSize() == BagSize.MEDIUM) {
            return this.abstractLockerRobots.get(0).takeBag(ticket);
        }

        if (ticket.getBagSize() == BagSize.LARGE) {
            return this.superLockerRobots.get(0).takeBag(ticket);
        }

        throw new BagNotFoundException();
    }
}
