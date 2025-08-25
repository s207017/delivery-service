package org.delivery.api.filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req =  new ContentCachingRequestWrapper( (HttpServletRequest) servletRequest);
        var res = new ContentCachingResponseWrapper( (HttpServletResponse) servletResponse);

        filterChain.doFilter(req, res); //this marks the point where filter runs

        //request information
        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            var headerValue = req.getHeader(headerKey);

            // authorization-token : ???, user-agent : ???
            headerValues.append(headerKey).append(" : ").append(" , ");
        });

        var requestBody = new String(req.getContentAsByteArray());
        var uri = req.getRequestURI();
        var method = req.getMethod();

        log.info(">>>>> uri: {}, method : {}, header : {}, body : {}", uri, method, headerValues, requestBody);

        //response information
        var responseHeaderValues = new StringBuilder();
        res.getHeaderNames().forEach(headerKey -> {
            var headerValue = res.getHeader(headerKey);

            responseHeaderValues.append(headerKey).append(" : ").append(" , ");
        });

        var responseBody = new String(res.getContentAsByteArray());
        log.info("<<<<< uri : {}, method: {}, header: {}, body: {}", uri, method, responseHeaderValues, responseBody);

        //without the copyBodyToResponse(), the response leaving filter will be empty
        //since we read it once above
        res.copyBodyToResponse();
    }
}
