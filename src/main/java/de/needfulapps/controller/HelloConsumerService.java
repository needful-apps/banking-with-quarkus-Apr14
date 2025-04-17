package de.needfulapps.controller;

import de.needfulapps.HelloGrpc;
import de.needfulapps.HelloReply;
import de.needfulapps.HelloRequest;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.java.Log;
import org.slf4j.LoggerFactory;

import java.util.List;

@Log
@ApplicationScoped
public class HelloConsumerService {

    @GrpcClient
    HelloGrpc helloClient;

    @Scheduled(every = "3s")
    public void sayHello() {
        var names = List.of("Hannes", "Gundula", "Hans");
        var name = names.get((int) (Math.random() * names.size()));

//        var result = helloClient.sayHello(HelloRequest.newBuilder().setName(name).build())
//                .onItem().transform(HelloReply::getMessage).await().indefinitely();
//
//        log.info(result);

        helloClient.sayHello(HelloRequest.newBuilder().setName(name).build())
                .onItem().transform(HelloReply::getMessage).subscribe().with(
                        x -> log.info("Got: " + x)
                );
    }
}
