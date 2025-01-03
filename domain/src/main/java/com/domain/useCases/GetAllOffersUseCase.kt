package com.domain.useCases

import com.domain.entitys.OffersDomainEntity
import com.domain.TicketsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllOffersUseCase {
    operator fun invoke(): Flow<List<OffersDomainEntity>>
}

class GetAllOffersUseCaseImpl @Inject constructor(
    private val ticketsRepository: TicketsRepository
): GetAllOffersUseCase {
    override  fun invoke(): Flow<List<OffersDomainEntity>> {
        return ticketsRepository.getAllOffers()
    }
}