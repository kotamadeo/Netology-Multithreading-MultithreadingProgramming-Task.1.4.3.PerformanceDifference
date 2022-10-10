package com.gmail.at.kotamadeo;

import java.util.Random;

public class ArrayGenerator {

    /**
     * Метод формирования массива целых чисел
     *
     * @param size     заданный размер массива
     * @param maxValue заданное максимальное значение для элементов массива
     * @return ссылка на созданный массив
     */
    public static int[] generateArray(int size, int maxValue) {
        return new Random().ints(size, 0, maxValue).toArray();
    }
}
