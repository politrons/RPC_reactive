package com.politrons.grpc.simple;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RpcClient {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ManagedChannel channel = getManagedChannel();
        RpcServiceGrpc.RpcServiceFutureStub stub = getRpcServiceStub(channel);
        ListenableFuture<RpcContract.RpcResponse> future =
                stub.connect(RpcContract.RpcRequest.newBuilder()
                        .setAttr("hello world")
                        .build());
        RpcContract.RpcResponse response = future.get(10, TimeUnit.SECONDS);
        System.out.println(response.getValue());
        channel.shutdown();
    }

    /**
     * From the contract of the proto we create the BlockingStup in this case there´re other strategies as Async communication.
     */
    private static RpcServiceGrpc.RpcServiceFutureStub getRpcServiceStub(ManagedChannel channel) {
        return RpcServiceGrpc.newFutureStub(channel);
    }

    /**
     * ManagedChannel is communication channel for the RPC
     */
    private static ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9999)
                .usePlaintext(true)
                .build();
    }
}