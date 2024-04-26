package com.example.countriesdocker.adapter.kafka.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonCreator

data class ProductCreation @JsonCreator constructor(
    @JsonProperty("countryName") val countryName: String,
    @JsonProperty("population") val population: Int,
    @JsonProperty("language") val language: String
)