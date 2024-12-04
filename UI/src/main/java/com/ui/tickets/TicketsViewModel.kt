package com.ui.tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.domain.entitys.TicketsDomainEntity
import com.domain.useCases.GetAllTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TicketsViewModel  @Inject constructor(
    getAllTicketsUseCase: GetAllTicketsUseCase
): ViewModel() {
    val  ticketsData: LiveData<List<TicketsDomainEntity>> = getAllTicketsUseCase().asLiveData()
}