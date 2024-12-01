package com.data

data class TicketsOffersResponse(
    val tickets_offers: List<TicketsOffer>
) {
    data class TicketsOffer(
        val id : Int,
        val title: String,
        val time_range: List<String>,
        val price: Price
    ) {
        data class Price(val value: Int)
    }
}
