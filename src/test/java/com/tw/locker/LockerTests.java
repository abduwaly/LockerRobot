package com.tw.locker;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LockerTests {

    @Test
    void should_save_bag_successfully_and_return_ticket_given_small_bag_and_S_Locker() {
        Bag bag = new Bag("testBag-1", "SMALL");
        Locker locker = new Locker("testLocker-1", "S");

        Ticker actual = locker.saveBag(bag);

        assertNotNull(actual);
        assertEquals(actual.getBagId(), "testBag-1");
        assertEquals(actual.getLockerId(), "testLocker-1");
    }
}
