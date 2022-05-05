package com.brandjunhoe.orderservice.order.domain

import com.brandjunhoe.orderservice.common.domain.DateColumnEntity
import com.brandjunhoe.orderservice.common.domain.DateDeleteColumnEntity
import com.brandjunhoe.orderservice.order.domain.enums.DeviceTypeEnum
import com.brandjunhoe.orderservice.order.domain.enums.OrderProductStateEnum
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Where
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "order_product")
class OrderProduct(

    @EmbeddedId
    val orderProductCode: OrderProductCode,

    @Column(name = "product_code", length = 255, nullable = false)
    val productCode: String,

    @Column(name = "itemCode", length = 255, nullable = false)
    val itemCode: String,

    @Column(name = "product_name", length = 255, nullable = false)
    val productName: String,

    @Column(name = "option_name", length = 255, nullable = false)
    val optionName: String,

    @Column(name = "option_value", length = 255, nullable = false)
    val optionValue: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "enum", nullable = false)
    var state: OrderProductStateEnum,

    @Column(name = "selling_price", nullable = false)
    val sellingPrice: Int,

    @Column(name = "discount_price", nullable = false)
    val discountPrice: Int,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "mileage", nullable = false)
    val mileage: Int,

    @Column(name = "purchase_confirm_date")
    var purchaseConfirmDate: Date = Date(),

    @Column(name = "memo")
    val memo: String? = null

) : DateColumnEntity() {

    fun updatePurchase() {
        this.state = OrderProductStateEnum.PURCHASE_CONFIRM
        this.purchaseConfirmDate = Date()
    }

}