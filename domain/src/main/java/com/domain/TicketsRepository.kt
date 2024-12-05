package com.domain

import com.domain.entitys.OffersDomainEntity
import com.domain.entitys.TicketsDomainEntity
import com.domain.entitys.TicketsOffersDomainEntity
import kotlinx.coroutines.flow.Flow

interface TicketsRepository {
    fun getAllTickets(): Flow<List<TicketsDomainEntity>>

    fun getAllOffers(): Flow<List<OffersDomainEntity>>

    fun getAllTicketsOffers(): Flow<List<TicketsOffersDomainEntity>>
}