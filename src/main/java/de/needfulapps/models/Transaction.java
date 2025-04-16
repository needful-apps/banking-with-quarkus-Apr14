package de.needfulapps.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Transaction {
    private String id;
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private String currency;
}

// 1. Neue Proto-Datei  "transaction.proto"
// 2. Neuer TransactionGrpcServie
// 3. Neue TransactionService
//    a - getTransaction
//    b - addTransaction -> Kontostand ändern
