package com.brandjunhoe.orderservice.order.application.dto

import javax.validation.constraints.NotBlank

class OrderProductSaveDTO(

    @NotBlank
    val productCode: String,

    @NotBlank
    val itemCode: String,

    @NotBlank
    val quantity: Int

)