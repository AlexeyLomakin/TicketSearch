package com.data

import com.data.models.OffersResponse
import com.data.models.TicketsOffersResponse
import com.data.models.TicketsResponse
import retrofit2.Response
import retrofit2.http.GET

interface TicketsApi {

    @GET("offers")
    suspend fun getAllOffers(): Response<OffersResponse>

    @GET("tickets")
    suspend fun getAllTickets(): Response<TicketsResponse>

    @GET("offers_tickets")
    suspend fun getAllTicketOffers(): Response<TicketsOffersResponse>
}