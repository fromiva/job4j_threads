package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountStorageTest {
    @Test
    public void whenAdd() {
        AccountStorage storage = new AccountStorage();
        boolean result = storage.add(new Account(1, 100));
        Account account = storage.getById(1).orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(result).isTrue();
        assertThat(account.amount()).isEqualTo(100);
    }

    @Test
    public void whenAddExistingThenFalse() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        boolean result = storage.add(new Account(1, 200));
        Account account = storage.getById(1).orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(result).isFalse();
        assertThat(account.amount()).isEqualTo(100);
    }

    @Test
    public void whenAddNullObjectThenException() {
        AccountStorage storage = new AccountStorage();
        assertThatThrownBy(() -> storage.add(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenUpdate() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        boolean result = storage.update(new Account(1, 200));
        Account account = storage.getById(1).orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(result).isTrue();
        assertThat(account.amount()).isEqualTo(200);
    }

    @Test
    public void whenUpdateNotExistingThenFalse() {
        AccountStorage storage = new AccountStorage();
        boolean result = storage.update(new Account(1, 200));
        assertThat(result).isFalse();
    }

    @Test
    public void whenUpdateNullObjectThenException() {
        AccountStorage storage = new AccountStorage();
        assertThatThrownBy(() -> storage.update(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenFindByIdAndNotFoundThenEmptyOptional() {
        AccountStorage storage = new AccountStorage();
        Optional<Account> account = storage.getById(1);
        assertThat(account).isEmpty();
    }

    @Test
    public void whenDelete() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        boolean result = storage.delete(1);
        Optional<Account> account = storage.getById(1);
        assertThat(result).isTrue();
        assertThat(account).isEmpty();
    }

    @Test
    public void whenDeleteNotExistingThenFalse() {
        AccountStorage storage = new AccountStorage();
        boolean result = storage.delete(1);
        assertThat(result).isFalse();
    }

    @Test
    public void whenTransfer() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        boolean result = storage.transfer(1, 2, 100);
        Account account1 = storage.getById(1).orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Account account2 = storage.getById(2).orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(result).isTrue();
        assertThat(account1.amount()).isEqualTo(0);
        assertThat(account2.amount()).isEqualTo(200);
    }

    @Test
    public void whenTransferAndFirstAccountNotFoundThenFalse() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(2, 100));
        boolean result = storage.transfer(1, 2, 100);
        assertThat(result).isFalse();
    }

    @Test
    public void whenTransferAndSecondAccountNotFoundThenFalse() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        boolean result = storage.transfer(1, 2, 100);
        assertThat(result).isFalse();
    }

    @Test
    public void whenTransferAndHasNoEnoughAmountThenFalse() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        boolean result = storage.transfer(1, 2, 200);
        assertThat(result).isFalse();
    }

    @Test
    public void whenTransferAndNegativeAmountThenException() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        assertThatThrownBy(() -> storage.transfer(1, 2, -100)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Negative amount is not allowed.");
    }
}
