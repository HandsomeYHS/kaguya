package com.akanemurakawa.kaguya.Config.shiro;

import com.akanemurakawa.kaguya.constant.BaseConstant;
import com.akanemurakawa.kaguya.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@EnableAutoConfiguration
@Slf4j
public class ShiroCasRealm extends CasRealm {

    @Resource
    private SessionContainer sessionContainer;

    /**
     * login authentication
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthenticationInfo info = super.doGetAuthenticationInfo(token);

        if (info != null) {
            Session session = sessionContainer.getSession();
            if (StringUtils.isEmpty(session.getAttribute(BaseConstant.I18N_LANGUAGE_SESSION))){
                sessionContainer.settingLanguage(LocaleContextHolder.getLocale());
            }

            // set Session
            session.setAttribute(BaseConstant.LOGIN_USER_SESSION, info.getPrincipals().getPrimaryPrincipal().toString());
            User user = (User) info.getPrincipals().asList().get(0);
            // set Session
            session.setAttribute(BaseConstant.LOGIN_USER_SESSION, user);
        }
        return info;
    }

    /**
     * authority authentication, grant role and permission to the current login Subject.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo;
        Session session = sessionContainer.getSession();
        if (session.getAttribute(BaseConstant.LOGIN_AUTH_INFO_SESSION) != null){
            authorizationInfo = (SimpleAuthorizationInfo) session.getAttribute(BaseConstant.LOGIN_AUTH_INFO_SESSION);
            return authorizationInfo;
        }

        authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principals.asList().get(0);
        if (user != null){
            Set<String> roles = new HashSet<>();
            roles.add(BaseConstant.USER_ROLE);
            authorizationInfo.setRoles(roles);
            authorizationInfo.addStringPermission(BaseConstant.USER_PERMISSION);

            // set Session
            session.setAttribute(BaseConstant.LOGIN_AUTH_INFO_SESSION, authorizationInfo);
        }else {
            authorizationInfo.addStringPermission(BaseConstant.USER_PERMISSION_AVAILABLE);
        }
        return authorizationInfo;
    }
}
