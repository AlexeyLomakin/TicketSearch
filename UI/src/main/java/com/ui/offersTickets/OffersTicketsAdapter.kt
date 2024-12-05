package com.ui.offersTickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.domain.entitys.TicketsOffersDomainEntity
import com.ui.R
import com.ui.databinding.OffersTicketsItemBinding

class OffersTicketsAdapter : ListAdapter<TicketsOffersDomainEntity, OffersTicketsAdapter.TicketsOffersViewHolder>(diffUtil)  {

    class TicketsOffersViewHolder(private val binding: OffersTicketsItemBinding) : ViewHolder(binding.root){
        fun bind(offers: TicketsOffersDomainEntity) {
            binding
            val circleDraw = when (offers.id){
                1 -> R.drawable.circle_red
                10 -> R.drawable.circle_blue
                else -> R.drawable.circle_white
            }
            binding.circleIv.setImageResource(circleDraw)
            binding.companyNameTv.text = offers.title
            "${offers.price} ₽>".also { binding.priceTv.text = it }
            binding.departureTimeTv.text = offers.time_range
                .joinToString(separator = " ") { it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsOffersViewHolder {
        val binding = OffersTicketsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketsOffersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketsOffersViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TicketsOffersDomainEntity>() {
            override fun areItemsTheSame(oldItem: TicketsOffersDomainEntity, newItem: TicketsOffersDomainEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TicketsOffersDomainEntity, newItem: TicketsOffersDomainEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}