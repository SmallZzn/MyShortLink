

package com.zhao.shorturl.aggregation;

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
        "com.zhao.shorturl.admin",
        "com.zhao.shorturl.project"
})
@MapperScan(value = {
        "com.zhao.shorturl.project.dao.mapper",
        "com.zhao.shorturl.admin.dao.mapper"
})
public class AggregationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }
}   