package com.ui.tickets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.domain.entitys.TicketsDomainEntity
import com.ui.R
import com.ui.databinding.TicketsItemsBinding
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Locale

class TicketsAdapter : ListAdapter<TicketsDomainEntity, TicketsAdapter.TicketsViewHolder>(diffUtil) {

    class TicketsViewHolder(private val binding: TicketsItemsBinding) : ViewHolder(binding.root) {

        private fun parseTime(dateString: String?): String? {
            if (dateString.isNullOrEmpty()) return null
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            return try {
                val currentDate = format.parse(dateString)
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                timeFormat.format(currentDate!!)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        private fun calculateTravelTime(departure: String?, arrival: String?): String {
            if (departure.isNullOrEmpty() || arrival.isNullOrEmpty()) return "N/A"
            val departureFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val arrivalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            try {
                val departureDate = departureFormat.parse(departure)
                val arrivalDate = arrivalFormat.parse(arrival)
                if (departureDate != null && arrivalDate != null) {
                    val durationInMillis = arrivalDate.time - departureDate.time
                    val duration = Duration.ofMillis(durationInMillis)
                    val hours = duration.toHours() % 24
                    val minutes = duration.toMinutes() % 60
                    return "${hours}ч ${minutes}м"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "N/A"
        }

        fun bind(offers: TicketsDomainEntity) {
            offers.badge?.takeIf { it.isNotEmpty() }?.let {
                binding.badge.visibility = View.VISIBLE
                binding.badge.text = it
            } ?: run {
                binding.badge.visibility = View.GONE
            }

            "${offers.price} ₽".also { binding.priceTv.text = it }

            if (!offers.has_transfer) {
                binding.transferTv.visibility = View.VISIBLE
                binding.transferTv.text = itemView.context.getString(R.string.without_transfer)
            } else {
                binding.transferTv.visibility = View.GONE
            }

            val departureTime = parseTime(offers.departure.date) ?: ""
            val arrivalTime = parseTime(offers.arrival.date) ?: ""

            "$departureTime - $arrivalTime".also { binding.departureTimeTv.text = it }

            val travelTime = calculateTravelTime(offers.departure.date, offers.arrival.date)
            binding.flightTimeTv.text = travelTime

            binding.departureAirportTv.text = offers.departure.airport
            binding.arrivalAirportTv.text = offers.arrival.airport
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        val binding = TicketsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TicketsDomainEntity>() {
            override fun areItemsTheSame(oldItem: TicketsDomainEntity, newItem: TicketsDomainEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TicketsDomainEntity, newItem: TicketsDomainEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
