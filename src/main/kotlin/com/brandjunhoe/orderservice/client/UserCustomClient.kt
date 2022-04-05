package com.brandjunhoe.orderservice.client

import com.brandjunhoe.orderservice.client.dto.UserDTO
import com.brandjunhoe.orderservice.common.response.CommonResponse
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*


@Component
class UserCustomClient(private val userClient: UserClient) {


    fun findUser(usrId: UUID): UserDTO {

        return check(userClient.findUser(usrId)) as UserDTO
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