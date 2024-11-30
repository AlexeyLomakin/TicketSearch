package com.ui

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ui.databinding.OffersFragmentBinding
import dagger.Binds
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OffersFragment: Fragment(R.layout.offers_fragment) {

    private val viewBinds by viewBinding(OffersFragmentBinding::bind)
}