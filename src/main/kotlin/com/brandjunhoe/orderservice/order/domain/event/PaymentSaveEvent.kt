package com.brandjunhoe.orderservice.order.domain.event

import java.math.BigDecimal


class PaymentSaveEvent(
    val orderCode: String,
    val paymentMethod: String,
    val amount: BigDecimal
)