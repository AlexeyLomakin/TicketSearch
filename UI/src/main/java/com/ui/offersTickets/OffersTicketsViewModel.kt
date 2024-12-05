package com.ui.offersTickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.domain.entitys.TicketsOffersDomainEntity
import com.domain.useCases.GetAllTicketsOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OffersTicketsViewModel @Inject constructor(
    getAllTicketsOffersUseCase: GetAllTicketsOffersUseCase
) : ViewModel() {
    val ticketsOffersData: LiveData<List<TicketsOffersDomainEntity>> =
        getAllTicketsOffersUseCase().asLiveData()
}
