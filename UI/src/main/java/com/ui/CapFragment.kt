package com.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ui.offers.OffersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CapFragment : Fragment(R.layout.fragment_cap) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.FragmentContainer, OffersFragment())
                .commit()
        }.isEnabled = true
    }
}