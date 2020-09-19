package com.tw.locker;


import com.tw.locker.enums.BagSize;
import com.tw.locker.enums.LockerType;
import com.tw.locker.exceptions.BagNotFoundException;
import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LockerTests {

    public static final String TEST_BAG_1 = "testBag-1";
    public static final String TEST_LOCKER_1 = "testLocker-1";
    private static final String TEST_LOCKER_2 = "testLocker-2";

    @Test
    void should_save_bag_successfully_and_return_ticket_given_small_bag_and_S_locker() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);

        Ticket actual = locker.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);
    }

    @Test
    void should_return_no_storage_error_given_small_bag_and_S_locker_without_capacity() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 0);

        assertThrows(NoStorageException.class, () -> locker.saveBag(bag));
    }

    @Test
    void should_save_bag_successfully_and_return_ticket_given_medium_bag_and_primary_robot_with_2_M_lockers_both_has_capacity() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        PrimaryLockerRobot robot = initPrimaryRobot(LockerType.M, 1, 1);

        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);
    }

    @Test
    void should_save_bag_successfully_into_second_locker_and_return_ticket_given_medium_bag_and_primary_robot_with_2_M_lockers_and_only_second_has_capacity() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        PrimaryLockerRobot robot = initPrimaryRobot(LockerType.M, 0, 1);

        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_2);
    }

    @Test
    void should_return_no_storage_exception_given_medium_bag_and_primary_robot_with_2_M_lockers_both_without_capacity() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        PrimaryLockerRobot robot = initPrimaryRobot(LockerType.M, 0, 0);

        assertThrows(NoStorageException.class, () -> robot.saveBag(bag));
    }

    @Test
    void should_save_bag_into_second_given_large_bag_and_super_robot_with_2_L_lockers_and_second_has_more_vacancy_rate() {

        SuperLockerRobot robot = initSuperLockerRobot(2, 2, true, false);

        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);
        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_2);
    }

    @Test
    void should_save_bag_into_second_given_large_bag_and_super_robot_with_2_L_lockers_and_only_second_has_capacity() {

        SuperLockerRobot robot = initSuperLockerRobot(1, 2, true, false);

        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);
        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_2);
    }

    @Test
    void should_return_no_storage_exception_given_large_bag_and_super_robot_with_2_L_lockers_and_both_has_no_capacity() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);

        SuperLockerRobot robot = initSuperLockerRobot(1, 1, true, true);

        assertThrows(NoStorageException.class, () -> robot.saveBag(bag));
    }

    @Test
    void should_return_bag_successfully_given_a_ticket_of_small_bag_provided() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);
        Ticket ticket = locker.saveBag(bag);

        Bag actual = locker.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    @Test
    void should_return_bag_by_primary_locker_robot_given_a_ticket_of_medium_bag_provided() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        PrimaryLockerRobot robot = initPrimaryRobot(LockerType.M, 1, 1);
        Ticket ticket = robot.saveBag(bag);

        Bag actual = robot.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    @Test
    void should_return_bag_by_super_locker_robot_given_a_ticket_of_large_bag_provided() {

        SuperLockerRobot robot = initSuperLockerRobot(2, 2, true, false);

        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);
        Ticket ticket = robot.saveBag(bag);

        Bag actual = robot.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    @Test
    void should_return_not_found_exception_given_a_ticket_of_large_bag_onto_locker() {
        SuperLockerRobot robot = initSuperLockerRobot(2, 2, true, false);

        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);
        Ticket ticket = robot.saveBag(bag);

        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);

        assertThrows(BagNotFoundException.class, ()->locker.takeBag(ticket));
    }

    @Test
    void should_return_not_found_exception_given_a_ticket_of_medium_bag_onto_super_locker_robot() {
        PrimaryLockerRobot robot = initPrimaryRobot(LockerType.M, 1, 1);
        Ticket ticket = robot.saveBag(new Bag("test-medium-bag", BagSize.MEDIUM));

        SuperLockerRobot superRobot = initSuperLockerRobot(2, 2, true, true);

        assertThrows(BagNotFoundException.class, ()-> superRobot.takeBag(ticket));
    }

    @Test
    void should_return_not_found_exception_given_a_ticket_of_small_bag_onto_primary_locker_robot() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);
        Ticket ticket = locker.saveBag(bag);

        PrimaryLockerRobot robot = initPrimaryRobot(LockerType.M, 1, 1);

        assertThrows(BagNotFoundException.class, ()->robot.takeBag(ticket));
    }

    @Test
    void should_return_fake_exception_given_a_fake_ticket_of_small_bag_onto_locker() {
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);
        Ticket fakeTicket = new Ticket(UUID.randomUUID(), "fake-bag-0", locker.getId(), BagSize.SMALL);

        assertThrows(FakeTicketException.class, () -> locker.takeBag(fakeTicket));
    }

    @Test
    void should_return_fake_ticket_exception_given_a_fake_ticket_of_medium_bag_onto_primary_locker_robot() {
        PrimaryLockerRobot robot = initPrimaryRobot(LockerType.M, 1,1);
        Ticket ticket = robot.saveBag(new Bag("Test-bag", BagSize.MEDIUM));

        Ticket fakeTicket = new Ticket(UUID.randomUUID(), "fake-bag-0", ticket.getLockerId(), BagSize.MEDIUM);

        assertThrows(FakeTicketException.class, ()->robot.takeBag(fakeTicket));
    }

    @Test
    void should_return_fake_ticket_exception_given_a_fake_ticket_of_large_bag_onto_super_locker_robot() {
        SuperLockerRobot robot = initSuperLockerRobot(1, 1, false, false);
        Ticket ticket = robot.saveBag(new Bag("Test-bag", BagSize.LARGE));

        Ticket fakeTicket = new Ticket(UUID.randomUUID(), "fake-bag-0", ticket.getLockerId(), BagSize.LARGE);

        assertThrows(FakeTicketException.class, ()->robot.takeBag(fakeTicket));
    }

    private PrimaryLockerRobot initPrimaryRobot(LockerType type, int firstCapacity, int secondCapacity) {
        Locker locker1 = new Locker(TEST_LOCKER_1, type, firstCapacity);
        Locker locker2 = new Locker(TEST_LOCKER_2, type, secondCapacity);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        return new PrimaryLockerRobot(lockers);
    }

    private SuperLockerRobot initSuperLockerRobot(int firstCapacity, int secondCapacity, boolean hasInitial1, boolean hasInitial2) {
        Locker locker1 = new Locker(TEST_LOCKER_1, LockerType.L, firstCapacity);
        if (hasInitial1) {
            locker1.saveBag(new Bag("temp_bag11", BagSize.LARGE));
        }

        Locker locker2 = new Locker(TEST_LOCKER_2, LockerType.L, secondCapacity);
        if (hasInitial2) {
            locker2.saveBag(new Bag("temp_bag22", BagSize.LARGE));
        }

        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        return new SuperLockerRobot(lockers);
    }
}
