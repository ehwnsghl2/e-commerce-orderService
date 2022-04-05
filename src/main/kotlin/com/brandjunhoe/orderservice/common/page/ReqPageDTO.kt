package com.brandjunhoe.orderservice.common.page

import org.springframework.data.domain.PageRequest


data class ReqPageDTO(private val page: Int = 1, private val size: Int = 20) {

    fun getPageable(): PageRequest = PageRequest.of(page - 1, size)

}