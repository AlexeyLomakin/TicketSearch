package com.domain.useCases

import com.domain.entitys.TicketsDomainEntity
import com.domain.TicketsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllTicketsUseCase {
    operator fun invoke(): Flow<List<TicketsDomainEntity>>
}

class GetAllTicketsUseCaseImpl @Inject constructor(
    private val ticketsRepository: TicketsRepository
): GetAllTicketsUseCase {
    override fun invoke(): Flow<List<TicketsDomainEntity>> {
        return ticketsRepository.getAllTickets()
    }
}