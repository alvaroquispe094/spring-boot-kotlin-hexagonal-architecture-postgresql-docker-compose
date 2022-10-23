package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.out.CreateCountryRepositoryPort
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class CreateCountryUseCase(
    private val createCountryRepositoryPort: CreateCountryRepositoryPort,
): CreateCountryInPort {

    override fun execute(countries: Countries): Countries = countries
        .log { info("Inicio de proceso de creación de country con body request : $it") }
        .let { createCountryRepositoryPort.createCountry(countries) }
        .log { info("Fin de proceso de creación de country con response : $it") }

    companion object: CompanionLogger()
}