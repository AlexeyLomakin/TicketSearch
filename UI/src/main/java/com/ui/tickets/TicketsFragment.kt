package com.ui.tickets

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ui.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.ui.databinding.TicketsFragmentBinding
import com.ui.offersTickets.OffersTicketsFragment

@AndroidEntryPoint
class TicketsFragment: Fragment(R.layout.tickets_fragment){
    private val viewBinding by viewBinding(TicketsFragmentBinding::bind)
    private val adapter = TicketsAdapter()
    private val viewModel : TicketsViewModel by viewModels()

    inner class SpacingItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.bottom = verticalSpaceHeight
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.ticketsRv.adapter = adapter
        viewBinding.ticketsRv.layoutManager = LinearLayoutManager(requireContext())

        arguments?.let { bundle ->
            val departure = bundle.getString("departure")
            val arrival = bundle.getString("arrival")
            val departureDate  = bundle.getString("departureDate")
            "$departure - $arrival".also { viewBinding.destinationTv.text = it }
            "$departureDate, 1 пассажир".also { viewBinding.departure.text = it }
        }

        viewBinding.backBtn.setOnClickListener {
            val departureText = arguments?.getString("departure") ?: ""
            val arrivalText = arguments?.getString("arrival") ?: ""

            val bundle = Bundle().apply {
                putString("departure", departureText)
                putString("arrival", arrivalText)
            }

            val offersTicketsFragment = OffersTicketsFragment().apply {
                arguments = bundle
            }

            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.FragmentContainer, offersTicketsFragment)
                .commit()
        }

        val spacingItemDecoration = SpacingItemDecoration(8)
        viewBinding.ticketsRv.addItemDecoration(spacingItemDecoration)

        viewModel.ticketsData.observe(viewLifecycleOwner) { ticketsData ->
            adapter.submitList(ticketsData)
        }
    }
}
