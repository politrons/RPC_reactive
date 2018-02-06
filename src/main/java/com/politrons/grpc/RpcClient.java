package com.politrons.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = getManagedChannel();
        RpcServiceGrpc.RpcServiceBlockingStub stub = getRpcServiceBlockingStub(channel);
        IntStream.of(5).forEach(index -> {
            RpcResponse response =
                    stub.connect(RpcRequest.newBuilder()
                            .setAttr(index)
                            .setAttr2(1)
                            .build());
            System.out.println(response.getValue());
        });
        channel.shutdown();
    }

    /**
     * From the contract of the proto we create the BlockingStup in this case there´re other strategies as Async communication.
     */
    private static RpcServiceGrpc.RpcServiceBlockingStub getRpcServiceBlockingStub(ManagedChannel channel) {
        return RpcServiceGrpc.newBlockingStub(channel);
    }

    /**
     * ManagedChannel is communication channel for the RPC
     */
    private static ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext(true)
                .build();
    }
}