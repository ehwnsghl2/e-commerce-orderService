package com.brandjunhoe.orderservice.order.presentation

import com.brandjunhoe.orderservice.common.page.ReqPageDTO
import com.brandjunhoe.orderservice.common.page.ResPageDTO
import com.brandjunhoe.orderservice.common.page.TotalPageDTO
import com.brandjunhoe.orderservice.common.response.CommonResponse
import com.brandjunhoe.orderservice.order.application.OrderService
import com.brandjunhoe.orderservice.order.application.dto.OrderProductDTO
import com.brandjunhoe.orderservice.order.presentation.dto.ReqOrderSaveDTO
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/order")
class OrderController(val orderService: OrderService) {


    @PostMapping
    fun saveOrder(@RequestBody request: ReqOrderSaveDTO): CommonResponse<Any> {
        orderService.save(request)
        return CommonResponse()
    }

    @GetMapping("/{usrId}")
    fun findOrderProducts(
        @PathVariable @Valid @NotBlank usrId: UUID,
        pageReq: ReqPageDTO
    ): CommonResponse<ResPageDTO<List<OrderProductDTO>>> {
        val result = orderService.findOrderProducts(usrId, pageReq.getPageable())

        return CommonResponse(
            ResPageDTO(
                TotalPageDTO(result.number, result.totalPages, result.totalElements),
                result.content
            )
        )
    }

    @PostMapping("/{orderCode}/product-purchase/{orderProductCode}")
    fun updatePurchaseOrderProduct(
        @PathVariable @Valid @NotBlank orderCode: String,
        @PathVariable @Valid @NotBlank orderProductCode: String
    ): CommonResponse<Any> {
        orderService.updateOrderProductPurchase(UUID.randomUUID()/*추후구현*/, orderCode, orderProductCode)
        return CommonResponse()
    }

}