package com.ticketsearch

import android.content.Context
import com.data.LocalJsonInterceptor
import com.data.models.Mapper
import com.data.models.OffersMapper
import com.data.models.OffersResponse
import com.data.models.TicketOffersMapper
import com.data.TicketsApi
import com.data.models.TicketsMapper
import com.data.models.TicketsOffersResponse
import com.data.TicketsRepositoryImpl
import com.data.models.TicketsResponse
import com.domain.useCases.GetAllOffersUseCase
import com.domain.useCases.GetAllOffersUseCaseImpl
import com.domain.useCases.GetAllTicketsOffersUseCase
import com.domain.useCases.GetAllTicketsOffersUseCaseImpl
import com.domain.useCases.GetAllTicketsUseCase
import com.domain.useCases.GetAllTicketsUseCaseImpl
import com.domain.entitys.OffersDomainEntity
import com.domain.entitys.TicketsDomainEntity
import com.domain.entitys.TicketsOffersDomainEntity
import com.domain.TicketsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Modules {

    @Binds
    @Singleton
    abstract fun bindOffersMapper(offersMapper: OffersMapper): Mapper<OffersResponse.Offer, OffersDomainEntity>

    @Binds
    @Singleton
    abstract fun bindTicketsMapper(ticketsMapper: TicketsMapper): Mapper<TicketsResponse.Tickets, TicketsDomainEntity>

    @Binds
    @Singleton
    abstract fun bindTicketsOffersMapper(ticketOffersMapper: TicketOffersMapper): Mapper<TicketsOffersResponse.TicketsOffer, TicketsOffersDomainEntity>

    @Binds
    @Singleton
    abstract fun bindTicketsRepository(ticketsRepositoryImpl: TicketsRepositoryImpl): TicketsRepository

    @Binds
    @Singleton
    abstract fun bindGetAllOffersUseCase(getAllOffersUseCaseImpl: GetAllOffersUseCaseImpl): GetAllOffersUseCase

    @Binds
    @Singleton
    abstract fun bindGetAllTicketsUseCase(getAllTicketsUseCaseImpl: GetAllTicketsUseCaseImpl): GetAllTicketsUseCase

    @Binds
    @Singleton
    abstract fun bindGetAllTicketsOffersUseCase(getAllTicketsOffersUseCaseImpl: GetAllTicketsOffersUseCaseImpl): GetAllTicketsOffersUseCase

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
