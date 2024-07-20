package com.el.chapo.exception.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
  JAVA_ERROR("common.java.error", HttpStatus.INTERNAL_SERVER_ERROR),
  NOT_FOUND("common.object.not.exists", HttpStatus.NOT_FOUND),
  FORBIDDEN("incorrect.auth_header_passing", HttpStatus.FORBIDDEN),
  UNAUTHORIZED("unauthorized", HttpStatus.UNAUTHORIZED),
  VALIDATION_ERROR("validation.error", HttpStatus.BAD_REQUEST);


  private final String key;
  private final HttpStatus status;

  public String toString() {
    return this.key;
  }
}
