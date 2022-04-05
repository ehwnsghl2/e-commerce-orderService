package com.brandjunhoe.orderservice.client

import com.brandjunhoe.orderservice.client.dto.ProductDTO
import com.brandjunhoe.orderservice.common.response.CommonResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "product-service")
interface ProductClient {

    @GetMapping
    fun products(@RequestParam productCodes: List<String>): CommonResponse<List<ProductDTO>>

}