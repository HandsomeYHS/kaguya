package com.akanemurakawa.kaguya.Config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@Slf4j
public class ShiroCasConfiguration {


    @Bean(name = "shiroCasRealm")
    public ShiroCasRealm shiroCasRealm(){
        return null;
    }
}


