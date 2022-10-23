package com.example.countriesdocker.adapter.persistance.repository

import com.example.countriesdocker.adapter.persistance.model.CountriesEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SpringDataCountriesRepository: CrudRepository<CountriesEntity, Long>{
    fun findByName(name: String): Optional<CountriesEntity>
}