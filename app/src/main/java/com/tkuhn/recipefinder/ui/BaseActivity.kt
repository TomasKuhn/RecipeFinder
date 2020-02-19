package com.tkuhn.recipefinder.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.wada811.databinding.dataBinding

abstract class BaseActivity<VM : BaseViewModel, BINDING : ViewDataBinding>(@LayoutRes layoutResId: Int) :
    AppCompatActivity() {

    protected val binding: BINDING by dataBinding(layoutResId)
    lateinit var vm: VM

    abstract fun viewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = viewModel()
    }
}