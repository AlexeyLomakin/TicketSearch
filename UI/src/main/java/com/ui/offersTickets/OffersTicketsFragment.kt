package com.ui.offersTickets

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.ui.BottomSheetFragment
import com.ui.R
import com.ui.databinding.OffersTicketsFragmentBinding
import com.ui.offers.OffersFragment
import com.ui.tickets.TicketsFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.getValue
import kotlin.toString

@AndroidEntryPoint
class OffersTicketsFragment : Fragment(R.layout.offers_tickets_fragment) {

    private val viewBinding by viewBinding(OffersTicketsFragmentBinding::bind)
    private val viewModel: OffersTicketsViewModel by viewModels()
    private val adapter = OffersTicketsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            handleIncomingData(bundle)
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            BottomSheetFragment.REQUEST_KEY,
            this
        ) { _, bundle ->
            handleIncomingData(bundle)
        }

        viewBinding.ticketRecommendations.layoutManager = LinearLayoutManager(requireContext())
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.setDividerColor(ContextCompat.getColor(requireContext(), R.color.gray2))

        viewBinding.ticketRecommendations.addItemDecoration(divider)
        viewBinding.ticketRecommendations.setPadding(16, 0, 16, 0)
        viewBinding.ticketRecommendations.adapter = adapter
        viewModel.ticketsOffersData.observe(viewLifecycleOwner) { ticketsOffersData ->
            adapter.submitList(ticketsOffersData)
        }
        viewBinding.ticketRecommendations.addOnScrollListener(ScrollListener())

        viewBinding.backBtn.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.FragmentContainer, OffersFragment())
                .commit()
        }

        viewBinding.departure.setOnClickListener {
            val arrivalText: String = viewBinding.arrival.text.toString()
            viewBinding.arrival.text = Editable.Factory
                .getInstance()
                .newEditable(viewBinding.departure.text)
            viewBinding.departure.text = Editable.Factory
                .getInstance()
                .newEditable(arrivalText)
        }

        val calendar = java.util.Calendar.getInstance()
        val spannable = getFormattedDate(calendar)
        viewBinding.departureDateBtn.text = spannable

        viewBinding.departureDateBtn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(java.util.Calendar.YEAR, year)
                    calendar.set(java.util.Calendar.MONTH, monthOfYear)
                    calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth)

                    val selectedSpannable = getFormattedDate(calendar)
                    viewBinding.departureDateBtn.text = selectedSpannable
                },
                calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        viewBinding.searchTicketsBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("departure", viewBinding.departure.text.toString())
                putString("arrival", viewBinding.arrival.text.toString())
                putString("departureDate", viewBinding.departureDateBtn.text.toString())
            }

            val fragment = TicketsFragment().apply {
                arguments = bundle
            }

            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.FragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun handleIncomingData(bundle: Bundle) {
        val departure = bundle.getString("departure") ?: bundle.getString(BottomSheetFragment.BUNDLE_KEY_DEPARTURE)
        val arrival = bundle.getString("arrival") ?: bundle.getString(BottomSheetFragment.BUNDLE_KEY_ARRIVAL)

        departure?.let { viewBinding.departure.text = Editable.Factory.getInstance().newEditable(it) }
        arrival?.let { viewBinding.arrival.text = Editable.Factory.getInstance().newEditable(it) }
    }

    private fun getFormattedDate(calendar: java.util.Calendar): SpannableString {
        val dateFormat = SimpleDateFormat("d MMM", Locale("ru"))
        val dayFormat = SimpleDateFormat("EE", Locale("ru"))

        val formattedDate = dateFormat.format(calendar.time)
        val formattedDay = dayFormat.format(calendar.time)
        val fullText = "$formattedDate, $formattedDay"

        return SpannableString(fullText).apply {
            setSpan(ForegroundColorSpan(Color.WHITE), 0, formattedDate.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(Color.GRAY), formattedDate.length, fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    inner class ScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollHorizontally(1)) {
                viewModel.ticketsOffersData
            }
        }
    }
}