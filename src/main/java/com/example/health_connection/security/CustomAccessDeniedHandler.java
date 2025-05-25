package com.example.health_connection.security;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.rmi.ServerException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
                handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}
