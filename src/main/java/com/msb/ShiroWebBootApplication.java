package com.msb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.msb.mapper")
public class ShiroWebBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroWebBootApplication.class, args);
    }

}
