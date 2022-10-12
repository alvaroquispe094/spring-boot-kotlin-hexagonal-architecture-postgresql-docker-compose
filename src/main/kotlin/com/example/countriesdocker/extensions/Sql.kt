package com.example.countriesdocker.extensions

fun String.trimSQL() = lines()
        .filter(String::isNotBlank)
        .joinToString(separator = " ") { it.trim() }
