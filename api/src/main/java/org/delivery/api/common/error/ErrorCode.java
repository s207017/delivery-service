package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeInterface{
    //the errorCode is the internal error code, it is not the code that's sent to the client
    OK(200, 200, "Success"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "Bad Request"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "Internal server error"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null pointer");

    private final Integer HttpStatusCode;
    private final Integer errorCode;
    private final String description;
}
