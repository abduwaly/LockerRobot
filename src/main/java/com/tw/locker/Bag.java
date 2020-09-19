package com.tw.locker;

import com.tw.locker.enums.BagSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Bag {

    private String id;
    private BagSize size;

}
