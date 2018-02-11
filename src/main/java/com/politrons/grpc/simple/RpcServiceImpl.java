package com.politrons.grpc.simple;

import io.grpc.stub.StreamObserver;
import rx.Observable;

/**
 * This simple Rpc service implement a connect method defined in their contract(rpc_contract.proto)
 * Where we define that the input request type is RpcRequest, and we response a RpcResponse.
 * All communication between client-server are done by a StreamObserver which implement the three standard callback
 *
 * onNext --> To send data from the server to the client. In case you define just one element to response, you can only send one element in this channel.
 * onError --> To send throwable in case of error from the server.
 * onComplete --> To be invoked once the communication has finish and we want to close the channel from the server side.
 */
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