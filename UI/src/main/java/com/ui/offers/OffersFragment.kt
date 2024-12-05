package com.ui.offers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ui.BottomSheetFragment
import com.ui.R
import com.ui.databinding.OffersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue


@AndroidEntryPoint
class OffersFragment : Fragment(R.layout.offers_fragment) {

    private val viewBinding by viewBinding(OffersFragmentBinding::bind)
    private val adapter = OffersAdapter()
    private val viewModel: OffersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.rvOffers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.rvOffers.adapter = adapter

        viewModel.offersData.observe(viewLifecycleOwner) { offersData ->
            adapter.submitList(offersData)
        }

        viewBinding.arrival.apply {
            isFocusable = false
            isCursorVisible = false
            setOnClickListener {
                val departureText = viewBinding.departure.text.toString()
                val bottomSheetFragment = BottomSheetFragment.newInstance(departureText)
                bottomSheetFragment.show(
                    requireActivity().supportFragmentManager,
                    BottomSheetFragment.TAG
                )
            }
        }
    }
}
