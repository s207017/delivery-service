package org.delivery.api.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order
public class GlobalExceptionHandler {
    //Last line of defense to catch all the general/uncaught errors
    //that were not caught by specific exception handlers
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(
            Exception exception
    ){
        log.error("",exception);
        return ResponseEntity
                .status(500)
                .body(
                        Api.ERROR(ErrorCode.SERVER_ERROR)
                );
    }
}
