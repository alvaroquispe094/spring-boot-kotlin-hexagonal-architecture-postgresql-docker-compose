package com.example.countriesdocker.adapter.persistance.repository

import com.example.countriesdocker.adapter.persistance.model.CountriesEntity
import org.springframework.data.repository.CrudRepository

interface SpringDataCountriesRepository: CrudRepository<CountriesEntity, Long>