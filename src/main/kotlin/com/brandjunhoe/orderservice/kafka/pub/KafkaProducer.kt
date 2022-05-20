package com.brandjunhoe.orderservice.kafka.pub

import com.brandjunhoe.orderservice.order.domain.event.MileageSaveEvent
import com.brandjunhoe.orderservice.order.domain.event.PaymentSaveEvent
import com.brandjunhoe.orderservice.order.domain.event.ProductItemQuantityUpdateEvent
import com.brandjunhoe.orderservice.order.domain.event.ShippingSaveEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {


    private val log = LoggerFactory.getLogger(javaClass)

    private val TOPIC_PAYMENT_SAVE = "payment-save"
    private val TOPIC_SHIPPING_SAVE = "shipping-save"
    private val TOPIC_MILEAGE_SAVE = "mileage-save"
    private val TOPIC_PRODUCT_ITEM_QUANTITY_UPDATE = "product-item-quantity-update"

    @EventListener
    @Async
    @Order(1)
    @Throws(JsonProcessingException::class)
    fun sendPaymentSaveEvent(event: PaymentSaveEvent) {
        sendMsg(TOPIC_PAYMENT_SAVE, event)
    }

    @EventListener
    @Async
    @Order(2)
    @Throws(JsonProcessingException::class)
    fun sendProductItemQuantityUpdate(event: ProductItemQuantityUpdateEvent) {
        sendMsg(TOPIC_PRODUCT_ITEM_QUANTITY_UPDATE, event)
    }

    @EventListener
    @Async
    @Order(3)
    @Throws(JsonProcessingException::class)
    fun sendShippingSaveEvent(event: ShippingSaveEvent) {
        sendMsg(TOPIC_SHIPPING_SAVE, event)
    }

    @EventListener
    @Async
    @Order(4)
    @Throws(JsonProcessingException::class)
    fun sendShippingSaveEvent(event: MileageSaveEvent) {
        sendMsg(TOPIC_MILEAGE_SAVE, event)
    }

    fun sendMsg(topic: String, message: Any) {
        val data = objectMapper.writeValueAsString(message)
        this.kafkaTemplate.send(topic, data)
            .addCallback(listenableFutureCallback(data))
    }


    private fun listenableFutureCallback(message: String) =
        object : ListenableFutureCallback<SendResult<String, String>> {
            override fun onSuccess(result: SendResult<String, String>?) {
                log.info(
                    "Send with topic = [${result!!.recordMetadata.topic()}], message = [$message], offset=[ ${result!!.recordMetadata.offset()} ]"
                )
            }

            override fun onFailure(ex: Throwable) {
                log.error("Message 전달 오류, message = [$message], due to : ${ex.message}", ex)
            }
        }

}