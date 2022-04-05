package com.brandjunhoe.orderservice.order.application.dto

import javax.validation.constraints.NotBlank

class OrderPaymentSaveDTO(

    @NotBlank
    val paymentType: String

)