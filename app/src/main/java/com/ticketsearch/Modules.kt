package com.ticketsearch

import android.content.Context
import com.data.LocalJsonInterceptor
import com.data.Mapper
import com.data.OffersMapper
import com.data.OffersResponse
import com.data.TicketOffersMapper
import com.data.TicketsApi
import com.data.TicketsMapper
import com.data.TicketsOffersResponse
import com.data.TicketsRepositoryImpl
import com.data.TicketsResponse
import com.domain.GetAllOffersUseCase
import com.domain.GetAllOffersUseCaseImpl
import com.domain.GetAllTicketsOffersUseCase
import com.domain.GetAllTicketsOffersUseCaseImpl
import com.domain.GetAllTicketsUseCase
import com.domain.GetAllTicketsUseCaseImpl
import com.domain.OffersDomainEntity
import com.domain.TicketsDomainEntity
import com.domain.TicketsOffersDomainEntity
import com.domain.TicketsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
abstract class Modules {

    @Binds
    abstract fun bindOffersMapper(offersMapper: OffersMapper): Mapper<OffersResponse.Offer, OffersDomainEntity>

    @Binds
    abstract fun bindTicketsMapper(ticketsMapper: TicketsMapper): Mapper<TicketsResponse.Tickets, TicketsDomainEntity>

    @Binds
    abstract fun bindTicketsOffersMapper(ticketOffersMapper: TicketOffersMapper): Mapper<TicketsOffersResponse.TicketsOffer, TicketsOffersDomainEntity>

    @Binds
    abstract fun bindAirTicketsOffersRepository(getAllOffers: TicketsRepositoryImpl): TicketsRepository

    @Binds
    abstract fun bindGetAllOffersUseCase(getAllOffers: GetAllOffersUseCaseImpl): GetAllOffersUseCase

    @Binds
    abstract fun bindGetAllTicketsUseCase(getAllOffers: GetAllTicketsUseCaseImpl): GetAllTicketsUseCase

    @Binds
    abstract fun bindGetAllTicketsOffersUseCase(getAllTicketsOffers: GetAllTicketsOffersUseCaseImpl): GetAllTicketsOffersUseCase

    companion object {
        @Provides
        @Singleton
        fun provideTicketsApi(@ApplicationContext context: Context): TicketsApi {
            val localJsonInterceptor = LocalJsonInterceptor(context)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(localJsonInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(TicketsApi::class.java)
        }
    }
}
