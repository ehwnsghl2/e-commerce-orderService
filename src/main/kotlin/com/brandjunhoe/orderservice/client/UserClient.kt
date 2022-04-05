package com.brandjunhoe.orderservice.client

import com.brandjunhoe.orderservice.client.dto.ProductDTO
import com.brandjunhoe.orderservice.client.dto.UserDTO
import com.brandjunhoe.orderservice.common.response.CommonResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@FeignClient(name = "user-service")
interface UserClient {

    @GetMapping("/user/{id}")
    fun findUser(@PathVariable("id") id: UUID): CommonResponse<UserDTO>

}