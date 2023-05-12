package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        Objects.requireNonNull(account);
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        Objects.requireNonNull(account);
        synchronized (accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id) != null;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Negative amount is not allowed.");
        }
        synchronized (accounts) {
            Optional<Account> from = getById(fromId);
            Optional<Account> to = getById(toId);
            boolean result = from.isPresent() && to.isPresent() && from.get().amount() >= amount;
            if (result) {
                update(new Account(from.get().id(), from.get().amount() - amount));
                update(new Account(to.get().id(), to.get().amount() + amount));
            }
            return result;
        }
    }
}
