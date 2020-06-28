package com.mitusov;


import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    /**
     * Давайте научимся комбинировать функции в более сложные функции. Для примера построим следующую комбинацию. Дан предикат condition и две функции ifTrue и ifFalse. Напишите метод ternaryOperator, который из них построит новую функцию, возвращающую значение функции ifTrue, если предикат выполнен, и значение ifFalse иначе.
     * <p>
     * Пример использования метода (можно было все свернуть в одну строчку, но для наглядности все элементы вынесены в отдельные переменные):
     * <p>
     * Predicate<Object> condition = Objects::isNull;
     * Function<Object, Integer> ifTrue = obj -> 0;
     * Function<CharSequence, Integer> ifFalse = CharSequence::length;
     * Function<String, Integer> safeStringLength = ternaryOperator(condition, ifTrue, ifFalse);
     * Результирующая функция будет для нулевых ссылок на String возвращать 0, а для ненулевых ссылок возвращать длину строки.
     * <p>
     * В качестве дополнительного задания самостоятельно разберите, почему у метода ternaryOperator такая сложная сигнатура.
     */

    public static <T, U> Function<T, U> ternaryOperator(
            Predicate<? super T> condition,
            Function<? super T, ? extends U> ifTrue,
            Function<? super T, ? extends U> ifFalse) {

        return x -> condition.test(x) ? ifTrue.apply(x) : ifFalse.apply(x);
    }

    /**
     * Напишите метод, возвращающий стрим псевдослучайных целых чисел. Алгоритм генерации чисел следующий:
     * <p>
     * Берется какое-то начальное неотрицательное число (оно будет передаваться в ваш метод проверяющей системой).
     * Первый элемент последовательности устанавливается равным этому числу.
     * Следующие элементы вычисляются по рекуррентной формуле R_{n+1}=\mathrm{mid}(R_{n}^2)R
     * где mid — это функция, выделяющая второй, третий и четвертый разряд переданного числа. Например, \mathrm{mid}(123456) = 345mid(123456)=345.
     * Пример
     * pseudoRandomStream(13) должен вернуть стрим, состоящий из следующих чисел:
     * 13, 16, 25, 62, 384, 745, 502, 200, 0, ... (дальше бесконечное количество нулей)
     */

    public static IntStream pseudoRandomStream(int seed) {
        return IntStream.iterate(seed, n -> (n * n / 10) % 1000);
    }

    /**
     * Напишите метод, находящий в стриме минимальный и максимальный элементы в соответствии порядком,
     * заданным Comparator'ом.
     * Найденные минимальный и максимальный элементы передайте в minMaxConsumer следующим образом:
     * minMaxConsumer.accept(min, max);
     * Если стрим не содержит элементов, то вызовите
     * minMaxConsumer.accept(null, null);
     **/

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {

        MinMaxFinder<T> minMaxFinder = new MinMaxFinder<>(order);
        stream.forEach(minMaxFinder);
        minMaxConsumer.accept(minMaxFinder.min, minMaxFinder.max);
    }


    private static class MinMaxFinder<T> implements Consumer<T> {

        private final Comparator<? super T> order;
        T min;
        T max;

        private MinMaxFinder(Comparator<? super T> order) {
            this.order = order;
        }

        @Override
        public void accept(T t) {
            if (min == null || order.compare(t, min) < 0) {
                min = t;
            }
            if (max == null || order.compare(max, t) < 0) {
                max = t;
            }
        }
    }


    public static void main(String[] args) {

    }
}
