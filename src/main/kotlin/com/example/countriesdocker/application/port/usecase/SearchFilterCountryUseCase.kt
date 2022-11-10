package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.SearchFilterCountryInPort
import com.example.countriesdocker.application.port.out.SearchFilterCountryRepositoryPort
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.domain.CountriesSearchFilter
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class SearchFilterCountryUseCase(
    private val searchFilterCountryRepositoryPort: SearchFilterCountryRepositoryPort,
): SearchFilterCountryInPort {

    override fun execute(searchFilter: CountriesSearchFilter): List<Countries> = searchFilter
        .log { info("Inicio de proceso de busqueda de country con filtros request : $it") }
        .let { searchFilterCountryRepositoryPort.searchCountry(searchFilter) }
        .log { info("Fin de proceso de busqueda de country con response : $it") }

    companion object: CompanionLogger()
}