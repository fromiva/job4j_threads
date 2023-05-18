package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {
    @Test
    void sumWhenMatrixTwoByTwo() {
        int[][] matrix = new int[][]{
                {1, 1},
                {1, 1}};
        Sums[] expected = new Sums[]{
                new Sums(2, 2),
                new Sums(2, 2)};
        Sums[] actual = RolColSum.sum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sumWhenMatrixThreeByThree() {
        int[][] matrix = new int[][]{
                {1, 1, 2},
                {1, 1, 2},
                {1, 1, 3}};
        Sums[] expected = new Sums[]{
                new Sums(4, 3),
                new Sums(4, 3),
                new Sums(5, 7)};
        Sums[] actual = RolColSum.sum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sumWhenMatrixFourByFour() {
        int[][] matrix = new int[][]{
                {1, 1, 2, 5},
                {1, 1, 2, 7},
                {1, 1, 3, 9},
                {1, 1, 5, 11}};
        Sums[] expected = new Sums[]{
                new Sums(9, 4),
                new Sums(11, 4),
                new Sums(14, 12),
                new Sums(18, 32)};
        Sums[] actual = RolColSum.sum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void asyncSumWhenMatrixTwoByTwo() {
        int[][] matrix = new int[][]{
                {1, 1},
                {1, 1}};
        Sums[] expected = new Sums[]{
                new Sums(2, 2),
                new Sums(2, 2)};
        Sums[] actual = RolColSum.asyncSum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void asyncSumWhenMatrixThreeByThree() {
        int[][] matrix = new int[][]{
                {1, 1, 2},
                {1, 1, 2},
                {1, 1, 3}};
        Sums[] expected = new Sums[]{
                new Sums(4, 3),
                new Sums(4, 3),
                new Sums(5, 7)};
        Sums[] actual = RolColSum.asyncSum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void asyncSumWhenMatrixFourByFour() {
        int[][] matrix = new int[][]{
                {1, 1, 2, 5},
                {1, 1, 2, 7},
                {1, 1, 3, 9},
                {1, 1, 5, 11}};
        Sums[] expected = new Sums[]{
                new Sums(9, 4),
                new Sums(11, 4),
                new Sums(14, 12),
                new Sums(18, 32)};
        Sums[] actual = RolColSum.asyncSum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sumAndAsyncSumWhenMatrixFourByFour() {
        int[][] matrix = new int[][]{
                {1, 1, 2, 5},
                {1, 1, 2, 7},
                {1, 1, 3, 9},
                {1, 1, 5, 11}};
        assertThat(RolColSum.asyncSum(matrix)).isEqualTo(RolColSum.sum(matrix));
    }
}
