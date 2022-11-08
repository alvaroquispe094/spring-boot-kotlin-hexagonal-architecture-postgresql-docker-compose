/*
package com.example.countriesdocker.config

import com.example.countriesdocker.adapter.controller.CountriesController
import com.example.countriesdocker.adapter.persistance.CountriesRepositoryAdapter
import com.example.countriesdocker.adapter.persistance.repository.SpringDataCountriesRepository
import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.`in`.FindAllCountriesInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByIdInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByNameInPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.math.BigDecimal
import java.time.Duration

@TestConfiguration
*/
/*@ComponentScan("com.example.*")
@EnableScheduling*//*

//@EnableConfigurationProperties(SpringDataCountriesRepository::class)
class AppTestConfig {

    @Bean
    fun getRestTemplate(
        restTemplateBuilder: RestTemplateBuilder,
        @Value("\${rest.client.default.timeout}") timeout: Int
    ): RestTemplate {
        return restTemplateBuilder
            .setConnectTimeout(Duration.ofMillis(timeout.toLong()))
            .setReadTimeout(Duration.ofMillis(timeout.toLong()))
            //.errorHandler(RestTemplateErrorHandler())
            .build()
    }

*/
/*@Bean
    fun countriesRepositoryAdapter(repository: SpringDataCountriesRepository): CountriesRepositoryAdapter {
        return CountriesRepositoryAdapter(repository)
    }*//*


    */
/*
@Bean
    open fun bootstrapData(repository: CountriesRepositoryAdapter): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->

        }
    }


@Bean
    fun countriesController(findCountryByNameInPort: FindCountryByNameInPort,
                            findAllCountriesInPort: FindAllCountriesInPort,
                            findCountryByIdInPort: FindCountryByIdInPort,
                            createCountryInPort: CreateCountryInPort
    ): CountriesController {
        return CountriesController(findCountryByNameInPort = findCountryByNameInPort,
            findAllCountriesInPort = findAllCountriesInPort,
            findCountryByIdInPort = findCountryByIdInPort,
            createCountryInPort = createCountryInPort)
    }
*//*


}
*/
