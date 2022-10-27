package com.example.countriesdocker.config.exception

open class BadArgumentException(
        errorCode: Int,
        message: String,
        cause: Throwable? = null
) : GenericException(
        errorCode,
        message,
        cause
)
