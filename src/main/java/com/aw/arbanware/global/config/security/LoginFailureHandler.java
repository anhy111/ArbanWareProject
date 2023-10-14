package com.aw.arbanware.global.config.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public LoginFailureHandler(String defaultUrl) {
        setDefaultFailureUrl(defaultUrl);
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        LoginFailureMessage message;

        if (exception instanceof BadCredentialsException) {
            message = LoginFailureMessage.BAD_CREDENTIALS;
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = LoginFailureMessage.SERVICE_EXCEPTION;
        } else if (exception instanceof UsernameNotFoundException) {
            message = LoginFailureMessage.USERNAME_NOT_FOUND;
        } else {
            message = LoginFailureMessage.UNKNOWN;
        }

        setDefaultFailureUrl("/login?error=true&message=" + message);
        super.onAuthenticationFailure(request, response, exception);
    }
}
