package com.domain.entitys

data class TicketsDomainEntity (
    val id: Int,
    val badge: String?,
    val price: Int,
    val provider_name: String,
    val company: String,
    val departure: Departure,
    val arrival: Arrival,
    val has_transfer: Boolean,
    val has_visa_transfer: Boolean,
    val luggage: Luggage?,
    val hand_luggage: HandLuggage?,
    val is_returnable: Boolean,
    val is_exchangable: Boolean,
) {
    data class Departure(
        val town: String,
        val date: String,
        val airport: String
    )

    data class Arrival(
        val town: String,
        val date: String,
        val airport: String
    )

    data class Luggage(
        val has_luggage: Boolean,
        val price: Price? = null // Сделаем price nullable, если оно не обязательно
    )

    data class HandLuggage(
        val has_hand_luggage: Boolean,
        val size: String? = null // Сделаем size nullable
    )

    data class Price(
        val value: Int
    )
}
