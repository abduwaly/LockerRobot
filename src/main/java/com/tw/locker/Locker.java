package com.tw.locker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class Locker {

    private final String id;
    private final LockerType type;
    private final int capacity;


    private List<Bag> bags = new ArrayList<>();

    public double vacancyRate() {
        return (double) (this.capacity - this.getBags().size()) / this.capacity;
    }

    public Ticket saveBag(Bag bag) {
        if (this.capacity <= 0) {
            throw new NoStorageException();
        } else {
            this.bags.add(bag);
            return new Ticket(UUID.randomUUID(), bag.getId(), this.id, bag.getSize());
        }
    }

    public Bag takeBag(Ticket ticket) {
        if (!isBagSizeMatchLockerType(ticket.getBagSize())) {
            throw new BagNotFoundException();
        }

        Optional<Bag> bag = bags.stream().filter(b -> ticket.getBagId().equals(b.getId())).findFirst();
        if (bag.isPresent()) {
            return bag.get();
        } else {
            throw new FakeTicketException();
        }
    }

    private boolean isBagSizeMatchLockerType(BagSize bagSize) {
        if (this.type == LockerType.L && bagSize == BagSize.LARGE) {
            return true;
        }
        if (this.type == LockerType.M && bagSize == BagSize.MEDIUM) {
            return true;
        }

        return this.type == LockerType.S && bagSize == BagSize.SMALL;
    }
}
