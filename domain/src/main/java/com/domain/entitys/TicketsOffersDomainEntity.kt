package com.domain.entitys

data class TicketsOffersDomainEntity(
    val id: Int,
    val title: String,
    val time_range: List<String>,
    val price: Int
)
