package com.example.countriesdocker.adapter.kafka.consumer

import com.example.countriesdocker.adapter.kafka.model.ProductCreation
import com.example.countriesdocker.shared.CompanionLogger
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer (
    private val objectMapper: ObjectMapper,
){
    @KafkaListener(topics = ["find"], groupId = "myGroup")
    fun createOrder(consumerRecord: ConsumerRecord<String?, String?>) {
        logger.info("Consumer message: {}", consumerRecord.value())

        try {
            val command: ProductCreation = objectMapper.readValue(consumerRecord.value(),
                ProductCreation::class.java)
            logger.info("Message data received {}", command)
            //ack.acknowledge()
            //logger.info("Find topic - Datos correctamente recibidos = ${objectMapper.writeValueAsString(command))}")
        } catch (e: JsonProcessingException) {
            logger.error(
                "Error parsing kafka message: {}. The message will be marked as read in Kafka queue.",
                consumerRecord,
                e
            )
        }
    }

    companion object: CompanionLogger()
}

/*
package com.example.countriesdocker.adapter.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.msmpos.application.exception.JDEInvalidStatusException;
import com.redbee.msmpos.application.port.in.CreateOrderCommand;
import com.redbee.msmpos.application.port.in.ValidateOrderStatusCommand;
import com.redbee.msmpos.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.retrytopic.DltStrategy.FAIL_ON_ERROR;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {
    private final CreateOrderCommand createOrderCommand;
    private final ValidateOrderStatusCommand validateOrderStatusCommand;
    private final NotificationService notificationService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.topic.orderCreation}")
    public void createOrder(ConsumerRecord<String, String> consumerRecord) {
        log.info("Consumer message: {}", consumerRecord);
        CreateOrderCommand.Command command;
        try {
            command = objectMapper.readValue(consumerRecord.value(),
                    new TypeReference<>() {
                    });
            createOrderCommand.execute(command);

        } catch (JsonProcessingException e) {
            log.error("Error parsing kafka message: {}. The message will be marked as read in Kafka queue.", consumerRecord, e);
        }
    }

    @RetryableTopic(
            attempts = "#{'${spring.kafka.orderStatus.retry.maxAttepts}'}",
            autoCreateTopics = "false",
            backoff = @Backoff(delayExpression = "#{'${spring.kafka.orderStatus.retryIntervalMilliseconds}'}"),
            sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC,
            include = {JDEInvalidStatusException.class},
            timeout = "#{'${spring.kafka.orderStatus.maxRetryDurationMilliseconds}'}",
            dltStrategy = FAIL_ON_ERROR)
    @KafkaListener(topics = "${spring.topic.orderStatus}")
    public void validateOrderStatus(ConsumerRecord<String, String> consumerRecord) {
        log.info("Consumer topic: {}, message: {}", consumerRecord.topic(), consumerRecord.value());
        String request = consumerRecord.value();
        var values = request.split(":");
        validateOrderStatusCommand.execute(values[0], values[1]);
    }

    @DltHandler
    public void listenOnDlt(ConsumerRecord<String, String> consumerRecord) {
        log.error("Received event on dlt.");
        String request = consumerRecord.value();
        notificationService.sendNotificationError(request.split(":")[0]);
    }

}

 */