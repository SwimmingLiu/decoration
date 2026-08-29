/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */

package com.lamp.foundation.base.lang.util.collections;

import java.util.Objects;

/**
 * @author hahaha
 */
@SuppressWarnings({"LombokGetterMayBeUsed", "PatternVariableCanBeUsed"})
public class Tuple {

    public static class Unit<A> {

        private final A unit;

        public Unit(A unit) {
            super();
            this.unit = unit;
        }

        public static <T> Unit<T> of(T t) {
            return new Unit<>(t);
        }

        public A getUnit() {
            return unit;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Unit) {
                return unit.equals(((Unit<?>) obj).unit);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Unit [unit=" + unit + "]";
        }


    }


    public static class Pair<A, B> extends Unit<A> {

        private final B pair;

        public Pair(A unit, B pair) {
            super(unit);
            this.pair = pair;
        }

        public static <A, B> Pair<A, B> of(A a, B b) {
            return new Pair<>(a, b);
        }

        public B getPair() {
            return pair;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            Pair<?, ?> pair1 = (Pair<?, ?>) o;
            return Objects.equals(pair, pair1.pair);
        }

        @Override
        public String toString() {
            return "Pair [pair=" + pair + "unit=" + getUnit() + "]";
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(pair);
        }


    }

    public static class Triplet<A, B, C> extends Pair<A, B> {


        private final C triplet;

        public Triplet(A unit, B pair, C triplet) {
            super(unit, pair);
            this.triplet = triplet;
        }

        public static <A, B, C> Triplet<A, B, C> of(A a, B b, C c) {
            return new Triplet<>(a, b, c);
        }

        public C getTriplet() {
            return triplet;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Triplet)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            Triplet<?, ?, ?> triplet1 = (Triplet<?, ?, ?>) o;
            return Objects.equals(triplet, triplet1.triplet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), triplet);
        }

        @Override
        public String toString() {
            return "Triplet{" +
                   "triplet=" + triplet +
                   "} " + super.toString();
        }
    }

    public static class Quartet<A, B, C, D> extends Triplet<A, B, C> {

        private final D quartet;

        public Quartet(A unit, B pair, C triplet, D quartet) {
            super(unit, pair, triplet);
            this.quartet = quartet;
        }

        public static <A, B, C, D> Quartet<A, B, C, D> of(A unit, B pair, C triplet, D quartet) {
            return new Quartet<>(unit, pair, triplet, quartet);
        }

        public D getQuartet() {
            return quartet;
        }

        @Override
        public String toString() {
            return "Quartet{" +
                   "quartet=" + quartet +
                   "} " + super.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Quartet)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            Quartet<?, ?, ?, ?> quartet1 = (Quartet<?, ?, ?, ?>) o;
            return Objects.equals(quartet, quartet1.quartet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), quartet);
        }
    }

    public static class Quintet<A, B, C, D, E> extends Quartet<A, B, C, D> {

        private final E quintet;

        public Quintet(A unit, B pair, C triplet, D quartet, E quintet) {
            super(unit, pair, triplet, quartet);
            this.quintet = quintet;
        }

        public static <A, B, C, D, E> Quintet<A, B, C, D, E> of(A unit, B pair, C triplet, D quartet, E quintet) {
            return new Quintet<>(unit, pair, triplet, quartet, quintet);
        }

        public final E getQuintet() {
            return quintet;
        }

        @Override
        public String toString() {
            return "Quintet{" +
                   "quintet=" + quintet +
                   "} " + super.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Quintet)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            Quintet<?, ?, ?, ?, ?> quintet1 = (Quintet<?, ?, ?, ?, ?>) o;
            return Objects.equals(quintet, quintet1.quintet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), quintet);
        }
    }

    static class Sextet<A, B, C, D, E, F> extends Quintet<A, B, C, D, E> {

        private final F sextet;

        public Sextet(A unit, B pair, C triplet, D quartet, E quintet, F sextet) {
            super(unit, pair, triplet, quartet, quintet);
            this.sextet = sextet;
        }

        public static <A, B, C, D, E, F> Sextet<A, B, C, D, E, F> of(A unit, B pair, C triplet, D quartet, E quintet, F sextet) {
            return new Sextet<>(unit, pair, triplet, quartet, quintet, sextet);
        }

        public final F getSextet() {
            return sextet;
        }

        @Override
        public String toString() {
            return "Sextet{" +
                   "sextet=" + sextet +
                   "} " + super.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Sextet)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            Sextet<?, ?, ?, ?, ?, ?> sextet1 = (Sextet<?, ?, ?, ?, ?, ?>) o;
            return Objects.equals(sextet, sextet1.sextet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), sextet);
        }
    }

    static class Septet<A, B, C, D, E, F, G> extends Sextet<A, B, C, D, E, F> {

        private final G septet;

        public Septet(A unit, B pair, C triplet, D quartet, E quintet, F sextet, G septet) {
            super(unit, pair, triplet, quartet, quintet, sextet);
            this.septet = septet;
        }

        public static <A, B, C, D, E, F, G> Septet<A, B, C, D, E, F, G> of(A unit, B pair, C triplet, D quartet, E quintet, F sextet, G septet) {
            return new Septet<>(unit, pair, triplet, quartet, quintet, sextet, septet);
        }

        public final G getSeptet() {
            return septet;
        }

        @Override
        public String toString() {
            return "Septet{" +
                   "septet=" + septet +
                   "} " + super.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Septet)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            Septet<?, ?, ?, ?, ?, ?, ?> septet1 = (Septet<?, ?, ?, ?, ?, ?, ?>) o;
            return Objects.equals(septet, septet1.septet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), septet);
        }
    }

    static class Octet<A, B, C, D, E, F, G, H> extends Septet<A, B, C, D, E, F, G> {


        private final H octet;

        public Octet(A unit, B pair, C triplet, D quartet, E quintet, F sextet, G septet, H octet) {
            super(unit, pair, triplet, quartet, quintet, sextet, septet);
            this.octet = octet;
        }

        public H getOctet() {
            return octet;
        }


    }

    static class Ennead<A, B, C, D, E, F, G, H, I> extends Octet<A, B, C, D, E, F, G, H> {

        private final I ennead;

        public Ennead(A unit, B pair, C triplet, D quartet, E quintet, F sextet, G septet, H octet,
            I ennead) {
            super(unit, pair, triplet, quartet, quintet, sextet, septet, octet);
            this.ennead = ennead;
        }

        public I getEnnead() {
            return ennead;
        }


    }

    static class Decade<A, B, C, D, E, F, G, H, I, J> extends Ennead<A, B, C, D, E, F, G, H, I> {

        private final J decade;


        public Decade(A unit, B pair, C triplet, D quartet, E quintet, F sextet, G septet, H octet, I ennead,
            J decade) {
            super(unit, pair, triplet, quartet, quintet, sextet, septet, octet, ennead);
            this.decade = decade;
        }


        public J getDecade() {
            return decade;
        }


    }
}
