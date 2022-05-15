package com.brandjunhoe.orderservice.order.domain.enums

enum class OrderProductStateEnum(val description: String) {
    PAYMENT_COMPLETE("결제 완료"),
    PRODUCT_READY("상품 준비"),
    PURCHASE_CONFIRM("구매 확정"),
    CANCEL_AFTER_PAYMENT("결제 취소"),
    CANCEL_BEFORE_ADMIN("결제전 취소 (관리자)"),
    CANCEL_BEFORE_SYSTEM("결제전 취소 (시스템)"),
    CANCEL_BEFORE_USER("결제전 취소 (사용자)"),
    EXCHANGE_COMPLETE("교환 완료"),
    PAYMENT_READY("결제 준비"),
    RETURN_COMPLETE("반품 완료"),
    SHIPPING_READY("배송 준비"),
    SHIPPING("배송 중"),
    SHIPPING_COMPLETE("배송 완료"),
    SHIPPING_HOLD("배송 보류")
}


