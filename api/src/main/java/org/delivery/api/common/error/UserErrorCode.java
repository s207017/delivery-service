package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
* For User, use 1000+ error codes
* */

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeInterface{
    //the errorCode is the internal error code, it is not the code that's sent to the client
    OK(400, 1404, "Cannot find user");

    private final Integer HttpStatusCode;
    private final Integer errorCode;
    private final String description;
}
