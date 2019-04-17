package com.akanemurakawa.kaguya.util;

import com.akanemurakawa.kaguya.constant.BaseConstant;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听器，统计在线人数
 */
@WebListener
public class ContextListenerUtils implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public ContextListenerUtils() {
    }

	/**
	 * +1访问的时候增加
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         HttpSession httpSession = se.getSession();
         ServletContext servletContext = httpSession.getServletContext();
         Integer onlineUser = (Integer)servletContext.getAttribute(BaseConstant.SESSION_ON_LINE_USER);
         if(onlineUser == null) {
        	 onlineUser = 1;
         }else {
        	 onlineUser++;
         }
         servletContext.setAttribute(BaseConstant.SESSION_ON_LINE_USER, onlineUser);
    }

	/**
	 * -1退出的时候销毁
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	HttpSession httpSession = se.getSession();
		ServletContext servletContext = httpSession.getServletContext();
		Integer onlineUser = (Integer)servletContext.getAttribute(BaseConstant.SESSION_ON_LINE_USER);
		if(onlineUser == null){
			onlineUser = 0;
		}else{
			onlineUser--;
		}
		servletContext.setAttribute(BaseConstant.SESSION_ON_LINE_USER,onlineUser);
    }
}
