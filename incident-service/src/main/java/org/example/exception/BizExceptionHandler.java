package org.example.exception;

import org.example.common.ResCodeEnum;
import org.example.common.ResponseDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class BizExceptionHandler {
    // handle incident biz exception
    @ExceptionHandler(IncidentException.class)
    public ResponseDTO<Object> onIncidentException(IncidentException e) {
        return ResponseDTO.error(e.getCode(), e.getMessage());
    }

    // handle param validation exception
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseDTO<Object> onConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return ResponseDTO.error(ResCodeEnum.RESPONSE_CODE_PARAM_ERROR.getCode(), msgList.toString());
    }
}
