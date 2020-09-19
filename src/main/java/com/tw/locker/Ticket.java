package com.tw.locker;

import com.tw.locker.enums.BagSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Ticket {
    private final UUID id;
    private final String bagId;
    private final String lockerId;
    private final BagSize bagSize;
}
