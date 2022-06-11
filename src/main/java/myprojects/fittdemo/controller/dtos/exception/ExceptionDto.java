package myprojects.fittdemo.controller.dtos.exception;

import lombok.Data;

@Data
public class ExceptionDto {

    private boolean hasException;
    private String message;

    public ExceptionDto(Boolean hasException) {
        setHasException(hasException);
    }

    public ExceptionDto(Boolean hasException, String message) {
        this(hasException);
        setMessage(message);
    }
}
