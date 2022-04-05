package com.brandjunhoe.orderservice.order.presentation

import com.brandjunhoe.orderservice.common.page.ReqPageDTO
import com.brandjunhoe.orderservice.common.page.ResPageDTO
import com.brandjunhoe.orderservice.common.page.TotalPageDTO
import com.brandjunhoe.orderservice.common.response.CommonResponse
import com.brandjunhoe.orderservice.order.application.OrderProductService
import com.brandjunhoe.orderservice.order.application.OrderService
import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO
import com.brandjunhoe.orderservice.order.presentation.dto.ReqOrderSaveDTO
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/order/product")
class OrderProductController(val orderProductService: OrderProductService) {


    @PostMapping("/{orderProductCode}")
    fun updatePurchaseOrderProduct(@PathVariable @Valid @NotBlank orderProductCode: String): CommonResponse<Any> {
        orderProductService.updatePurchase(orderProductCode)
        return CommonResponse()
    }


}