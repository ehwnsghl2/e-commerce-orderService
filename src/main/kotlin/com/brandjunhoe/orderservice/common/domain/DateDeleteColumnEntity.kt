package com.brandjunhoe.orderservice.common.domain

import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

@MappedSuperclass
open class DateDeleteColumnEntity(

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deldate")
    var deldate: Date? = null

) : DateColumnEntity() {

    fun delete() {
        this.deldate = Date()
    }

}

