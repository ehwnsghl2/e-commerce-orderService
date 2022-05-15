package com.brandjunhoe.orderservice.kafka.pub.enums

enum class MileageTypeEnum(val desc: String) {
    PRODUCT("상품 적립"),
    TEXT_REVIEW("일반 리뷰 적립"),
    PHOTO_REVIEW("포토 리뷰 적립")
}