package com.brandjunhoe.orderservice.common.generator

import org.apache.commons.lang.RandomStringUtils
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Create by DJH on 2022/03/21.
 */
class CodeGenerator {

    companion object {

        fun RANDOM(code: String, datetime: LocalDateTime = LocalDateTime.now()): String {
            val currentTime = Timestamp.valueOf(datetime).time.toString().substring(1, 10)
            val lastCode = RandomStringUtils.randomAlphanumeric(2)
            return "$code$currentTime$lastCode"
        }
    }

}