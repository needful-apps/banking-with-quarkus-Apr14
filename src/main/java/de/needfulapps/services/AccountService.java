package de.needfulapps.services;

import de.needfulapps.models.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log
@ApplicationScoped
public class AccountService {
    private List<Account> accounts = new ArrayList<>(List.of(
            new Account("1", "Max Mustermann", new BigDecimal(1000)),
            new Account("2", "Erika Mustermann", new BigDecimal(2000)),
            new Account("3", "Hans Müller", new BigDecimal(3000))
    ));

    public Account addAccount(String name) {
        var newAccount = new Account(UUID.randomUUID().toString(), name, new BigDecimal(-1));
        accounts.add(newAccount);
        log.info("Account added: " + newAccount);
        return newAccount;
    }

    public Account getAccount(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public void changeBalance(String accountNumber, BigDecimal amount) {
        var account = getAccount(accountNumber);
        if (account != null) {
            account.setBalance(account.getBalance().add(amount));
            log.info("Account balance changed: " + account);
        } else {
            log.warning("Account not found: " + accountNumber);
        }
    }
}
