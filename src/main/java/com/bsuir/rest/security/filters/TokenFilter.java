package com.bsuir.rest.security.filters;

import com.bsuir.rest.security.authentications.TokenAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("tokenValue");

        TokenAuthentication tokenAuthentication = new TokenAuthentication(token);

        if(token != null) {
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        } else {
            tokenAuthentication.setAuthenticated(false);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}