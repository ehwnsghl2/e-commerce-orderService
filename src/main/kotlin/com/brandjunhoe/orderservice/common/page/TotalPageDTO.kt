package com.brandjunhoe.orderservice.common.page

data class TotalPageDTO(private val number: Int = 0,
                   private val totalPages: Int = 0,
                   private val totalElements: Long = 0) {

    fun getNumber(): Int =
            number + 1

}