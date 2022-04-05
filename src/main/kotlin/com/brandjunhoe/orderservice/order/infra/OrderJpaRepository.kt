package com.brandjunhoe.orderservice.order.infra

import com.brandjunhoe.orderservice.order.domain.Orders
import com.brandjunhoe.orderservice.order.domain.OrderCode
import com.brandjunhoe.orderservice.order.domain.OrderRepository
import org.springframework.data.jpa.repository.JpaRepository


interface OrderJpaRepository : JpaRepository<Orders, OrderCode>, OrderRepository {

}