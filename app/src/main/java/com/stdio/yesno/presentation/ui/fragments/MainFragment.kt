package com.stdio.yesno.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.stdio.yesno.common.subscribeInUI
import com.stdio.yesno.common.viewBinding
import com.stdio.yesno.presentation.viewmodel.MainViewModel
import com.stdio.yesno.R
import com.stdio.yesno.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        binding.button.setOnClickListener {
            viewModel.getYesNoResult()
        }
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            binding.textResult.text = getString(R.string.text_result, it.answer)
            Glide.with(requireContext())
                .load(it.image)
                .into(binding.imageView)
        }
    }
}