package com.lamp.decoration.foundation.network;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StreamGroupJoinExample {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 35),
            new Person("David", 40),
            new Person("Eve", 45),
            new Person("Frank", 50),
            new Person("Grace", 55),
            new Person("Henry", 60)
        );

        // 每4个对象进行一次join
        String result = IntStream.range(0, persons.size())
            .boxed()
            .collect(Collectors.groupingBy(i -> i / 4))
            .values()
            .stream()
            .map(group -> group.stream()
                .map(persons::get)
                .map(Person::getName)
                .collect(Collectors.joining(", ")))
            .collect(Collectors.joining(" \n "));

        System.out.println(result);
        // 输出: Alice, Bob, Charlie, David | Eve, Frank, Grace, Henry
    }


    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
