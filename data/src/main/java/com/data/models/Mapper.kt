package com.data.models

import com.domain.entitys.OffersDomainEntity
import com.domain.entitys.TicketsDomainEntity
import com.domain.entitys.TicketsOffersDomainEntity
import javax.inject.Inject

interface Mapper<I,O> {
    fun map(input: I): O
}

class OffersMapper  @Inject constructor()  : Mapper<OffersResponse.Offer, OffersDomainEntity>  {
    override fun map(input: OffersResponse.Offer): OffersDomainEntity  {
        return OffersDomainEntity(
            id = input.id,
            title = input.title,
            town = input.town,
            price = input.price.value,
        )
    }
}

class TicketOffersMapper   @Inject constructor()   : Mapper<TicketsOffersResponse.TicketsOffer, TicketsOffersDomainEntity>   {
    override fun map(input: TicketsOffersResponse.TicketsOffer): TicketsOffersDomainEntity   {
        return TicketsOffersDomainEntity(
            id = input.id,
            title = input.title,
            time_range = input.time_range,
            price = input.price.value
        )
    }
}

class TicketsMapper @Inject constructor() : Mapper<TicketsResponse.Tickets, TicketsDomainEntity> {
    override fun map(input: TicketsResponse.Tickets): TicketsDomainEntity {
        return TicketsDomainEntity(
            id = input.id,
            badge = input.badge,
            price = input.price.value,
            provider_name = input.provider_name,
            company = input.company,
            departure = TicketsDomainEntity.Departure(
                town = input.departure.town,
                date = input.departure.date,
                airport = input.departure.airport
            ),
            arrival = TicketsDomainEntity.Arrival(
                town = input.arrival.town,
                date = input.arrival.date,
                airport = input.arrival.airport
            ),
            has_transfer = input.has_transfer,
            has_visa_transfer = input.has_visa_transfer,
            luggage = input.luggage?.let {
                TicketsDomainEntity.Luggage(
                    it.has_luggage
                )
            },
            hand_luggage = input.hand_luggage?.let {
                TicketsDomainEntity.HandLuggage(
                    has_hand_luggage = it.has_hand_luggage,
                    size = input.hand_luggage.size
                )
            },
            is_returnable = input.is_returnable,
            is_exchangable = input.is_exchangable
        )
    }
}