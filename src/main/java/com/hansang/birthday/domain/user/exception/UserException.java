package com.hansang.birthday.domain.user.exception;

import com.hansang.birthday.global.error.exception.BusinessException;
import com.hansang.birthday.global.error.exception.ErrorCode;

public class UserException extends BusinessException {
  public UserException(ErrorCode errorCode) {
    super(errorCode);
  }
}
