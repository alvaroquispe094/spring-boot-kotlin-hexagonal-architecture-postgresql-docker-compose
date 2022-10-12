package com.example.countriesdocker.adapter.persistance.exception


class TimeoutRestClientException(
        errorCode: Int,
        message: String,
        cause: Throwable? = null
) : RestClientGenericException(
        errorCode,
        message,
        cause
)


