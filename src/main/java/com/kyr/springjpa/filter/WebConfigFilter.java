package com.kyr.springjpa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebConfigFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        log.info("do! Filter Encoding -> {} , ContentType -> {} , ContextPath -> {} , "
                , servletRequest.getCharacterEncoding()
                , servletRequest.getContentType()
                , servletRequest.getServletContext().getContextPath());


        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;

        httpResp.setHeader("Access-Control-Allow-Origin", "*"); //허용대상 도메인
        httpResp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
        httpResp.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");

        chain.doFilter(servletRequest, servletResponse);
    }
}

