package de.needfulapps.controller;

import de.needfulapps.*;
import de.needfulapps.services.TransactionService;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

import java.math.BigDecimal;

@GrpcService
public class TransactionGrpcService implements TransactionGrpc {

    @Inject
    TransactionService service;

    @Override
    public Uni<GetTransactionReply> getTransaction(GetTransactionRequest request) {
        return service.getTransaction(request.getId());
    }

    @Override
    public Uni<AddTransactionReply> addTransaction(AddTransactionRequest request) {
        return service.addTransaction(request.getSender(), request.getReceiver(), BigDecimal.valueOf(request.getAmount()), request.getCurrency());
    }
}
