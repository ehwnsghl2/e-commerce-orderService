package com.brandjunhoe.orderservice.client.dto

import com.brandjunhoe.orderservice.client.enum.UserGradeEnum

class UserDTO(

    val name: String,

    val email: String,

    val grade: UserGradeEnum

)