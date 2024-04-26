package com.example.countriesdocker.adapter.kafka

import com.example.countriesdocker.adapter.kafka.model.ProductCreation
import com.example.countriesdocker.application.port.out.NotificationRepositoryPort
import com.example.countriesdocker.config.MessageError
import com.example.countriesdocker.config.exception.ResourceNotFoundException
import com.example.countriesdocker.config.properties.SpringConfigurationProperties
import com.example.countriesdocker.shared.CompanionLogger
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Repository

@Repository
class KafkaProducerAdapter(
    val kafkaTemplate: KafkaTemplate<String, ProductCreation>,
    val springConfigurationProperties: SpringConfigurationProperties,
    val objectMapper: ObjectMapper,
): NotificationRepositoryPort {

    override fun notifyCreation(productCreation: ProductCreation) =
        try {
            productCreation
                .log { info("Enviando notificacion con productCreration data= {}", it) }
                .let {
                    kafkaTemplate.send("find", it)
                    kafkaTemplate.flush()
                }
                //.orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "Ocurrio un error en kafka") }
                //.toCountriesDomain()
                .log { info("Notificacion enviada correctamente") }
        }catch (e: Exception) {
            logger.error("Ocurrio un error en kafka {}", e)
            throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "Ocurrio un error en kafka")
        }
        /*productCreation
            .log { info("Enviando notificacion con productCreration data= {}", it) }
        val seedModel: SeedNotificationModel = SeedNotificationModel.fromDomain(seed)
        seedModel.setUuid(notificationId)
        log.info("Enviando notificacion de seed  {} ", seedModel)
        try {
            kafkaTemplate.send(springConfigurationProperties.getKafka().getTopic().getCreation(), seedModel)
            kafkaTemplate.flush()
        } catch (e: Exception) {
            log.error("Ocurrio un error en kafka {}", e)
            throw NotificationException(ErrorCode.KAFKA_EXCEPTION)
        }*/


    companion object: CompanionLogger()

}