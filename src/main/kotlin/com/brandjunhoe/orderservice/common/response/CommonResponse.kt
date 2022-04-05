package com.brandjunhoe.orderservice.common.response

import org.springframework.http.HttpStatus

data class CommonResponse<T>(
        val code: Int,
        val message: String,
        var data: T?
) {

    constructor() : this(HttpStatus.OK.value(), HttpStatus.OK.reasonPhrase, null)

    constructor(content: T) : this(HttpStatus.OK.value(), HttpStatus.OK.reasonPhrase, content)

    constructor(httpStatus: HttpStatus, message: String? = null) : this(httpStatus.value(), message
            ?: httpStatus.reasonPhrase, null)

    constructor(code: Int, message: String) : this(code, message, null)

    constructor(httpStatus: HttpStatus, content: T) : this(httpStatus.value(), httpStatus.reasonPhrase, content)

}