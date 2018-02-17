package com.politrons.grpc.benchmark.regular;

import io.grpc.stub.StreamObserver;
import rx.Observable;

import static com.politrons.grpc.simple.RpcBenchmark.RpcBenchMarkRequest;
import static com.politrons.grpc.simple.RpcBenchmark.RpcBenchMarkResponse;
import static com.politrons.grpc.simple.RpcBenchmarkServiceGrpc.RpcBenchmarkServiceImplBase;

/**
 * This simple Rpc service implement a connect method defined in their contract(rpc_contract.proto)
 * Where we define that the input request type is RpcRequest, and we response a RpcResponse.
 * All communication between client-server are done by a StreamObserver which implement the three standard callback
 * <p>
 * onNext --> To send data from the server to the client. In case you define just one element to response, you can only send one element in this channel.
 * onError --> To send throwable in case of error from the server.
 * onComplete --> To be invoked once the communication has finish and we want to close the channel from the server side.
 */
public class RpcBenchMarkServiceImpl extends RpcBenchmarkServiceImplBase {

    @Override
    public void benchmark(RpcBenchMarkRequest request, StreamObserver<RpcBenchMarkResponse> responseObserver) {
        responseObserver.onNext(getRpcResponse(request.getAttr()));
        responseObserver.onCompleted();
    }

    private RpcBenchMarkResponse getRpcResponse(String value) {
        return RpcBenchMarkResponse.newBuilder()
                .setValue(value)
                .build();
    }
}