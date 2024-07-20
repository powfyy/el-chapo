package com.el.chapo.exception.handler;

import com.el.chapo.exception.ExceptionResponse;
import com.el.chapo.exception.LogicException;
import com.el.chapo.exception.helper.CommonErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CommonExceptionHandler {
  @Value("${spring.profiles.active:}")
  private String activeProfile;

  @ExceptionHandler(LogicException.class)
  @ResponseBody
  public ResponseEntity<ExceptionResponse> handleLogicException(LogicException e, HttpServletRequest request) {
    log(e);
    return ResponseEntity
      .status(ObjectUtils.defaultIfNull(determineStatus(e), HttpStatus.INTERNAL_SERVER_ERROR))
      .contentType(MediaType.APPLICATION_JSON)
      .body(new ExceptionResponse(e.getCode(),
        Objects.isNull(e.getMessage()) ? "No message available" : e.getMessage(),
        request.getRequestURI()));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
  @ResponseBody
  public ResponseEntity<ExceptionResponse> handleValidationException(Exception ex) {
    StringBuilder errorMessage = new StringBuilder();
    if (ex instanceof MethodArgumentNotValidException exception) {
      exception.getBindingResult().getFieldErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
    } else if (ex instanceof HandlerMethodValidationException exception) {
      exception.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
    } else {
      errorMessage.append(ex.getMessage());
    }
    return new ResponseEntity<>(new ExceptionResponse(CommonErrorCode.VALIDATION_ERROR, errorMessage.toString()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<ExceptionResponse> handleOtherException(Exception e, HttpServletRequest request) {
    log(e);
    return ResponseEntity
      .status(ObjectUtils.defaultIfNull(determineStatus(e), HttpStatus.INTERNAL_SERVER_ERROR))
      .contentType(MediaType.APPLICATION_JSON)
      .body(new ExceptionResponse(CommonErrorCode.JAVA_ERROR,
        Objects.isNull(e.getMessage()) ? "No message available" : e.getMessage(),
        request.getRequestURI()));
  }

  protected HttpStatus determineStatus(Throwable ex) {
    ResponseStatus ann = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
    if (ann != null) {
      return ann.code();
    }

    if (ex instanceof LogicException)
      return ((LogicException) ex).getCode().getStatus();
    return null;
  }

  protected void log(Exception e) {
    if (!StringUtils.containsIgnoreCase(activeProfile, "local")) {
      log.error("{} message: {} stacktrace: {}", e, e.getMessage(), StringUtils.join(e.getStackTrace(), "\n"));
    } else {
      log.error("Exception", e);
    }
  }
}
