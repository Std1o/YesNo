package com.stdio.yesno.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stdio.yesno.common.showSnackbar
import com.stdio.yesno.common.subscribeInUI
import com.stdio.yesno.common.viewBinding
import com.stdio.yesno.presentation.viewmodel.BankListViewModel
import com.stdio.yesno.R
import com.stdio.yesno.databinding.FragmentBankListBinding
import com.stdio.yesno.presentation.ui.adapter.BanksAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankListFragment : Fragment(R.layout.fragment_bank_list) {

    private val viewModel by viewModels<BankListViewModel>()
    private val binding by viewBinding(FragmentBankListBinding::bind)
    lateinit var adapter: BanksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BanksAdapter {
            showSnackbar(getString(R.string.connect_bank_placeholder, it))
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter.setDataList(it)
        }
    }
}