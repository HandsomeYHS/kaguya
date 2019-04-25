package com.akanemurakawa.kaguya.Config;

import com.akanemurakawa.kaguya.Config.shiro.SessionContainer;
import com.akanemurakawa.kaguya.constant.BaseConstant;
import org.apache.shiro.session.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class I18nLocaleResolver implements LocaleResolver {

    @Resource
    private SessionContainer sessionContainer;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lan = request.getParameter("lan");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(lan)){
            String[] split = lan.split("_");
            locale = new Locale(split[1], split[2]);
            Session session = sessionContainer.getSession();
            session.setAttribute(BaseConstant.I18N_LANGUAGE_SESSION, locale);

        }else{
            Session session = sessionContainer.getSession();
            Locale localeSession = (Locale) session.getAttribute(BaseConstant.I18N_LANGUAGE_SESSION);
            if (localeSession != null){
                return localeSession;
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }

    @Bean
    public LocaleResolver getLocaleResolver(){
        return new I18nLocaleResolver();
    }
}
