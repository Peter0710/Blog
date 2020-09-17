package com.leo.star;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Liu
 */
@EnableRedisHttpSession
@EnableSwagger2
@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class StarApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarApplication.class, args);
    }

}
