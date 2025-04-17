package de.needfulapps.services;

import de.needfulapps.exceptions.InsufficientCreditsException;
import de.needfulapps.models.Account;
import de.needfulapps.models.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.needfulapps.services.TransactionService.MAX_OVERDRAFT;

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

    public void changeBalance(String accountNumber, BigDecimal amount) throws InsufficientCreditsException {
        var account = getAccount(accountNumber);
        if (account != null) {
            log.info(accountNumber + ": " + account.getBalance().add(amount));

            if (account.getBalance().add(amount).compareTo(MAX_OVERDRAFT.negate()) < 0) {
                log.warning("Overdraft limit exceeded for account: " + accountNumber);
                throw new InsufficientCreditsException();
            }

            account.setBalance(account.getBalance().add(amount));
            log.info("Account balance changed: " + account);
        } else {
            log.warning("Account not found: " + accountNumber);
        }
    }
}
