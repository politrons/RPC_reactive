package com.politrons.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class RpcClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext(true)
                .build();

        RpcServiceGrpc.RpcServiceBlockingStub stub
                = RpcServiceGrpc.newBlockingStub(channel);

        for (int i = 0; i < 5; i++) {
            RpcResponse response =
                    stub.connect(RpcRequest.newBuilder()
                            .setAttr(i)
                            .setAttr2(1)
                            .build());
            System.out.println(response.getValue());
            Thread.sleep(1000);
        }
        channel.shutdown();
    }
}