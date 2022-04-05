package com.brandjunhoe.orderservice.common.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

/**
 * Exception 커스텀 예외 클래스 설계
 * https://jsonobject.tistory.com/501
 *
 * 사전에 개발자가 의도한 예외를 설계할 차례이다. 오류 코드, 오류 메시지, HTTP 응답 상태 코드 3개를 담도록 설계했다
 * 개발자는 비지니스 로직에서 적절한 오류 코드를 담아 이 예외를 발생시키면, 아래 설명할 @ControllerAdvice가 적절한 오류 응답을 하게 된다
 *
 * Created by KMS on 2021/05/09.
 */
open class CommonException(message: String) : RuntimeException(message) {

}