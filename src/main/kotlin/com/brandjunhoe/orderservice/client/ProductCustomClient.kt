package com.brandjunhoe.orderservice.client

import com.brandjunhoe.orderservice.client.dto.ProductDTO
import com.brandjunhoe.orderservice.common.exception.BadRequestException
import com.brandjunhoe.orderservice.common.response.CommonResponse
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component


@Component
class ProductCustomClient(private val productClient: ProductClient) {


    fun findProduct(productCodes: List<String>): List<ProductDTO> {

        return check(productClient.products(productCodes)) as List<ProductDTO>
    }


    private fun check(response: CommonResponse<*>): Any {

        try {

            if (response.code == HttpStatus.OK.value() && response.data != null)
                return response.data!!
            else throw Exception("feign server error")

        } catch (exception: FeignException.FeignClientException) {
            throw Exception("product feignt client exception")
        } catch (exception: FeignException.FeignServerException) {
            throw Exception("product feignt server exception")
        }


    }

}