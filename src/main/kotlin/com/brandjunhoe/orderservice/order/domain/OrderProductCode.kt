package com.brandjunhoe.orderservice.order.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * Create by DJH on 2022/03/27.
 */
@Embeddable
class OrderProductCode(

    @Column(name = "order_product_code")
    val orderProductCode: String

) : Serializable