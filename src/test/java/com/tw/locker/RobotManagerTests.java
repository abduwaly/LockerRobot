package com.tw.locker;

import com.tw.locker.enums.BagSize;
import com.tw.locker.enums.LockerType;
import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RobotManagerTests {

    private static final String TEST_BAG_1 = "testBag1";
    private static final String TEST_LOCKER_1 = "testLocker1";
    private static final String TEST_LOCKER_2 = "testLocker2";

    @Test
    void should_save_bag_successfully_for_VIP_user_and_return_ticket_given_small_bag_and_S_locker() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);

        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker);
        LockerRobotManager manager = new LockerRobotManager(lockers, null, null);

        Ticket actual = manager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);
    }

    @Test
    void should_return_no_storage_error_for_VIP_user_given_small_bag_and_S_locker_without_capacity(){
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker);
        LockerRobotManager manager = new LockerRobotManager(lockers, null, null);
        manager.saveBag(new Bag("tempBagId", BagSize.SMALL));

        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        assertThrows(NoStorageException.class, ()-> manager.saveBag(bag));
    }

    @Test
    void should_save_bag_successfully_for_VIP_user_given_medium_bag_and_a_manager_with_a_PrimaryLockerRobot_has_storage() {

        List<PrimaryLockerRobot> robots = initPrimaryRobots(LockerType.M, 1, 1);

        LockerRobotManager manager = new LockerRobotManager(null, robots, null);

        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        Ticket actual = manager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);

    }

    @Test
    void should_return_no_storage_exception_for_VIP_user_given_medium_bag_and_a_manager_with_a_PrimaryLockerRobot_without_storage() {

        List<PrimaryLockerRobot> robots = initPrimaryRobots(LockerType.M, 1, 1);

        LockerRobotManager manager = new LockerRobotManager(null, robots, null);
        manager.saveBag(new Bag("temp1", BagSize.MEDIUM));
        manager.saveBag(new Bag("temp2", BagSize.MEDIUM));

        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);

        assertThrows(NoStorageException.class, () -> manager.saveBag(bag));
    }

    @Test
    void should_save_bag_successfully_for_VIP_user_given_large_bag_and_a_manager_with_a_PrimaryLockerRobot_has_storage() {

        List<SuperLockerRobot> robots = initSuperRobots(LockerType.L, 1, 1);

        LockerRobotManager manager = new LockerRobotManager(null, null, robots);

        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);
        Ticket actual = manager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);

    }

    @Test
    void should_return_no_storage_exception_for_VIP_user_given_large_bag_and_a_manager_with_a_PrimaryLockerRobot_without_storage() {
        List<SuperLockerRobot> robots = initSuperRobots(LockerType.L, 1, 1);
        LockerRobotManager manager = new LockerRobotManager(null, null, robots);
        manager.saveBag(new Bag("temp1", BagSize.LARGE));
        manager.saveBag(new Bag("temp2", BagSize.LARGE));

        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);

        assertThrows(NoStorageException.class, () -> manager.saveBag(bag));
    }

    @Test
    void should_return_bag_for_VIP_user_given_a_ticket_of_small_bag() {
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker);
        LockerRobotManager manager = new LockerRobotManager(lockers, null, null);

        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        Ticket ticket = manager.saveBag(bag);

        Bag actual = manager.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    @Test
    void should_return_bag_for_VIP_user_given_a_ticket_of_medium_bag() {
        List<PrimaryLockerRobot> robots = initPrimaryRobots(LockerType.M, 1, 1);
        LockerRobotManager manager = new LockerRobotManager(null, robots, null);

        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        Ticket ticket = manager.saveBag(bag);

        Bag actual = manager.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    @Test
    void should_return_bag_for_VIP_user_given_a_ticket_of_large_bag() {
        List<SuperLockerRobot> robots = initSuperRobots(LockerType.L, 1, 1);
        LockerRobotManager manager = new LockerRobotManager(null, null, robots);

        Bag bag = new Bag(TEST_BAG_1, BagSize.LARGE);
        Ticket ticket = manager.saveBag(bag);

        Bag actual = manager.takeBag(ticket);

        assertNotNull(actual);
        assertEquals(actual.getId(), bag.getId());
    }

    @Test
    void should_return_fake_ticket_exception_for_VIP_user_given_a_fake_ticket_provided() {
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S, 1);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker);
        LockerRobotManager manager = new LockerRobotManager(lockers, null, null);

        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        manager.saveBag(bag);

        Ticket fakeTicket = new Ticket(UUID.randomUUID(), TEST_BAG_1, TEST_LOCKER_1, BagSize.SMALL);

        assertThrows(FakeTicketException.class, () -> manager.takeBag(fakeTicket));
    }

    private List<PrimaryLockerRobot> initPrimaryRobots(LockerType type, int firstCapacity, int secondCapacity) {
        Locker locker1 = new Locker(TEST_LOCKER_1, type, firstCapacity);
        Locker locker2 = new Locker(TEST_LOCKER_2, type, secondCapacity);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        List<PrimaryLockerRobot> result = new ArrayList<>();
        result.add(new PrimaryLockerRobot(lockers));

        return result;
    }

    private List<SuperLockerRobot> initSuperRobots(LockerType type, int firstCapacity, int secondCapacity) {
        Locker locker1 = new Locker(TEST_LOCKER_1, type, firstCapacity);
        Locker locker2 = new Locker(TEST_LOCKER_2, type, secondCapacity);
        List<Locker> lockers = new ArrayList<>();
        lockers.add(locker1);
        lockers.add(locker2);

        List<SuperLockerRobot> result = new ArrayList<>();
        result.add(new SuperLockerRobot(lockers));

        return result;
    }
}
