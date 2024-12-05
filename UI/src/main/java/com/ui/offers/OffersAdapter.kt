package com.ui.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.domain.entitys.OffersDomainEntity
import com.ui.databinding.OffersItemBinding
import com.ui.R

class OffersAdapter : ListAdapter<OffersDomainEntity, OffersAdapter.OffersViewHolder>(diffUtil) {

    private val imageResIds = mapOf(
        1 to R.drawable.offer_id1,
        2 to R.drawable.offer_id2,
        3 to R.drawable.offer_id3
    )

    class OffersViewHolder(private val binding: OffersItemBinding) : ViewHolder(binding.root) {

        fun bind(offers: OffersDomainEntity, imageResId: Int?) {
            binding.title.text = offers.title
            binding.town.text = offers.town
            "от ${offers.price} ₽".also { binding.price.text = it }
            imageResId?.toInt()?.let { binding.image.setImageResource(it) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        val binding = OffersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OffersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        val offer = getItem(position)
        val imageResId = imageResIds[offer.id]
        holder.bind(offer, imageResId?.toInt())
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OffersDomainEntity>() {
            override fun areItemsTheSame(oldItem: OffersDomainEntity, newItem: OffersDomainEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: OffersDomainEntity, newItem: OffersDomainEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}