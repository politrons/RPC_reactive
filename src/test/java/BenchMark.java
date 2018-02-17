import com.politrons.grpc.benchmark.regular.RpcBenchMarkClient;
import com.politrons.grpc.benchmark.regular.RpcBenchMarkServer;
import org.junit.Test;

import java.io.IOException;

public class BenchMark {


    @Test
    public void RpcVsRest() throws IOException {
        RpcBenchMarkServer.start();
        long start = System.currentTimeMillis();
        RpcBenchMarkClient.run();
        System.out.println("RPC average response time:" + ((System.currentTimeMillis() - start)/1000) + " seconds");
        MyGrizzlyServer.start();
        start = System.currentTimeMillis();
        HttpClient.run();
        System.out.println("Rest average response time:" + ((System.currentTimeMillis() - start)/1000) + " seconds");

    }

}
