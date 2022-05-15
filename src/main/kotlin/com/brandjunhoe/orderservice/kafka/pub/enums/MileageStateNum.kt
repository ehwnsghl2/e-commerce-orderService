package com.brandjunhoe.orderservice.kafka.pub.enums

enum class MileageStateNum(val desc: String) {
    READY("예정"),
    SAVE("적립"),
    CANCEL("취소")
}