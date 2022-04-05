package com.brandjunhoe.orderservice.order.application

import com.brandjunhoe.orderservice.common.exception.DataNotFoundException
import com.brandjunhoe.orderservice.order.domain.OrderProductCode
import com.brandjunhoe.orderservice.order.domain.OrderProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderProductService(val orderProductRepository: OrderProductRepository) {


    @Transactional
    fun updatePurchase(orderProductCode: String) {

        val orderProduct = orderProductRepository.findByOrderProductCode(OrderProductCode(orderProductCode))
            ?: throw DataNotFoundException("data not found orderProduct")

        orderProduct.updatePurchase()

        // 적립금 가용 처리


    }

}