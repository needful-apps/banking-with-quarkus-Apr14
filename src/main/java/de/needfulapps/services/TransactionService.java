package de.needfulapps.services;

import de.needfulapps.AddTransactionReply;
import de.needfulapps.GetTransactionReply;
import de.needfulapps.exceptions.InsufficientCreditsException;
import de.needfulapps.models.Transaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

@ApplicationScoped
public class TransactionService {

    public static final BigDecimal MAX_OVERDRAFT = new BigDecimal(3000);

    @Inject
    AccountService accountService;

    private HashMap<String, Transaction> transactions = new HashMap<>();

    public Uni<AddTransactionReply> addTransaction(String sender, String receiver, BigDecimal amount, String currency) throws InsufficientCreditsException {
        var id = UUID.randomUUID().toString();
        var transaction = new Transaction(id, sender, receiver, amount, currency);

        accountService.changeBalance(sender, amount.negate());
        accountService.changeBalance(receiver, amount);

        transactions.put(transaction.getId(), transaction);
        return getTransactionAsAddReply(transaction);
    }

    public Uni<GetTransactionReply> getTransaction(String transactionId) {
        return getTransactionAsGetReply(transactions.get(transactionId));
    }

    private Uni<GetTransactionReply> getTransactionAsGetReply (Transaction transaction) {
        if (transaction == null) {
            return Uni.createFrom().failure(new RuntimeException("Transaction not found"));
        }
        return Uni.createFrom().item(GetTransactionReply.newBuilder()
                .setId(transaction.getId())
                .setSender(transaction.getSender())
                .setReceiver(transaction.getReceiver())
                .setAmount(transaction.getAmount().doubleValue())
                .setCurrency(transaction.getCurrency())
                .build());
    }

    private Uni<AddTransactionReply> getTransactionAsAddReply (Transaction transaction) {
        if (transaction == null) {
            return Uni.createFrom().failure(new RuntimeException("Transaction not found"));
        }
        return Uni.createFrom().item(AddTransactionReply.newBuilder()
                .setId(transaction.getId())
                .build());
    }
}
