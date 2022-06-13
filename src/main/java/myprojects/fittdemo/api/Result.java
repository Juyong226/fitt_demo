package myprojects.fittdemo.api;

import lombok.Data;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;

@Data
public class Result<T> {
    private ExceptionDto exception;
    private MessageAndRedirection messageAndRedirection;
    private String message;
    private T result;

    public Result(ExceptionDto exception) {
        setException(exception);
    }

    public Result(ExceptionDto exception, MessageAndRedirection messageAndRedirection) {
        this(exception);
        setMessageAndRedirection(messageAndRedirection);
    }

    public Result(ExceptionDto exception, MessageAndRedirection messageAndRedirection, T result) {
        this(exception, messageAndRedirection);
        setResult(result);
    }
}
