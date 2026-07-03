package com.conectacampo.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error"),
                new ErrorPage(HttpStatus.FORBIDDEN, "/error"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"),
                new ErrorPage(HttpStatus.BAD_REQUEST, "/error"),
                new ErrorPage(HttpStatus.UNAUTHORIZED, "/error"),
                new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error"),
                new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/error")
        );
    }
}