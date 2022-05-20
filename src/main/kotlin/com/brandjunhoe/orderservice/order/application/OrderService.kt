package com.brandjunhoe.orderservice.order.application

import com.brandjunhoe.orderservice.client.ProductCustomClient
import com.brandjunhoe.orderservice.client.UserCustomClient
import com.brandjunhoe.orderservice.common.exception.DataNotFoundException
import com.brandjunhoe.orderservice.common.generator.CodeGenerator
import com.brandjunhoe.orderservice.kafka.pub.enums.MileageStateNum
import com.brandjunhoe.orderservice.kafka.pub.enums.MileageTypeEnum
import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO
import com.brandjunhoe.orderservice.order.application.dto.ShippingRegionDTO
import com.brandjunhoe.orderservice.order.domain.*
import com.brandjunhoe.orderservice.order.domain.enums.DeviceTypeEnum
import com.brandjunhoe.orderservice.order.domain.enums.OrderProductStateEnum
import com.brandjunhoe.orderservice.order.domain.event.MileageSaveEvent
import com.brandjunhoe.orderservice.order.domain.event.PaymentSaveEvent
import com.brandjunhoe.orderservice.order.domain.event.ProductItemQuantityUpdateEvent
import com.brandjunhoe.orderservice.order.domain.event.ShippingSaveEvent
import com.brandjunhoe.orderservice.order.presentation.dto.ReqOrderSaveDTO
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderCustomRepository: OrderCustomRepository,
    private val userCustomClient: UserCustomClient,
    private val productCustomClient: ProductCustomClient,
    private val eventPublisher: ApplicationEventPublisher
) {


    @Transactional
    fun save(request: ReqOrderSaveDTO) {


        // 상품
        val products = productCustomClient.findProduct(
            request.products.map { it.productCode }
        )


        // 할인 (상품 할인, 등급 할인)
        val user = userCustomClient.findUser(request.usrId)

        val freeAmount = 70000
        val basicShippingPrice = 3000

        val sellingPrice = products.sumOf { it.sellingPrice.plus(it.itemAddPrice) }

        val discountPrice = rate(sellingPrice, user.grade.discountRate).unaryMinus()

        val totalPrice = sellingPrice.plus(discountPrice)

        // 배송 정책 (무배 금액 기준)
        // 기본 배송비
        val shippingPrice = if (totalPrice < freeAmount)
            basicShippingPrice else 0

        // 지역 배송비 (우선 제주도만)
        val shippingRegion = listOf(ShippingRegionDTO("제주도", 63000, 63644, 4000))
        val shippingAddPrice =
            shippingRegion.find { request.shipping.postCode.toInt() >= it.from && request.shipping.postCode.toInt() <= it.until }
                ?.let {
                    it.price
                } ?: 0


        // 주문서 코드 생성
        val code = CodeGenerator.RANDOM("OC")


        val orderProduct = request.products.map { product ->

            products.find { it.productCode == product.productCode }?.let {
                val sellingPrice = it.sellingPrice.plus(it.itemAddPrice)
                val discountPrice = rate(sellingPrice, user.grade.discountRate).unaryMinus()

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
                    sellingPrice.plus(discountPrice).toBigDecimal(),
                    rate(totalPrice, user.grade.mileageSaveRate)
                )
            } ?: throw DataNotFoundException("product not found")


        }

        val orders = Orders(
            OrderCode(code), request.usrId, user.name, user.email, request.shipping.phone,
            DeviceTypeEnum.PC,
            orderProducts = orderProduct
        )

        orderRepository.save(orders)

        // 결제금액
        val amount = totalPrice.minus(shippingPrice).minus(shippingAddPrice).toBigDecimal()

        // 결제
        eventPublisher.publishEvent(PaymentSaveEvent(code, request.paymentMethod.paymentMethod, amount))

        // 재고
        orderProduct.forEach {
            eventPublisher.publishEvent(
                ProductItemQuantityUpdateEvent(
                    it.productCode,
                    it.itemCode,
                    it.quantity
                )
            )
        }

        // 배송
        eventPublisher.publishEvent(
            ShippingSaveEvent(
                code,
                request.shipping.receiver,
                request.shipping.phone,
                request.shipping.postCode,
                request.shipping.address,
                request.shipping.addressDetail
            )
        )

        // 적립금 미 가용 처리
        orderProduct.forEach {
            eventPublisher.publishEvent(
                MileageSaveEvent(
                    request.usrId,
                    code,
                    it.orderProductCode.orderProductCode,
                    MileageTypeEnum.PRODUCT,
                    MileageStateNum.READY,
                    it.mileage
                )
            )
        }


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

    fun updateOrderProductPurchase(usrId: UUID, orderCode: String, orderProductCode: String) {

        val orders = orderRepository.findByOrderCode(OrderCode(orderCode))
            ?: throw DataNotFoundException("order not found")

        orders.changeOrderProductPurchase(orderProductCode)

        // 적립금 가용 처리

        eventPublisher.publishEvent(
            MileageSaveEvent(
                usrId,
                orderCode,
                orderProductCode,
                MileageTypeEnum.PRODUCT,
                MileageStateNum.SAVE
            )
        )

    }


    fun rate(target: Int, rate: Int): Int {
        return (rate / 100) * target
    }

}