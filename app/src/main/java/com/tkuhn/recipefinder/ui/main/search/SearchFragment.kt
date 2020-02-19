package com.tkuhn.recipefinder.ui.main.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.databinding.FragmentSearchBinding
import com.tkuhn.recipefinder.ui.BaseFragment
import com.tkuhn.recipefinder.utils.extensions.showSnackbar
import org.koin.androidx.viewmodel.ext.android.getStateViewModel

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(R.layout.fragment_search) {

    override fun viewModel(): SearchViewModel = getStateViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm

        vm.snackMessage.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }
    }
}