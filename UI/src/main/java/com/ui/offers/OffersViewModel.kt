package com.ui.offers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.domain.useCases.GetAllOffersUseCase
import com.domain.entitys.OffersDomainEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    getAllOffersUseCase: GetAllOffersUseCase
) : ViewModel() {
    val offersData : LiveData<List<OffersDomainEntity>> = getAllOffersUseCase().asLiveData()
}