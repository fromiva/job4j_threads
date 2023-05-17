package ru.job4j.fork;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ForkSearchTest {
    @Test
    void whenSearchIntegerInArrayShorterThenTenElementsAndFound() {
        Integer[] array = new Integer[] {1, 2, 3, 4};
        Integer target = 3;
        int expected = 2;
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchIntegerInArrayShorterThenTenElementsAndNotFound() {
        Integer[] array = new Integer[] {1, 2, 3, 4};
        Integer target = 5;
        int expected = -1;
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchStringInArrayShorterThenTenElementsAndFound() {
        String[] array = new String[] {"aa", "ab", "ac", "ad"};
        String target = "ac";
        int expected = 2;
        ForkSearch<String> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchStringInArrayShorterThenTenElementsAndNotFound() {
        String[] array = new String[] {"aa", "ab", "ac", "ad"};
        String target = "ae";
        int expected = -1;
        ForkSearch<String> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchIntegerInArrayGreaterThenTenElementsAndFoundInFirstHalf() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Integer target = 3;
        int expected = 2;
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchIntegerInArrayGreaterThenTenElementsWithRepetitionAndFoundInFirstHalf() {
        Integer[] array = new Integer[] {1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        Integer target = 3;
        int expected = 2;
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchIntegerInArrayGreaterThenTenElementsWithNullAndRepetitionAndFoundInFirstHalf() {
        Integer[] array = new Integer[] {1, 2, null, null, 3, null, 3, 3, 3, null, 3, 3};
        Integer target = null;
        int expected = 2;
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchIntegerInBigArrayAndFoundInSecondHalf() {
        int size = 100;
        Integer[] array = new Integer[size];
        Integer target = 85;
        int expected = 85;
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchIntegerInArrayGreaterThenTenElementsAndFoundInSecondHalf() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Integer target = 12;
        int expected = 11;
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchIntegerInArrayGreaterThenTenElementsAndNotFound() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Integer target = 13;
        int expected = -1;
        ForkSearch<Integer> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchStringInArrayGreaterThenTenElementsAndFoundInFirstHalf() {
        String[] array = new String[] {"aa", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "ak", "al"};
        String target = "ac";
        int expected = 2;
        ForkSearch<String> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchStringInArrayGreaterThenTenElementsAndFoundInSecondHalf() {
        String[] array = new String[] {"aa", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "ak", "al"};
        String target = "al";
        int expected = 11;
        ForkSearch<String> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenSearchStringInArrayGreaterThenTenElementsAndNotFound() {
        String[] array = new String[] {"aa", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "ak", "al"};
        String target = "am";
        int expected = -1;
        ForkSearch<String> searcher = new ForkSearch<>(array);
        int actual = searcher.findFirst(target);
        assertThat(actual).isEqualTo(expected);
    }
}
