package com.politrons.grpc.benchmark.regular;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class RpcBenchMarkServer {
    /**
     * Just like Finagle here we define a server in a specific port and we add a service.
     *
     * All request in port specify it will be redirected to the service.
     */
    public static void start() throws IOException {
        Server server = ServerBuilder
                .forPort(9998)
                .addService(new RpcBenchMarkServiceImpl()).build();
        server.start();
    }
}
