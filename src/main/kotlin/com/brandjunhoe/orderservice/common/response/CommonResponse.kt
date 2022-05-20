package com.brandjunhoe.orderservice.common.response

import org.springframework.http.HttpStatus

data class CommonResponse<T>(
    val status: Int,
    val code: Int,
    val message: String,
    var data: T?
) {

    constructor() : this(HttpStatus.OK.value(), HttpStatus.OK.value(), HttpStatus.OK.reasonPhrase, null)

    constructor(content: T) : this(HttpStatus.OK.value(), HttpStatus.OK.value(), HttpStatus.OK.reasonPhrase, content)


    constructor(httpStatus: HttpStatus, message: String? = null) : this(
        httpStatus.value(), httpStatus.value(), message
            ?: httpStatus.reasonPhrase, null
    )

    constructor(httpStatus: HttpStatus, code: Int, message: String? = null) : this(
        httpStatus.value(), code, message
            ?: httpStatus.reasonPhrase, null
    )

    constructor(status: Int, code: Int, message: String) : this(status, code, message, null)

    constructor(httpStatus: HttpStatus, code: Int, content: T) : this(
        httpStatus.value(),
        code,
        httpStatus.reasonPhrase,
        content
    )
}