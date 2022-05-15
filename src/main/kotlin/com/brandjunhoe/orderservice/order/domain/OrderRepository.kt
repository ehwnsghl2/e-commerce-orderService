package com.brandjunhoe.orderservice.order.domain

interface OrderRepository {
    fun save(orders: Orders): Orders
    fun findByOrderCode(orderCode: OrderCode): Orders?
}