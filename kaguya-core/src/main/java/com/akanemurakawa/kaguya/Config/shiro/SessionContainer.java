package com.akanemurakawa.kaguya.Config.shiro;

import com.akanemurakawa.kaguya.constant.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
@Slf4j
public class SessionContainer {

    public Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    public void settingLanguage(Locale locale){
        getSession().setAttribute(BaseConstant.I18N_LANGUAGE_SESSION, locale);
    }
}
