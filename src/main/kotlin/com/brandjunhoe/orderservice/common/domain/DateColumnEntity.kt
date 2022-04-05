package com.brandjunhoe.orderservice.common.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

@MappedSuperclass
open class DateColumnEntity(

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "regdate", nullable = false)
        val regdate: Date? = null,

        @UpdateTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "moddate")
        val moddate: Date? = null

)
