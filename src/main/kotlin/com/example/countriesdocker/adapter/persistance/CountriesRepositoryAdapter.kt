package com.example.countriesdocker.adapter.persistance

import com.example.countriesdocker.adapter.persistance.mapper.CountriesMapper
import com.example.countriesdocker.adapter.persistance.model.CountriesEntity
import com.example.countriesdocker.adapter.persistance.repository.SpringDataCountriesRepository
import com.example.countriesdocker.application.port.out.CountriesRepositoryPort
import com.example.countriesdocker.application.port.out.CountryByIdRepositoryPort
import com.example.countriesdocker.application.port.out.CreateCountryRepositoryPort
import com.example.countriesdocker.config.MessageError
import com.example.countriesdocker.config.exception.ResourceNotFoundException
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.shared.CompanionLogger
import org.springframework.stereotype.Repository

@Repository
class CountriesRepositoryAdapter(
    private val repository: SpringDataCountriesRepository
): CountriesRepositoryPort, CountryByIdRepositoryPort, CreateCountryRepositoryPort {

    override fun findAllCountries(): List<Countries> {
        return repository.findAll().map { it.toCountriesDomain() }
    }

    override fun findCountryById(id: Long) = id
        .log { info("CountryWebService Request: {}", id) }
        .let {
            repository.findById(it).orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el pokemon $id") }

        }
        //.orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el pokemon $id") }

        ?.toCountriesDomain()
        ?.log { info("CountryWebService Response: $it") }
        ?: throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el pokemon $id")


    override fun createCountry(countries: Countries) = countries
        .log { info("CountryWebService Request: {}", countries) }
        .let { CountriesMapper.toCountriesEntity(it) }
        .let { repository.save(it) }
        //.orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el pokemon $it") }
        ?.toCountriesDomain()
        .log {  info("CountryWebService Response: $it")}
        //?: throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el pokemon $it")

    companion object: CompanionLogger()
}