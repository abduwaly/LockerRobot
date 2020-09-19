package com.tw.locker;

import com.tw.locker.exceptions.NoStorageException;
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

    public Bag takeBag(Ticket ticket) {
        Locker correspondingLocker = lockers.stream().filter(l -> l.getId().equals(ticket.getLockerId())).findFirst().get();

        return correspondingLocker.takeBag(ticket);
    }
}
