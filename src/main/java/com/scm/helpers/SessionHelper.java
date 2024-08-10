package com.scm.helpers;

import org.aspectj.bridge.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public static void removeMessage(){
        try {
            HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("Message");
        } catch (Exception e) {
            System.out.println("Error in sesssion helper " + e);
            e.printStackTrace();
        }
    }
}
