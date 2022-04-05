package com.brandjunhoe.orderservice.order.application

import com.brandjunhoe.orderservice.client.ProductCustomClient
import com.brandjunhoe.orderservice.client.UserCustomClient
import com.brandjunhoe.orderservice.client.dto.ProductDTO
import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO
import com.brandjunhoe.orderservice.order.domain.OrderCustomRepository
import com.brandjunhoe.orderservice.order.domain.OrderRepository
import com.brandjunhoe.orderservice.order.presentation.dto.ReqOrderSaveDTO
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class OrderService(private val orderRepository: OrderRepository,
                   private val orderCustomRepository: OrderCustomRepository,
                   private val userCustomClient: UserCustomClient,
                   private val productCustomClient: ProductCustomClient) {


    @Transactional
    fun save(request: ReqOrderSaveDTO) {


        // 상품 가격
        val products = productCustomClient.findProduct(
            request.products.map { it.productCode }
        )


        // 할인 (상품 할인, 등급 할인)
        val user = userCustomClient.findUser(request.usrId)


        // 배송 정책o
        // 단독 배송 상품/묶음 배송 상품
        // 배송비
        // 마일리지 정책
        // 마일리지 금액


        // 주문서 생성

        // 결제

        // 적립금 미 가용 처리

        // 배송


        // 추후
        // 마일리지 사용, 쿠폰

    }


    fun findOrderProducts(usrId: UUID, pageRequest: PageRequest): PageImpl<OrderProductDTO> {

        val result = orderCustomRepository.findByOrderProduct(usrId, pageRequest)

        val products = productCustomClient.findProduct(result.content.map { it.productCode })

        result.map {
            products.find { product -> it.productCode == product.productCode }?.run {
                it.productUpdate(this.productImage, this.productName, this.optionName, this.optionValue)
            }
        }


        return result


    }

}