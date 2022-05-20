package com.brandjunhoe.orderservice.order.domain.event


class ProductItemQuantityUpdateEvent(
    val productCode: String,
    val itemCode: String,
    val quantity: Int
)