package com.brandjunhoe.orderservice.order.domain.event


class ShippingSaveEvent(
    val orderCode: String,
    val receiver: String,
    val phone: String,
    val postCode: String,
    val address: String,
    val addressDetail: String
)