package com.tw.locker;

import com.tw.locker.enums.BagSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Bag {

    private final String id;
    private final BagSize size;

}
