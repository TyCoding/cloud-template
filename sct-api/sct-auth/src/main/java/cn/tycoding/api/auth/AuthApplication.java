package cn.tycoding.api.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 身份校验 auth
 *
 * @author tycoding
 * @date 2019-05-21
 */
@EnableResourceServer
@EnableFeignClients("cn.tycoding.api.admin.api.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
