package com.lamp.decoration.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;


public class SpringApplicationUtils {

    private static final Logger logger = LoggerFactory.getLogger(SpringApplicationUtils.class);

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {

        try {
            ConfigurableApplicationContext context = SpringApplication.run(primarySource, args);
            logger.info("{} Boot Successful!", primarySource.getSimpleName());
            return context;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(primarySources, args);
            for (Class<?> clazz : primarySources) {
                logger.info("{} Boot Successful!", clazz.getSimpleName());
            }
            return context;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
