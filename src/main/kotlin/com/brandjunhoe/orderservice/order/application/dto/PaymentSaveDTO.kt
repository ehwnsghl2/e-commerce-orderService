package com.brandjunhoe.orderservice.order.application.dto

import javax.validation.constraints.NotBlank

class PaymentSaveDTO(

    @NotBlank
    val paymentMethod: String

)