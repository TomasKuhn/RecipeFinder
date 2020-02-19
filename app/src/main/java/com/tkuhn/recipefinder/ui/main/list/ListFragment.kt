package com.tkuhn.recipefinder.ui.main.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.cache.ForecastCacheData
import com.tkuhn.recipefinder.databinding.FragmentListBinding
import com.tkuhn.recipefinder.ui.BaseFragment
import com.tkuhn.recipefinder.utils.navigateTo
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>(R.layout.fragment_list) {

    private val cachedForecast by inject<ForecastCacheData>()

    override fun viewModel(): ListViewModel = getViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm


        vm.forecast.observe(this) {
            Timber.d("Handle forecast $it in UI")
        }

        vm.detailEvent.observe(this) {
            navigateTo(ListFragmentDirections.actionListFragmentToDetailFragment(it))
        }

        cachedForecast.data.observe(this) {
            Timber.d("Observer gets: $it")
        }
    }
}