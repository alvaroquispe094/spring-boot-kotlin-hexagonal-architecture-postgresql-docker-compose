package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.FindAllCountriesInPort
import com.example.countriesdocker.application.port.out.CountriesRepositoryPort
import com.example.countriesdocker.domain.Countries
import org.springframework.stereotype.Component

@Component
class GetAllCountriesUseCase(
    private val countriesRepositoryPort: CountriesRepositoryPort,
): FindAllCountriesInPort {
    override fun find(): List<Countries> {
        val countries = countriesRepositoryPort.findAllCountries()
        //countries.thenAccept { log { info("Pokemon: {}", it) } }
        return countries
    }

}