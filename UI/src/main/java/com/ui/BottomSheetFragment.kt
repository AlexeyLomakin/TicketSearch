package com.ui

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ui.databinding.BottomSheetFragmentBinding
import com.ui.offersTickets.OffersTicketsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment(R.layout.bottom_sheet_fragment) {

    private val viewBinding by viewBinding(BottomSheetFragmentBinding::bind)

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        dialog.setCancelable(true)
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            departure.text = arguments?.getString(ARG_DEPARTURE) ?: ""

            anywhereButton.setOnClickListener {
                arrival.text = Editable.Factory.getInstance().newEditable(getString(R.string.anywhere))
            }

            listOf(
                difficultRouteButton,
                weekendsButton,
                hotTicketsButton
            ).forEach { button ->
                button.setOnClickListener { navigateToCapFragment() }
            }

            istanbulCard.setOnClickListener {
                navigateToChoosingCountryFragment(getString(R.string.istanbul_name))
            }
            sochiCard.setOnClickListener {
                navigateToChoosingCountryFragment(getString(R.string.sochi_name))
            }
            phuketCard.setOnClickListener {
                navigateToChoosingCountryFragment(getString(R.string.phuket_name))
            }
        }
    }

    private fun navigateToCapFragment() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.FragmentContainer, CapFragment())
            .commit()
        dismiss()
    }

    private fun navigateToChoosingCountryFragment(arrivalText: String) {
        val departureText = viewBinding.departure.text.toString()

        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(
                BUNDLE_KEY_DEPARTURE to departureText,
                BUNDLE_KEY_ARRIVAL to arrivalText
            )
        )

        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.FragmentContainer, OffersTicketsFragment())
            .addToBackStack(null)
            .commit()

        dismiss()
    }

    companion object {
        const val TAG = "BottomSheetFragment"
        private const val ARG_DEPARTURE = "departure"
        const val REQUEST_KEY = "BottomSheetFragment:Request"
        const val BUNDLE_KEY_DEPARTURE = "departure"
        const val BUNDLE_KEY_ARRIVAL = "arrival"

        fun newInstance(departure: String): BottomSheetFragment {
            return BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DEPARTURE, departure)
                }
            }
        }
    }
}