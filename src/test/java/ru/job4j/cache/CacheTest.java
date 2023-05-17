package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {

    @Test
    void whenAddThenTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("Name");
        assertThat(cache.add(model)).isTrue();
    }

    @Test
    void whenAddTwoTimesThenFirstTrueAndSecondFalse() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("Name");
        assertThat(cache.add(model)).isTrue();
        assertThat(cache.add(model)).isFalse();
    }

    @Test
    void whenRemoveThenSecondAddIsTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("Name1");
        assertThat(cache.add(model)).isTrue();
        cache.delete(model);
        assertThat(cache.add(model)).isTrue();
    }

    @Test
    void whenUpdateExistingWithTheSameVersionThenTrue() {
        Cache cache = new Cache();
        Base oldModel = new Base(1, 1);
        oldModel.setName("Name");
        Base newModel = new Base(1, 1);
        newModel.setName("NewName");
        assertThat(cache.add(oldModel)).isTrue();
        assertThat(cache.update(newModel)).isTrue();
        assertThat(newModel).isNotSameAs(cache.get(1));
        assertThat(cache.get(1).getVersion()).isEqualTo(2);
        assertThat(cache.get(1).getName()).isEqualTo("NewName");
    }

    @Test
    void whenUpdateExistingWithDifferentVersionThenException() {
        Cache cache = new Cache();
        Base oldModel = new Base(1, 1);
        oldModel.setName("Name");
        Base newModel = new Base(1, 2);
        newModel.setName("NewName");
        assertThat(cache.add(oldModel)).isTrue();
        assertThatThrownBy(() -> cache.update(newModel))
                .isInstanceOf(OptimisticException.class)
                .hasMessage("Versions are not equal");
    }

    @Test
    void whenUpdateNotExistingThenFalse() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("Name");
        assertThat(cache.update(model)).isFalse();
    }
}
