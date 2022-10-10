package com.gmail.at.kotamadeo.performance_analyzer;

import lombok.AllArgsConstructor;

import java.util.Map;

import static java.lang.Thread.currentThread;

@AllArgsConstructor
public class ReadThread implements Runnable {
    private final Map<?, ?> map;
    private final int size;

    @Override
    public void run() {
        int i = 0;
        System.out.printf("Поток %s начал чтение элементов map.%n", currentThread().getName());
        while (i < size) {
            while (map.get(i) == null) ;
            map.get(i);
            i++;
        }
        System.out.printf("Поток %s завершил чтение элементов map. Прочитано - %d элементов.%n",
                currentThread().getName(), i);
    }
}
