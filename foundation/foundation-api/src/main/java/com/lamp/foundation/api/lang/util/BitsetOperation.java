package com.lamp.foundation.api.lang.util;

import lombok.Setter;

/**
 * @author hahaha
 */
public class BitsetOperation {

    private static final Bitset[] BIT_SET_ARRAY = new Bitset[32];

    private static final BitsetOperation DEFAULT = new BitsetOperation();

    public static Bitset getDefault(int index) {
        if (index >= BIT_SET_ARRAY.length) {
            return DEFAULT.create(index);
        }
        return BIT_SET_ARRAY[index];
    }

    public static BitsetOperation of() {
        BitsetOperation bitsetOperation = new BitsetOperation();
        bitsetOperation.setIncrement(true);
        return bitsetOperation;
    }

    static {
        for (int i = 0; i < BIT_SET_ARRAY.length; i++) {
            BIT_SET_ARRAY[i] = DEFAULT.increment();
        }
    }

    @Setter
    private boolean increment;

    private int index = 1;


    public Bitset increment() {
        return this.create(this.index++);
    }

    public Bitset create(int index) {
        if (index == -1) {
            throw new RuntimeException(" index not zero");
        }
        Bitset bitset = new Bitset();
        bitset.index = index;
        bitset.value = bitset.index << 1;
        return bitset;
    }

    public Bitset create(Bitset... bitsets) {
        Bitset bitset = new Bitset();
        for (Bitset b : bitsets) {
            bitset.index += b.index;
            bitset.value = bitset.value | b.value;
        }
        return bitset;
    }


    public class Bitset {

        private int index;

        private int value;


        public boolean match(Bitset bitset) {
            return this.match(bitset.value);
        }

        public boolean match(int bitset) {
            return (bitset & this.value) == this.value;
        }

        public Bitset create(Bitset... bitsets) {
            return BitsetOperation.this.create(bitsets);
        }

    }

}
