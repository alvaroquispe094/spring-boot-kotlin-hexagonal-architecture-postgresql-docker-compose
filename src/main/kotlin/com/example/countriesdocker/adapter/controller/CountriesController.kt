package com.example.countriesdocker.adapter.controller

import com.example.countriesdocker.adapter.controller.model.CountriesRest
import com.example.countriesdocker.application.port.`in`.CreateCountryInPort
import com.example.countriesdocker.application.port.`in`.FindAllCountriesInPort
import com.example.countriesdocker.application.port.`in`.FindCountryByIdInPort
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletionStage

@RestController
@RequestMapping("/api/v1")
class CountriesController(
    private val findAllCountriesInPort: FindAllCountriesInPort,
    private val findAllCountryByIdInPort: FindCountryByIdInPort,
    private val createCountryInPort: CreateCountryInPort,
) {

    @GetMapping("/countries")
    fun findAll(): List<CountriesRest>{
        return findAllCountriesInPort.find()
            .let { c -> c.map { CountriesRest.from(it) } }
    }

    @GetMapping("/countries/{id}")
    fun find(@PathVariable("id") id: Long): CompletionStage<CountriesRest> = id
        .log { info("Llamada al servicio /countries/{}", it) }
        .let { it ->
            findAllCountryByIdInPort.find(it)
                .thenApply { p -> CountriesRest.from(p) }
                .thenApply { response ->
                    response.log { info("Respuesta servicio getPokemon future: {}", it) }
                }
        }

    @PostMapping("/countries")
    fun create(@RequestBody @Validated req: CountriesRest): String = req
        .log { info("Llamada a creacion de countries {}", it) }
        .let { req.toDomain() }
        .let { createCountryInPort.create(it) }
        .toString()

    companion object: CompanionLogger()

}