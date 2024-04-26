package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.adapter.kafka.model.ProductCreation
import com.example.countriesdocker.application.port.`in`.FindCountryByIdInPort
import com.example.countriesdocker.application.port.out.CountryByIdRepositoryPort
import com.example.countriesdocker.application.port.out.NotificationRepositoryPort
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class GetCountryUseCase(
    private val countryByIdRepositoryPort: CountryByIdRepositoryPort,
    private val notificationRepositoryPort: NotificationRepositoryPort,
): FindCountryByIdInPort {

    override fun execute(id: Long): Countries = id
        .log {  info("Inicio de proceso de busqueda de country con id = {}", it) }
        .let { countryByIdRepositoryPort.findCountryById(it) }
        .also { notificationRepositoryPort.notifyCreation(
            ProductCreation(countryName = it.name, population= it.population, language = it.language))
        }
        .log { info("Fin de proceso de busqueda de country - response : $it") }

    companion object: CompanionLogger()
}