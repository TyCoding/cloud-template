package cn.tycoding.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.internal.EnableZipkinServer;

/**
 * 服务链路追踪 zipkin
 *
 * @author tycoding
 * @date 2019-05-20
 */
@EnableZipkinServer
@EnableDiscoveryClient
@SpringBootApplication
public class ZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
