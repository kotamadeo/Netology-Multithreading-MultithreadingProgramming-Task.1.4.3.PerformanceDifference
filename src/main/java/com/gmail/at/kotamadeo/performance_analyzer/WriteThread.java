package com.gmail.at.kotamadeo.performance_analyzer;

import lombok.AllArgsConstructor;

import java.util.Map;

import static java.lang.Thread.currentThread;

@AllArgsConstructor
public class WriteThread implements Runnable {
    private final int[] array;
    private final int start;
    private final int end;
    private final Map<Integer, Integer> map;

    @Override
    public void run() {
        System.out.printf("Поток %s начал запись элементов в map.%n", currentThread().getName());
        for (int j = start; j <= end; j++) {
            map.put(j, array[j]);
        }
        System.out.printf("Поток %s закончил запись элементов в map.%n", currentThread().getName());
    }
}
