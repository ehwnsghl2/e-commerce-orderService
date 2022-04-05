package com.brandjunhoe.orderservice.order.application.dto

class OrderProductDTO(

    val orderProductCode: String,

    val productCode: String,

    var productImage: String,

    var productName: String,

    var optionName: String,

    var optionValue: String,

    val orderDatetime: String,

    val amount: Int,

    val quantity: Int,

    val state: String

    // 리뷰 작성 여부

) {

    // 상품정보 업데이트
    fun productUpdate(productImage: String,
                      productName: String,
                      optionName: String,
                      optionValue: String) {
        this.productImage = productImage
        this.productName = productName
        this.optionName = optionName
        this.optionValue = optionValue

    }
}
