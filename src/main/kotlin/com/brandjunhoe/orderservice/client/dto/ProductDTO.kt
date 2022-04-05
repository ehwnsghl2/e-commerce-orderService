package com.brandjunhoe.orderservice.client.dto

class ProductDTO(

    val productCode: String,

    val itemCode: String,

    val productImage: String,

    val productName: String,

    val optionName: String,

    val optionValue: String,

    val itemAddPrice: Int,

    val sellingPrice: Int,

    val discountPrice: Int? = null

)