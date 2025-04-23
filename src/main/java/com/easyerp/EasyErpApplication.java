package com.easyerp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.easyerp")
@MapperScan("com.easyerp.mappers")
@EnableTransactionManagement()
public class EasyErpApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyErpApplication.class, args);
    }
}
