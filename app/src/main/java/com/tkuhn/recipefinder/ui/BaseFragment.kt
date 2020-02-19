package com.tkuhn.recipefinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.tkuhn.recipefinder.utils.showSnackbar
import com.wada811.databinding.dataBinding

abstract class BaseFragment<VM : BaseViewModel, BINDING : ViewDataBinding>(@LayoutRes layoutResId: Int) :
    Fragment() {

    protected val binding: BINDING by dataBinding(layoutResId)
    lateinit var vm: VM

    abstract fun viewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = viewModel().apply {
            snackMessage.observe(this@BaseFragment) {
                showSnackbar(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}