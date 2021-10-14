package com.politrons.grpc.simple;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;

import java.io.IOException;

public class RpcServer {
    /**
     * Just like Finagle here we define a server in a specific port and we add a service.
     * <p>
     * All request in port specify it will be redirected to the service.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(9999)
                .addService(ServerInterceptors.intercept(new RpcServiceImpl(), new HeaderServerInterceptor()))
                .build();
        server.start();
        server.awaitTermination();
    }
}
