package com.politrons.grpc.reactive;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ReactiveServer {
    /**
     * Just like Finagle here we define a server in a specific port and we add a service.
     *
     * All request in port specify it will be redirected to the service.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(9991)
                .addService(new ReactiveServiceImpl()).build();
        server.start();
        server.awaitTermination();
    }
}
