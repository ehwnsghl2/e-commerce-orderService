package com.brandjunhoe.orderservice.common.code

import org.springframework.http.HttpStatus


enum class ErrorCode(val code: Int, val message: String) {

    // 2XX
    DATA_NOT_FOUND(HttpStatus.NO_CONTENT.value(),"일치하는 데이터가 없습니다"),

    // 4XX
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),"잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "인증 실패하였습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "해당 메서드는 해당되지 않습니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),"지원하지 않는 HTTP 메서드입니다"),
    CONFLICT(HttpStatus.CONFLICT.value(),"이미 사용 중인 데이터 입니다."),

    //5XX
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"서버에러가 발생했습니다. 서버 관리자에게 문의하세요")



}