package com.zy.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: zy
 * @Date: 20190331 19:49
 * @Description:
 */
@Service("authenticationFailHandler")
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)throws
            IOException, ServletException {

        this.returnJson(response,exception);

    }

    private void returnJson(HttpServletResponse response,
                           AuthenticationException exception)throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println("{\"exceptionId\":\"null\",\"messageCode\":\"401\"," +
                "\"message\": \""+ exception.getMessage() +"\",\"serverTime\": " + System.currentTimeMillis() +"}");

    }

}