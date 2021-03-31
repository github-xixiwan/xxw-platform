package com.xxw.platform.waybill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xxw.platform"})
public class WaybillApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaybillApplication.class, args);
    }

}
