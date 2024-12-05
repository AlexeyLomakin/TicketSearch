package com.data

import com.data.models.Mapper
import com.data.models.OffersResponse
import com.data.models.TicketsOffersResponse
import com.data.models.TicketsResponse
import com.domain.entitys.OffersDomainEntity
import com.domain.entitys.TicketsDomainEntity
import com.domain.entitys.TicketsOffersDomainEntity
import com.domain.TicketsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(
    private val ticketsService: TicketsApi,
    private val offersResponseMapper: Mapper<OffersResponse.Offer, OffersDomainEntity>,
    private val ticketsMapper: Mapper<TicketsResponse.Tickets, TicketsDomainEntity>,
    private val ticketsOffersResponseMapper: Mapper<TicketsOffersResponse.TicketsOffer, TicketsOffersDomainEntity>
) : TicketsRepository {

    override fun getAllTickets(): Flow<List<TicketsDomainEntity>> = flow {
        val response: Response<TicketsResponse> = ticketsService.getAllTickets()
        if (response.isSuccessful) {
            val tickets = response.body()?.tickets ?: emptyList()
            val ticketsDomainEntities = tickets.map {
                ticketsMapper.map(it)
            }
            emit(ticketsDomainEntities)
        }
    }

    override fun getAllTicketsOffers(): Flow<List<TicketsOffersDomainEntity>> = flow {
        val response: Response<TicketsOffersResponse> = ticketsService.getAllTicketOffers()
        if (response.isSuccessful) {
            val ticketsOffers = response.body()?.tickets_offers ?: emptyList()
            val ticketsOffersEntities = ticketsOffers.map {
                ticketsOffersResponseMapper.map(it)
            }
            emit(ticketsOffersEntities)
        }
    }

    override fun getAllOffers(): Flow<List<OffersDomainEntity>> = flow {
        val response: Response<OffersResponse> = ticketsService.getAllOffers()
        if (response.isSuccessful) {
            val offers = response.body()?.offers ?: emptyList()
            val offersDomainEntities = offers.map {
                offersResponseMapper.map(it)
            }
            emit(offersDomainEntities)
        }
    }
}
