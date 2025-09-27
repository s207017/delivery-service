package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url: {}", request.getRequestURI());

        //For web (chrome), it sends a request prior to the actual request to check if method exists
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        //pass if js, html, png resources are being requested
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // TODO authorize header


        return true;
    }
}
