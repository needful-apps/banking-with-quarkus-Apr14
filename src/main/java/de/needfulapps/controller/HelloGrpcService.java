package de.needfulapps.controller;

import de.needfulapps.HelloGrpc;
import de.needfulapps.HelloReply;
import de.needfulapps.HelloRequest;
import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class HelloGrpcService implements HelloGrpc {

    private static final String SERVER_IP = "127.0.0.1"; ;
    private static final Logger LOG = LoggerFactory.getLogger(HelloGrpcService.class);
    private static final Logger MINILOG = LoggerFactory.getLogger("LOG ME");

    int counter;

    // Hannes - 1433
    // Mit dem Namen in einer List suchen
    // und Kundennummer zurückgeben

    // 1. HashMap mit Namen und ID von fiktiven Customers
    // 2. Mit say Hello, den Namen in der HashMap -> ID zurückgeben

    @Override
    @RolesAllowed({"admin", "user"})
    public Uni<HelloReply> sayHello(HelloRequest request) {

//        "timestamp": {
//            "low": 1121403988,
//                    "high": 406,
//                    "unsigned": false
//        }

        int low = 1121403988;
        int high = 406;

        long unsignedLow = Integer.toUnsignedLong(low);
        long longValue = (((long) high) << 32 | unsignedLow);

        LOG.info("Received request from: " + request.getName());
        MINILOG.info("minime");
        counter++;
        return Uni.createFrom().item("Hello " + request.getName() + " request number: " + counter)
                .map(msg -> HelloReply.newBuilder().setMessage(msg).build());
    }

}
