package com.lamp.decoration.servlet.example.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class DecorationProviderApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(DecorationProviderApplication.class, args);
            log.info("{} 启动成功", DecorationProviderApplication.class.getSimpleName());
        } catch (Exception e) {
        	log.error(e.getMessage() , e);
        }
    }
}