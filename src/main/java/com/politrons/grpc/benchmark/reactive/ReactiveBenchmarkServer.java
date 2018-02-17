package com.politrons.grpc.benchmark.reactive;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Just like Finagle here we define a server in a specific port and we add a service.
 * <p>
 * All request in port specify it will be redirected to the service.
 */
public class ReactiveBenchmarkServer {

    public static void start() throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(9991)
                .addService(new ReactiveBenchmarkServiceImpl()).build();
        server.start();
        Thread.sleep(1000);
    }
}
