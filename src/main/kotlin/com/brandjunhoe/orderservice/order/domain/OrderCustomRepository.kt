package com.brandjunhoe.orderservice.order.domain

import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

interface OrderCustomRepository {

    fun findByOrderProduct(usrId: UUID, pageRequest: PageRequest): PageImpl<OrderProductDTO>

}