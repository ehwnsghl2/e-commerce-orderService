package com.brandjunhoe.orderservice.order.infra

import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO
import com.brandjunhoe.orderservice.order.domain.OrderCustomRepository
import com.brandjunhoe.orderservice.order.domain.QOrderProduct.orderProduct
import com.brandjunhoe.orderservice.order.domain.QOrders.orders
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OrderCustomImplRepository(val queryFactory: JPAQueryFactory) : OrderCustomRepository {


    override fun findByOrderProduct(usrId: UUID, pageRequest: PageRequest): PageImpl<OrderProductDTO> {
        val result = queryFactory.select(
            Projections.constructor(
                OrderProductDTO::class.java,
                orderProduct.orderProductCode,
                orderProduct.productCode,
                Expressions.stringTemplate("DATE_FORMAT({0}, {1})",
                    orders.regdate, "%Y-%m-%d"),
                orderProduct.amount,
                orderProduct.quantity,
                orderProduct.state
            )
        )
            .from(orders)
            .join(orders.orderProducts, orderProduct)
            .where(orders.usrId.eq(usrId))
            .fetchResults()

        return PageImpl(result.results, pageRequest, result.total)

    }


}