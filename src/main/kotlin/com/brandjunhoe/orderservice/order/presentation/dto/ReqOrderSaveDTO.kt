package com.brandjunhoe.orderservice.order.presentation.dto

import com.brandjunhoe.orderservice.order.application.dto.OrderPaymentSaveDTO
import com.brandjunhoe.orderservice.order.application.dto.OrderProductSaveDTO
import com.brandjunhoe.orderservice.order.application.dto.OrderShippingSaveDTO
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ReqOrderSaveDTO(


    @NotBlank
    val usrId: UUID,

    @NotBlank
    val orderer: String,

    @NotBlank
    val email: String,

    @NotEmpty
    val products: List<OrderProductSaveDTO>,

    @NotNull
    val shipping: OrderShippingSaveDTO,

    @NotNull
    val paymentMethod: OrderPaymentSaveDTO

)