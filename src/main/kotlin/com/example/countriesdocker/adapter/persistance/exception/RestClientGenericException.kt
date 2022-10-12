package com.example.countriesdocker.adapter.persistance.exception

import com.example.countriesdocker.config.exception.GenericException


open class RestClientGenericException(
        errorCode: Int,
        message: String,
        cause: Throwable? = null
) : GenericException(
        errorCode,
        message,
        cause
)
