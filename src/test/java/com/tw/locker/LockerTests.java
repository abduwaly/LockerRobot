package com.tw.locker;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    void should_save_bag_successfully_and_return_ticket_given_medium_bag_and_primary_robot_with_2_M_lockers_both_has_capacity(){
        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        PrimaryLockerRobot robot = initRobot(LockerType.M, 1, 1);

        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);
    }

    @Test
    void should_save_bag_successfully_into_second_locker_and_return_ticket_given_medium_bag_and_primary_robot_with_2_M_lockers_and_only_second_has_capacity(){
        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        PrimaryLockerRobot robot = initRobot(LockerType.M, 0, 1);

        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_2);
    }

    @Test
    void should_return_no_storage_exception_given_medium_bag_and_primary_robot_with_2_M_lockers_both_without_capacity(){
        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        PrimaryLockerRobot robot = initRobot(LockerType.M, 0, 0);

        assertThrows(NoStorageException.class, () -> robot.saveBag(bag));
    }

    @Test
    void should_save_bag_into_second_given_large_bag_and_super_robot_with_2_L_lockers_and_second_has_more_vacancy_rate() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);

        Locker locker1 = new Locker(TEST_LOCKER_1, LockerType.L, 2);
        locker1.saveBag(new Bag("temp_bag", BagSize.LARGE));

        Locker locker2 = new Locker(TEST_LOCKER_2, LockerType.L, 2);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        SuperLockerRobot robot = new SuperLockerRobot(lockers);

        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_2);
    }

    @Test
    void should_save_bag_into_second_given_large_bag_and_super_robot_with_2_L_lockers_and_only_second_has_capacity() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);

        Locker locker1 = new Locker(TEST_LOCKER_1, LockerType.L, 1);
        locker1.saveBag(new Bag("temp_bag", BagSize.LARGE));

        Locker locker2 = new Locker(TEST_LOCKER_2, LockerType.L, 2);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        SuperLockerRobot robot = new SuperLockerRobot(lockers);

        Ticket actual = robot.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_2);
    }

    @Test
    void should_return_no_storage_exception_given_large_bag_and_super_robot_with_2_L_lockers_and_both_has_no_capacity() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);

        Locker locker1 = new Locker(TEST_LOCKER_1, LockerType.L, 1);
        locker1.saveBag(new Bag("temp_bag11", BagSize.LARGE));

        Locker locker2 = new Locker(TEST_LOCKER_2, LockerType.L, 1);
        locker2.saveBag(new Bag("temp_bag22", BagSize.LARGE));

        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        SuperLockerRobot robot = new SuperLockerRobot(lockers);

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
        PrimaryLockerRobot robot = initRobot(LockerType.M, 1, 1);
        Ticket ticket = robot.saveBag(bag);

        Bag actual = robot.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    @Test
    void should_return_bag_by_super_locker_robot_given_a_ticket_of_large_bag_provided() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);

        Locker locker1 = new Locker(TEST_LOCKER_1, LockerType.L, 2);
        locker1.saveBag(new Bag("temp_bag", BagSize.LARGE));

        Locker locker2 = new Locker(TEST_LOCKER_2, LockerType.L, 2);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        SuperLockerRobot robot = new SuperLockerRobot(lockers);

        Ticket ticket = robot.saveBag(bag);

        Bag actual = robot.takeBag(ticket);


        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    private PrimaryLockerRobot initRobot(LockerType type, int firstCapacity, int secondCapacity) {
        Locker locker1 = new Locker(TEST_LOCKER_1, type, firstCapacity);
        Locker locker2 = new Locker(TEST_LOCKER_2, type, secondCapacity);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        return new PrimaryLockerRobot(lockers);
    }
}
