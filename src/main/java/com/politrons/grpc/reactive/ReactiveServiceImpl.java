package com.politrons.grpc.reactive;

import io.grpc.stub.StreamObserver;

public class ReactiveServiceImpl extends ReactiveServiceGrpc.ReactiveServiceImplBase {

    @Override
    public StreamObserver<ReactiveRequest> streamCall(StreamObserver<ReactiveResponse> responseObserver) {
        return new StreamObserver<ReactiveRequest>() {

            @Override
            public void onNext(ReactiveRequest value) {
                System.out.println("value: " + value.getAttr());
                ReactiveResponse response = ReactiveResponse.newBuilder()
                        .setValue(value.getAttr().toUpperCase())
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}