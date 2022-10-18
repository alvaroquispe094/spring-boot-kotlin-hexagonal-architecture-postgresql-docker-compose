package com.example.countriesdocker.application.port.usecase

import com.example.countriesdocker.application.port.`in`.FindCountryByIdInPort
import com.example.countriesdocker.application.port.out.CountryByIdRepositoryPort
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor

@Component
class GetCountryUseCase(
    private val countryByIdRepositoryPort: CountryByIdRepositoryPort,
    //@Qualifier("asyncExecutor") private val executor: Executor
): FindCountryByIdInPort {

    override fun find(id: Long): CompletionStage<Countries> {
        val fcountry = CompletableFuture.supplyAsync { countryByIdRepositoryPort.findCountryById(id) }
        fcountry.thenAccept { log { info("Pokemon: {}", it) } }

        return CompletableFuture.allOf().thenApply {
            val contries = fcountry.join()

            Countries(
                name = contries.name,
                capital = contries.capital,
                subregion = contries.subregion,
                region = contries.region,
                population = contries.population,
                area = contries.area,
                currency = contries.currency,
                language = contries.language,
                flag = contries.flag,
            )
        }
    }

    companion object: CompanionLogger()
}