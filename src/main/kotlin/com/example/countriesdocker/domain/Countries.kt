package com.example.countriesdocker.domain

data class Countries(
    val name: String,
    val capital: String?,
    val subregion: String,
    val region: String,
    val population: Int,
    val area: Double,
    val currency: String?,
    val language: String,
    val flag: String,
)