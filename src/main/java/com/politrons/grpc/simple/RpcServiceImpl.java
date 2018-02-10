package com.politrons.grpc.simple;

import io.grpc.stub.StreamObserver;
import rx.Observable;

public class RpcServiceImpl extends RpcServiceGrpc.RpcServiceImplBase {

    @Override
    public void connect(RpcContract.RpcRequest request, StreamObserver<RpcContract.RpcResponse> responseObserver) {
        Observable.just(request.getAttr())
                .map(String::toUpperCase)
                .subscribe(value -> responseObserver.onNext(getRpcResponse(value)),
                        responseObserver::onError,
                        responseObserver::onCompleted);
    }

    private RpcContract.RpcResponse getRpcResponse(String value) {
        return RpcContract.RpcResponse.newBuilder()
                .setValue(value)
                .build();
    }
}