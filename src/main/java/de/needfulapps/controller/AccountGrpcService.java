package de.needfulapps.controller;

import de.needfulapps.*;
import de.needfulapps.models.Account;
import de.needfulapps.services.AccountService;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

@GrpcService
public class AccountGrpcService implements AccountGrpc {

    Logger LOG = LoggerFactory.getLogger(AccountGrpcService.class);

    @Inject
    AccountService service;

    @Override
    public Uni<GetAccountReply> getAccount(GetAccountRequest request) {
        var userAccount = service.getAccount(request.getCustomerNumber());
        return Uni.createFrom()
                .item(userAccount)
                .map(usr ->
                        GetAccountReply.newBuilder()
                                .setName(usr.getName())
                                .setBalance(usr.getBalance().doubleValue())
                                .build());

    }

    @Override
    public Uni<AddAccountReply> addAccount(AddAccountRequest request) {
       var customer = service.addAccount(request.getName());

        return Uni.createFrom().item(customer)
                .map(usr ->
                        AddAccountReply.newBuilder()
                                .setName(usr.getName())
                                .setBalance(usr.getBalance().doubleValue())
                                .setCustomerNumber(usr.getAccountNumber())
                                .build());
    }


}
