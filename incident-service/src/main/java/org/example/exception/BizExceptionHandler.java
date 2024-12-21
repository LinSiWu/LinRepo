package org.example.exception;

import org.example.common.ResCodeEnum;
import org.example.common.Response;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BizExceptionHandler {
    // handle incident biz exception
    @ExceptionHandler(IncidentException.class)
    public Response<Object> onIncidentException(IncidentException e) {
        return Response.error(e.getCode(), e.getMessage());
    }

    // handle param validation exception
    @ExceptionHandler({ConstraintViolationException.class})
    public Response<Object> onConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return Response.error(ResCodeEnum.RESPONSE_CODE_PARAM_ERROR.getCode(), msgList.toString());
    }

    // handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("%s %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        return Response.error(ResCodeEnum.RESPONSE_CODE_PARAM_ERROR.getCode(), message);
    }
}
