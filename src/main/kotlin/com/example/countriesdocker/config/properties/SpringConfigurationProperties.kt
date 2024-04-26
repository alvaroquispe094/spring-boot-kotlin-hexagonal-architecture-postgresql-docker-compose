package com.example.countriesdocker.config.properties

import com.example.countriesdocker.config.properties.model.KafkaProperties
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.stereotype.Component

/*@Component
@Configuration
class SpringConfigurationProperties(
    val kafka: KafkaProperties
)*/

@Component
@Configuration
class SpringConfigurationProperties{
    @Bean
    fun topicCreate(): NewTopic {
        return TopicBuilder.name("find").build()
    }
}