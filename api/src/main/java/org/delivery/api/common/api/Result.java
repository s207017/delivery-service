package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeInterface;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    /**
     * Convenience factory for a success result
     */
    public static Result OK(){
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("request processed successfully")
                .build();
    }

    /**
     * Convenience factory for an error result from a known error code
     * @param errorCodeInterface
     * @return
     */
    public static Result ERROR(ErrorCodeInterface errorCodeInterface){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription("error")
                .build();
    }

    /**
     * Error factory that also incorporates an exception message
     * FIXME: Leaking raw exception messages (tx.getLocalizedMessage())
     * @param errorCodeInterface
     * @param tx
     * @return
     */
    public static Result ERROR(ErrorCodeInterface errorCodeInterface, Throwable tx){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface, String descrption){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription(descrption)
                .build();
    }
}
