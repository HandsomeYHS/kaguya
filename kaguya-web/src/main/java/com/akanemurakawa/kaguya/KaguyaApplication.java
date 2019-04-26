package com.akanemurakawa.kaguya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KaguyaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KaguyaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KaguyaApplication.class);
    }
}
