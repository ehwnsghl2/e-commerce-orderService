package com.brandjunhoe.orderservice.order.domain

interface OrderProductRepository {

    fun findByOrderProductCode(orderProductCode: OrderProductCode): OrderProduct?

}