package com.tw.locker;

import com.tw.locker.enums.BagSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Ticket {
    private UUID id;
    private String bagId;
    private String lockerId;
    private BagSize bagSize;
}
