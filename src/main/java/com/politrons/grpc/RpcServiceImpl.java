package com.politrons.grpc;

import io.grpc.stub.StreamObserver;
import rx.Observable;

import java.util.UUID;

public class RpcServiceImpl extends RpcServiceGrpc.RpcServiceImplBase {

    @Override
    public void connect(RpcRequest request, StreamObserver<RpcResponse> responseObserver) {
        Observable.range(request.getAttr(), request.getAttr2())
                .map(value -> value * 10)
                .subscribe(value -> responseObserver.onNext(getRpcResponse(request)),
                        responseObserver::onError,
                        responseObserver::onCompleted);

    }

    private RpcResponse getRpcResponse(RpcRequest request) {
        return RpcResponse.newBuilder()
                .setValue(String.valueOf(request.getAttr() * request.getAttr2()))
                .build();
    }
}