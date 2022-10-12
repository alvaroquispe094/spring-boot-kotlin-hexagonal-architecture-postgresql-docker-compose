package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.out.CountryByIdRepositoryPort
import com.example.countriesdocker.application.port.out.CreateCountryRepositoryPort
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor

@Component
class CreateCountryUseCase(
    private val createCountryRepositoryPort: CreateCountryRepositoryPort,
): CreateCountryInPort {

    override fun create(countries: Countries): Countries {
        val country = createCountryRepositoryPort.createCountry(countries)
        //countries.thenAccept { log { info("Pokemon: {}", it) } }
        return country
    }

    companion object: CompanionLogger()
}