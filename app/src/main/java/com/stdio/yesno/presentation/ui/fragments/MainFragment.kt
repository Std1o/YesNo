package com.stdio.yesno.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.stdio.yesno.R
import com.stdio.yesno.common.showIf
import com.stdio.yesno.common.subscribeInUI
import com.stdio.yesno.common.viewBinding
import com.stdio.yesno.databinding.FragmentMainBinding
import com.stdio.yesno.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        binding.button.setOnClickListener {
            if (binding.swForced.isChecked) {
                viewModel.getYesNoResult(binding.spinner.selectedItem as String)
            } else {
                viewModel.getYesNoResult()
            }
        }
        binding.swForced.setOnCheckedChangeListener { _, isChecked ->
            binding.spinner.showIf(isChecked)
        }
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            binding.textResult.text = getString(R.string.text_result, it.answer)
            Glide.with(requireContext())
                .load(it.image)
                .placeholder(R.drawable.progress_animation)
                .into(binding.imageView)
        }
    }
}