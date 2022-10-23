package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.FindCountryByNameInPort
import com.example.countriesdocker.application.port.out.CountryByNameRepositoryPort
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class GetCountryByNameUseCase(
    private val countryByNameRepositoryPort: CountryByNameRepositoryPort,
): FindCountryByNameInPort {

    override fun execute(name: String): Countries = name
        .log {  info("Inicio de proceso de busqueda de country con id = {}", it) }
        .let { countryByNameRepositoryPort.findCountryByName(it) }
        .log { info("Fin de proceso de busqueda de country - response : $it") }

    companion object: CompanionLogger()
}