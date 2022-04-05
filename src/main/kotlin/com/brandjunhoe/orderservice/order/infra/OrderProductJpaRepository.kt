package com.brandjunhoe.orderservice.order.infra

import com.brandjunhoe.orderservice.order.domain.OrderProduct
import com.brandjunhoe.orderservice.order.domain.OrderProductCode
import com.brandjunhoe.orderservice.order.domain.OrderProductRepository
import org.springframework.data.jpa.repository.JpaRepository


interface OrderProductJpaRepository : JpaRepository<OrderProduct, OrderProductCode>, OrderProductRepository {

}