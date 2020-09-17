package com.tw.locker;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LockerTests {

    public static final String TEST_BAG_1 = "testBag-1";
    public static final String TEST_LOCKER_1 = "testLocker-1";

    @Test
    void should_save_bag_successfully_and_return_ticket_given_small_bag_and_S_Locker() {
        Bag bag = new Bag(TEST_BAG_1, BagSize.SMALL);
        Locker locker = new Locker(TEST_LOCKER_1, LockerType.S);

        Ticker actual = locker.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), TEST_BAG_1);
        assertEquals(actual.getLockerId(), TEST_LOCKER_1);
    }
}
