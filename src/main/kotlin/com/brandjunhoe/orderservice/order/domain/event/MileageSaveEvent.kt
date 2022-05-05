package com.brandjunhoe.orderservice.order.domain.event

import java.math.BigDecimal
import java.util.*


class MileageSaveEvent(
    val usrId: UUID,
    val orderProductCode: String,
    val amount: Int
)