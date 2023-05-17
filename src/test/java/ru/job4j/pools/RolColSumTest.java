package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {
    @Test
    void sumWhenMatrixTwoByTwo() {
        int[][] matrix = new int[][]{
                {1, 1},
                {1, 1}};
        RolColSum.Sums[] expected = new RolColSum.Sums[]{
                new RolColSum.Sums(2, 2),
                new RolColSum.Sums(2, 2)};
        RolColSum.Sums[] actual = RolColSum.sum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sumWhenMatrixThreeByThree() {
        int[][] matrix = new int[][]{
                {1, 1, 2},
                {1, 1, 2},
                {1, 1, 3}};
        RolColSum.Sums[] expected = new RolColSum.Sums[]{
                new RolColSum.Sums(4, 3),
                new RolColSum.Sums(4, 3),
                new RolColSum.Sums(5, 7)};
        RolColSum.Sums[] actual = RolColSum.sum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sumWhenMatrixFourByFour() {
        int[][] matrix = new int[][]{
                {1, 1, 2, 5},
                {1, 1, 2, 7},
                {1, 1, 3, 9},
                {1, 1, 5, 11}};
        RolColSum.Sums[] expected = new RolColSum.Sums[]{
                new RolColSum.Sums(9, 4),
                new RolColSum.Sums(11, 4),
                new RolColSum.Sums(14, 12),
                new RolColSum.Sums(18, 32)};
        RolColSum.Sums[] actual = RolColSum.sum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void asyncSumWhenMatrixTwoByTwo() {
        int[][] matrix = new int[][]{
                {1, 1},
                {1, 1}};
        RolColSum.Sums[] expected = new RolColSum.Sums[]{
                new RolColSum.Sums(2, 2),
                new RolColSum.Sums(2, 2)};
        RolColSum.Sums[] actual = RolColSum.asyncSum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void asyncSumWhenMatrixThreeByThree() {
        int[][] matrix = new int[][]{
                {1, 1, 2},
                {1, 1, 2},
                {1, 1, 3}};
        RolColSum.Sums[] expected = new RolColSum.Sums[]{
                new RolColSum.Sums(4, 3),
                new RolColSum.Sums(4, 3),
                new RolColSum.Sums(5, 7)};
        RolColSum.Sums[] actual = RolColSum.asyncSum(matrix);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void asyncSumWhenMatrixFourByFour() {
        int[][] matrix = new int[][]{
                {1, 1, 2, 5},
                {1, 1, 2, 7},
                {1, 1, 3, 9},
                {1, 1, 5, 11}};
        RolColSum.Sums[] expected = new RolColSum.Sums[]{
                new RolColSum.Sums(9, 4),
                new RolColSum.Sums(11, 4),
                new RolColSum.Sums(14, 12),
                new RolColSum.Sums(18, 32)};
        RolColSum.Sums[] actual = RolColSum.asyncSum(matrix);
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
