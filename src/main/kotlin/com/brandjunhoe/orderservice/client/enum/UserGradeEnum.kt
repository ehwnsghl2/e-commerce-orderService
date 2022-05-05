package com.brandjunhoe.orderservice.client.enum

enum class UserGradeEnum(val discountRate: Int, val mileageSaveRate: Int) {
    FAMILY(1, 1),
    SILVER(3, 3),
    GLOD(5, 5),
    VIP(10, 10)
}
