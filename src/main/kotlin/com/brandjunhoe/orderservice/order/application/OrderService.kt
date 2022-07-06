package com.brandjunhoe.orderservice.order.application

import com.brandjunhoe.orderservice.client.ProductCustomClient
import com.brandjunhoe.orderservice.client.UserCustomClient
import com.brandjunhoe.orderservice.common.exception.DataNotFoundException
import com.brandjunhoe.orderservice.kafka.pub.enums.MileageStateEnum
import com.brandjunhoe.orderservice.kafka.pub.enums.MileageTypeEnum
import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO
import com.brandjunhoe.orderservice.order.application.dto.OrderShippingSaveDTO
import com.brandjunhoe.orderservice.order.application.dto.ShippingRegionDTO
import com.brandjunhoe.orderservice.order.domain.OrderCode
import com.brandjunhoe.orderservice.order.domain.OrderCustomRepository
import com.brandjunhoe.orderservice.order.domain.OrderProduct
import com.brandjunhoe.orderservice.order.domain.OrderRepository
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
import java.math.BigDecimal
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
        val products = productCustomClient.findProduct(request.products.map { it.productCode })

        // 할인 (상품 할인, 등급 할인)
        val user = userCustomClient.findUser(request.usrId)

        // 주문서 생성
        val orders = orderRepository.save(request.toEntity(user, products))

        // 총 상품가
        val sellingPrice = orders.orderProduct.sumOf { it.amount }

        // 총 할인가
        val discountPrice = orders.orderProduct.sumOf { it.discountPrice }

        // 상품 할인가
        val totalPrice = sellingPrice.plus(discountPrice)

        // 기본 배송비
        val shippingPrice = getShippingPrice(totalPrice)

        // 지역 배송비
        val shippingAddPrice = getShippingAddPrice(request.shipping.postCode)

        // 결제 금액
        val amount = paymentAmount(totalPrice, shippingPrice, shippingAddPrice)

        // 결제
        paymentSaveEvent(orders.orderCode, request.payment.paymentMethod, amount.toBigDecimal())

        // 재고
        stockQuantityEvent(orders.orderProduct)

        // 배송
        shippingSaveEvent(orders.orderCode, request.shipping)

        // 적립금 미 가용 처리
        mileageSaveEvent(request.usrId, orders.orderCode, MileageTypeEnum.PRODUCT, MileageStateEnum.READY, orders.orderProduct)


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

        val orderProduct = orders.changeOrderProductPurchase(orderProductCode)

        // 적립금 가용 처리
        mileageSaveEvent(usrId, orders.orderCode, MileageTypeEnum.PRODUCT, MileageStateEnum.SAVE, listOf(orderProduct))

    }

    private fun getShippingPrice(totalPrice: Int): Int {
        val freeAmount = 70000
        val basicShippingPrice = 3000
        return if (totalPrice < freeAmount)
            basicShippingPrice else 0
    }

    private fun getShippingAddPrice(postCode: String): Int {
        // 지역 배송비 (우선 제주도만)
        val shippingRegion = listOf(ShippingRegionDTO("제주도", 63000, 63644, 4000))
        val shippingAddPrice =
            shippingRegion.find { postCode.toInt() >= it.from && postCode.toInt() <= it.until }
                ?.let { it.price } ?: 0
        return shippingAddPrice
    }

    private fun paymentAmount(totalPrice: Int, shippingPrice: Int, shippingAddPrice: Int): Int =
        totalPrice.minus(shippingPrice).minus(shippingAddPrice)


    private fun paymentSaveEvent(orderCode: OrderCode, paymentMethod: String, amount: BigDecimal) {
        eventPublisher.publishEvent(PaymentSaveEvent(orderCode.orderCode, paymentMethod, amount))
    }

    private fun stockQuantityEvent(orderProduct: List<OrderProduct>) {
        orderProduct.forEach {
            eventPublisher.publishEvent(ProductItemQuantityUpdateEvent(it.productCode, it.itemCode, it.quantity))
        }
    }

    private fun shippingSaveEvent(orderCode: OrderCode, shipping: OrderShippingSaveDTO) {
        eventPublisher.publishEvent(
            ShippingSaveEvent(
                orderCode.orderCode,
                shipping.receiver,
                shipping.phone,
                shipping.postCode,
                shipping.address,
                shipping.addressDetail
            )
        )
    }

    private fun mileageSaveEvent(
        usrId: UUID,
        orderCode: OrderCode,
        mileageType: MileageTypeEnum,
        mileageState: MileageStateEnum,
        orderProduct: List<OrderProduct>
    ) {
        orderProduct.forEach {
            eventPublisher.publishEvent(
                MileageSaveEvent(
                    usrId,
                    orderCode.orderCode,
                    it.orderProductCode.orderProductCode,
                    mileageType,
                    mileageState,
                    it.mileage
                )
            )
        }
    }

}