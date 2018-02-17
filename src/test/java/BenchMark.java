import com.politrons.grpc.benchmark.reactive.ReactiveBenchmarkClient;
import com.politrons.grpc.benchmark.reactive.ReactiveBenchmarkServer;
import com.politrons.grpc.benchmark.regular.RpcBenchMarkClient;
import com.politrons.grpc.benchmark.regular.RpcBenchMarkServer;
import org.junit.Test;

import java.io.IOException;

public class BenchMark {


    @Test
    public void RpcVsRest() throws IOException, InterruptedException {
        MyGrizzlyServer.start();
        long start = System.currentTimeMillis();
        HttpClient.run();
        System.out.println("Rest response time:" + ((System.currentTimeMillis() - start) ) + " millis");
        RpcBenchMarkServer.start();
        start = System.currentTimeMillis();
        RpcBenchMarkClient.run();
        System.out.println("RPC regular response time:" + ((System.currentTimeMillis() - start) ) + " millis");
        ReactiveBenchmarkServer.start();
        start = System.currentTimeMillis();
        ReactiveBenchmarkClient.run();
        System.out.println("RPC Reactive response time:" + ((System.currentTimeMillis() - start)) + " millis");

    }

}
