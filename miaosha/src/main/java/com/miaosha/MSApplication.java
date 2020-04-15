package com.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @date: 2019\11\21 0021
 * @author: zhaijh
 * @description:
 */
@SpringBootApplication
@MapperScan("com.miaosha.dao")
public class MSApplication {
    public static void main(String[] args) {
        SpringApplication.run(MSApplication.class,args);
    }
}
