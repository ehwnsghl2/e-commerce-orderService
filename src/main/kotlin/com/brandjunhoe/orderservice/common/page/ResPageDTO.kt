package com.brandjunhoe.orderservice.common.page

import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO

data class ResPageDTO<T>(private val total: TotalPageDTO, private val data: T)