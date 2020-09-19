package com.tw.locker;

import com.tw.locker.enums.BagSize;
import com.tw.locker.enums.LockerType;
import com.tw.locker.exceptions.NoStorageException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        LockerRobotManager manager = new LockerRobotManager(lockers, null);

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
        LockerRobotManager manager = new LockerRobotManager(lockers, null);
        manager.saveBag(new Bag("tempBagId", BagSize.SMALL));

        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        assertThrows(NoStorageException.class, ()-> manager.saveBag(bag));
    }

    @Test
    void should_save_bag_successfully_for_VIP_user_given_medium_bag_and_a_manager_with_a_PrimaryLockerRobot_has_storage() {

        List<PrimaryLockerRobot> robots = initPrimaryRobots(LockerType.M, 1, 1);

        LockerRobotManager manager = new LockerRobotManager(null, robots);

        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);
        Ticket actual = manager.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);

    }

    @Test
    void should_return_no_storage_exception_for_VIP_user_given_medium_bag_and_a_manager_with_a_PrimaryLockerRobot_without_storage() {

        List<PrimaryLockerRobot> robots = initPrimaryRobots(LockerType.M, 1, 1);

        LockerRobotManager manager = new LockerRobotManager(null, robots);
        manager.saveBag(new Bag("temp1", BagSize.MEDIUM));
        manager.saveBag(new Bag("temp2", BagSize.MEDIUM));

        Bag bag = new Bag(TEST_BAG_1, BagSize.MEDIUM);

        assertThrows(NoStorageException.class, () -> manager.saveBag(bag));
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
}
