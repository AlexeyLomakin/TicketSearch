package com.domain.useCases

import com.domain.entitys.TicketsOffersDomainEntity
import com.domain.TicketsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllTicketsOffersUseCase {
    operator fun invoke(): Flow<List<TicketsOffersDomainEntity>>
}

class GetAllTicketsOffersUseCaseImpl @Inject constructor(
    private val ticketsRepository: TicketsRepository
): GetAllTicketsOffersUseCase {
    override  fun invoke(): Flow<List<TicketsOffersDomainEntity>> {
        return ticketsRepository.getAllTicketsOffers()
    }
}