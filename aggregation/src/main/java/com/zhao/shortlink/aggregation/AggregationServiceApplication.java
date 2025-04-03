

package com.zhao.shortlink.aggregation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 短链接聚合应用
 * 小赵
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.zhao.shortlink.admin",
        "com.zhao.shortlink.project"
})
@MapperScan(value = {
        "com.zhao.shortlink.project.dao.mapper",
        "com.zhao.shortlink.admin.dao.mapper"
})
public class AggregationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }
}   