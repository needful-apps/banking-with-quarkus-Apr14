package de.needfulapps.controller;


import de.needfulapps.StockGrpc;
import de.needfulapps.StockPriceReply;
import de.needfulapps.StockRequest;
import de.needfulapps.services.StockPriceService;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;

import java.time.Duration;

@GrpcService
public class StockPriceGrpcService implements StockGrpc {

    @Inject
    StockPriceService service;

    @Override
    public Multi<StockPriceReply> streamStockPrices(StockRequest request) {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem()
                .transformToMulti(tick -> service.generatePrices(request.getSymbolList()))
                .concatenate();
    }
}
