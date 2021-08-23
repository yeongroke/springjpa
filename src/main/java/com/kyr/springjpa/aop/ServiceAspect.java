package com.kyr.springjpa.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ServiceAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    @Around("execution(* com.kyr.springjpa.service.*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        long start = System.currentTimeMillis();
        try{
            return pjp.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
            logger.info("Doing Time - " + timeMs/1000 + " Second");
        }
    }

    @Around("execution(* com.kyr.springjpa.controller.*.*(..))")
    public Object AroundController(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String controllerName = pjp.getSignature().getDeclaringType().getName();
        String methodName = pjp.getSignature().getName();
        Map<String,Object> logsParams = new HashMap<>();
        try{
            logsParams.put("controller" , controllerName);
            logsParams.put("method" , methodName);
            logsParams.put("log_Time" , LocalDateTime.now());
            logsParams.put("reqeust_Url" , request.getRequestURI());
            logsParams.put("Http_Method" , request.getMethod());
        } catch(Exception e){
            log.error("Controller Aspect", e);
        }
        log.info("Controller Log -> {}" , logsParams);
        Object result = pjp.proceed();

        return result;
    }
}
