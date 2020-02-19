package com.tkuhn.recipefinder.ui.main.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.databinding.FragmentDetailBinding
import com.tkuhn.recipefinder.ui.BaseFragment
import com.tkuhn.recipefinder.utils.addBackArrow
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.getStateViewModel
import org.koin.core.parameter.parametersOf

class DetailFragment :
    BaseFragment<DetailViewModel, FragmentDetailBinding>(R.layout.fragment_detail) {

    private lateinit var args: DetailFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        args = navArgs<DetailFragmentArgs>().value
        super.onCreate(savedInstanceState)
    }

    override fun viewModel(): DetailViewModel = getStateViewModel { parametersOf(args.forecast) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        vToolbarDetail.addBackArrow()
    }
}