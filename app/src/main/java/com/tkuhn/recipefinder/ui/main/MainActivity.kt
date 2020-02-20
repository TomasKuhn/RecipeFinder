package com.tkuhn.recipefinder.ui.main

import android.os.Bundle
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.databinding.ActivityMainBinding
import com.tkuhn.recipefinder.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity :
    BaseActivity<MainActivityViewModel, ActivityMainBinding>(R.layout.activity_main) {

    override fun viewModel(): MainActivityViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = vm
    }
}