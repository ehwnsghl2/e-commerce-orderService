package com.brandjunhoe.orderservice.order.presentation.dto

import com.brandjunhoe.orderservice.client.dto.ProductDTO
import com.brandjunhoe.orderservice.client.dto.UserDTO
import com.brandjunhoe.orderservice.common.exception.DataNotFoundException
import com.brandjunhoe.orderservice.common.generator.CodeGenerator
import com.brandjunhoe.orderservice.common.utils.rate
import com.brandjunhoe.orderservice.order.application.dto.PaymentSaveDTO
import com.brandjunhoe.orderservice.order.application.dto.OrderProductSaveDTO
import com.brandjunhoe.orderservice.order.application.dto.OrderShippingSaveDTO
import com.brandjunhoe.orderservice.order.domain.OrderCode
import com.brandjunhoe.orderservice.order.domain.OrderProduct
import com.brandjunhoe.orderservice.order.domain.OrderProductCode
import com.brandjunhoe.orderservice.order.domain.Orders
import com.brandjunhoe.orderservice.order.domain.enums.DeviceTypeEnum
import com.brandjunhoe.orderservice.order.domain.enums.OrderProductStateEnum
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
    val payment: PaymentSaveDTO

) {

    fun toEntity(user: UserDTO, product: List<ProductDTO>): Orders = Orders(
        OrderCode(CodeGenerator.RANDOM("OC")), usrId, user.name, user.email, shipping.phone, DeviceTypeEnum.PC,
        orderProduct(user, product)
    )


    private fun orderProduct(user: UserDTO, findProduct: List<ProductDTO>): List<OrderProduct> =
        products.map { product ->
            findProduct.find { it.productCode == product.productCode }?.let {
                val sellingPrice = it.sellingPrice.plus(it.itemAddPrice)
                val discountPrice = rate(sellingPrice, user.grade.discountRate).unaryMinus()
                val price = sellingPrice.plus(discountPrice)

                OrderProduct(
                    OrderProductCode(CodeGenerator.RANDOM("OP")),
                    it.productCode,
                    it.itemCode,
                    it.productName,
                    it.optionName,
                    it.optionValue,
                    OrderProductStateEnum.PAYMENT_READY,
                    sellingPrice,
                    discountPrice,
                    product.quantity,
                    sellingPrice.plus(discountPrice),
                    rate(price, user.grade.mileageSaveRate)
                )
            } ?: throw DataNotFoundException("product not found")
        }
}