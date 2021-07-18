package com.gabia.slack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SlackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackApplication.class, args);
    }

}
