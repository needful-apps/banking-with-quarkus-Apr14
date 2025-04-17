package de.needfulapps.services;

import de.needfulapps.StockPriceReply;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class StockPriceService {

    private final Random random = new Random();

    public Multi<StockPriceReply> generatePrices(List<String> symbols) {
        var now = Instant.now().toEpochMilli();

        return Multi.createFrom().iterable(
                symbols.stream().map(
                        symbol -> StockPriceReply.newBuilder()
                                .setSymbol(symbol)
                                .setPrice(50+ random.nextDouble()*100)
                                .setTimestamp(now)
                                .build()).toList());
    }

}
