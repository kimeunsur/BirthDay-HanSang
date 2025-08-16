package com.hansang.birthday.domain.birthday.exception;

import com.hansang.birthday.global.error.exception.BusinessException;
import com.hansang.birthday.global.error.exception.ErrorCode;

public class BirthdayException extends BusinessException {
    public BirthdayException(ErrorCode errorCode) {
        super(errorCode);
    }
}
