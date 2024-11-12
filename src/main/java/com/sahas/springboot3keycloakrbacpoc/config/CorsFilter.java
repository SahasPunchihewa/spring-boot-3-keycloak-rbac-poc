package com.sahas.springboot3keycloakrbacpoc.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.sahas.springboot3keycloakrbacpoc.config.Constants.ORIGIN_HEADER;

public class CorsFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        String origin = "*";
        if (request.getHeader(ORIGIN_HEADER) != null && !request.getHeader(ORIGIN_HEADER).isEmpty())
        {
            origin = request.getHeader(ORIGIN_HEADER);
        }

        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");

        if (!(request.getMethod().equalsIgnoreCase("OPTIONS")))
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}