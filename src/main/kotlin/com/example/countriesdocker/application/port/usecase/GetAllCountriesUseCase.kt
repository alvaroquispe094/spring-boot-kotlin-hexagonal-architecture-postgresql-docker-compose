package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.FindAllCountriesInPort
import com.example.countriesdocker.application.port.out.CountriesRepositoryPort
import com.example.countriesdocker.application.port.usecase.CreateCountryUseCase.Companion.log
import com.example.countriesdocker.domain.Countries
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetAllCountriesUseCase(
    private val countriesRepositoryPort: CountriesRepositoryPort,
): FindAllCountriesInPort {

    private val logger = LoggerFactory.getLogger(GetAllCountriesUseCase::class.java)

    override fun execute(): List<Countries> = run {
        logger.info("Inicio de proceso de busqueda de countries")
            .let { countriesRepositoryPort.findAllCountries() }
            .log { info("Fin de proceso de busqueda de countries") }

    }
}