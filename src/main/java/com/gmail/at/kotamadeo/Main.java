package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.performance_analyzer.ReadThread;
import com.gmail.at.kotamadeo.performance_analyzer.WriteThread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    private static final int ARRAY_SIZE = 3_000_000_0;
    private static final int MAX_VALUE_OF_ELEMENT = 100;
    private static final int THREAD_AMOUNT = Runtime.getRuntime().availableProcessors();
    private static final int SLEEP_TIME = 5;
    private static int[] array = ArrayGenerator.generateArray(ARRAY_SIZE, MAX_VALUE_OF_ELEMENT);


    public static void main(String[] args) throws InterruptedException {
        // нужно выбрать нужную мапу, иначе будет стековерфлоуЕррор
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        final ExecutorService writePool = Executors.newFixedThreadPool(THREAD_AMOUNT);
        final ExecutorService readPool = Executors.newFixedThreadPool(THREAD_AMOUNT);
        long startTime = System.currentTimeMillis();
        benchMap(writePool, readPool, concurrentHashMap);
        SECONDS.sleep(SLEEP_TIME);
        getSpendTime(startTime);
    }

    private static void benchMap(ExecutorService writePool, ExecutorService readPool, Map<Integer, Integer> map) throws InterruptedException {
        int step = array.length / THREAD_AMOUNT;
        for (int i = 0; i < THREAD_AMOUNT; i++) {
            int start = i * step;
            int end = step < (array.length - 1 - (i + 1) * step) ? (array.length - 1) : (i + 1) * step;
            writePool.submit(new WriteThread(array, start, end, map));
            readPool.submit(new ReadThread(map, ARRAY_SIZE));
        }
        writePool.shutdown();
        readPool.shutdown();
        writePool.awaitTermination(2, SECONDS);
        readPool.awaitTermination(2, SECONDS);
    }

    private static void getSpendTime(long start) {
        System.out.printf("Время записи/чтения: %d мс.%n", System.currentTimeMillis() - start - SLEEP_TIME);
    }
}