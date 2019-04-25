package com.akanemurakawa.kaguya.Config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SessionContainer {

    public Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }
}
