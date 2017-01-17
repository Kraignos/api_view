package com.api.annotation;

import javax.servlet.http.HttpServletRequest;

import com.api.domain.Views;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiViewAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiViewAspect.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    ObjectMapper mapper;

    @Pointcut(value = "execution(@com.api.annotation.ApiView * *.*(..))")
    public void apiViewPointcut() {
        if (request == null) {
            throw new IllegalStateException("No HttpServletRequest could be found.");
        }
    }

    @Around("apiViewPointcut()")
    public Object aroundSecuredApiPointcut(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (request.getUserPrincipal() != null) {
            LOGGER.info("'{} {}' - '{}'", request.getMethod(), request.getRequestURI(), request.getUserPrincipal().getName());
        }
        Object response = joinPoint.proceed();
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        ObjectWriter writer = mapper.writerWithView(Views.resolveFromRole(auth.getAuthorities().iterator().next().getAuthority()));
        /*if (request.isUserInRole(Role.ADMIN.name())) {
            writer = mapper.writerWithView(Views.Admin.class);
        } else if (request.isUserInRole(Role.MANAGER.name())) {
            writer = mapper.writerWithView(Views.Manager.class);
        } else {
            writer = mapper.writerWithView(Views.BasicUser.class);
        }*/
        return writer.writeValueAsString(response);
    }
}
