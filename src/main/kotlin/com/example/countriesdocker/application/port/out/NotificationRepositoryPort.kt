package com.example.countriesdocker.application.port.out

import com.example.countriesdocker.adapter.kafka.model.ProductCreation

interface NotificationRepositoryPort {
    fun notifyCreation(productCreation: ProductCreation)
}