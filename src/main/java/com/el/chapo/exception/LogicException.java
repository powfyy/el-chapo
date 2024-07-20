package com.el.chapo.exception;

import com.el.chapo.exception.helper.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogicException extends RuntimeException {

  private ErrorCode code;
  private String message;
  private String exceptionString;

  public LogicException(ErrorCode code, Exception exception) {
    super(code.getKey());
    this.code = code;
    this.message = exception.getMessage();
    this.exceptionString = exception.toString();
  }

  public String toString() {
    return "LogicException{code=" + this.code + ", exceptionString=" + exceptionString + ", message=" + message + "}";
  }

}
