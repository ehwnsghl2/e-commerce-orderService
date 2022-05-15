package com.brandjunhoe.orderservice.order.domain.event

import com.brandjunhoe.orderservice.kafka.pub.enums.MileageStateNum
import com.brandjunhoe.orderservice.kafka.pub.enums.MileageTypeEnum
import java.util.*


class MileageSaveEvent(
    val usrId: UUID,
    val orderCode: String,
    val orderProductCode: String,
    val mileageType: MileageTypeEnum,
    val mileageState: MileageStateNum,
    val amount: Int? = null
)