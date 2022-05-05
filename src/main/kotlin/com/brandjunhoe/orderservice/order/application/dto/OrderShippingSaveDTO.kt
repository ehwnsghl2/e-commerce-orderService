package com.brandjunhoe.orderservice.order.application.dto

import javax.validation.constraints.NotBlank

class OrderShippingSaveDTO(

    @NotBlank
    val receiver: String,

    @NotBlank
    val phone: String,

    @NotBlank
    val postCode: String,

    @NotBlank
    val address: String,

    @NotBlank
    val addressDetail: String

)