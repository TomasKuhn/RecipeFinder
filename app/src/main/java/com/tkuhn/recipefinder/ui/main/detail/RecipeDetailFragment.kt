package com.tkuhn.recipefinder.ui.main.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.databinding.FragmentRecipeDetailBinding
import com.tkuhn.recipefinder.ui.BaseFragment
import com.tkuhn.recipefinder.utils.extensions.addBackArrow
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class RecipeDetailFragment : BaseFragment<RecipeDetailViewModel, FragmentRecipeDetailBinding>(R.layout.fragment_recipe_detail) {

    private lateinit var args: RecipeDetailFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        args = navArgs<RecipeDetailFragmentArgs>().value
        super.onCreate(savedInstanceState)
    }

    override fun viewModel(): RecipeDetailViewModel = getViewModel { parametersOf(args.recipeId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        vToolbarRecipeDetail.addBackArrow()
    }
}