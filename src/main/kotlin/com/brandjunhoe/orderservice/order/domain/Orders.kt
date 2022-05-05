package com.brandjunhoe.orderservice.order.domain

import com.brandjunhoe.orderservice.common.domain.DateDeleteColumnEntity
import com.brandjunhoe.orderservice.order.domain.enums.DeviceTypeEnum
import org.hibernate.annotations.Where
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
@Where(clause = "deldate IS NOT NULL")
class Orders(

    @EmbeddedId
    val orderCode: OrderCode,

    @Column(name = "usr_id", nullable = false)
    val usrId: UUID,

    @Column(name = "orderer", length = 45, nullable = false)
    val orderer: String,

    @Column(name = "email", length = 255, nullable = false)
    val email: String,

    @Column(name = "phone", length = 11, nullable = false)
    val phone: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", columnDefinition = "enum", nullable = false)
    var deviceType: DeviceTypeEnum,

    @Column(name = "total_order_amount")
    val totalOrderAmount: BigDecimal? = null,

    @Column(name = "total_shipping_amount")
    val totalShippingAmount: BigDecimal? = null,

    @Column(name = "total_sale_amount")
    val totalSaleAmount: BigDecimal? = null,

    @Column(name = "total_payment_amount")
    val totalPaymentAmount: BigDecimal? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "orderCode", nullable = false)
    val orderProducts: List<OrderProduct?>

) : DateDeleteColumnEntity() {

}