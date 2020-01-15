package com.example.study1.web.support;

import com.example.study1.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session){
        User loginUSer = (User) session.getAttribute(USER_SESSION_KEY);
        if (loginUSer == null) {
            return false;
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session){
        if (!isLoginUser(session)){
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
