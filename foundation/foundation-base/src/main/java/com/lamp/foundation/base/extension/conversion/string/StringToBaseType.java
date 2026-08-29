package com.lamp.foundation.base.extension.conversion.string;

import java.math.BigDecimal;

import com.lamp.foundation.api.extension.conversion.Conversion;

/**
 * @author hahaha
 */
public interface StringToBaseType {


    class ByteConversion extends AbstractObjectToStringBidirectionalConversion<Byte> {

        @Override
        public Conversion<Byte, String> from() {
            return Byte::valueOf;
        }

    }

    class ShortConversion extends AbstractObjectToStringBidirectionalConversion<Short> {

        @Override
        public Conversion<Short, String> from() {
            return Short::valueOf;
        }
    }

    class IntegerConversion extends AbstractObjectToStringBidirectionalConversion<Integer> {

        @Override
        public Conversion<Integer, String> from() {
            return Integer::valueOf;
        }
    }

    ;

    class LongConversion extends AbstractObjectToStringBidirectionalConversion<Long> {

        @Override
        public Conversion<Long, String> from() {
            return Long::valueOf;
        }
    }

    ;

    class FloatConversion extends AbstractObjectToStringBidirectionalConversion<Float> {

        @Override
        public Conversion<Float, String> from() {
            return Float::valueOf;
        }
    }

    ;

    class DoubleConversion extends AbstractObjectToStringBidirectionalConversion<Double> {

        @Override
        public Conversion<Double, String> from() {
            return Double::valueOf;
        }
    }

    ;

    class BigDecimalConversion extends AbstractObjectToStringBidirectionalConversion<BigDecimal> {

        @Override
        public Conversion<BigDecimal, String> from() {
            return BigDecimal::new;
        }
    }

    ;

}
