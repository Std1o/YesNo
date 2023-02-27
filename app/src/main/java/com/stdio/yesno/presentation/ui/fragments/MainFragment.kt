package com.stdio.yesno.presentation.ui.fragments

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.URLUtil
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
import java.io.File


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        binding.btnGetAnswer.setOnClickListener {
            if (binding.swForced.isChecked) {
                viewModel.getYesNoResult(binding.spinner.selectedItem as String)
            } else {
                viewModel.getYesNoResult()
            }
        }
        binding.swForced.setOnCheckedChangeListener { _, isChecked ->
            binding.spinner.showIf(isChecked)
        }
        binding.btnDownload.setOnClickListener {
            downloadImage(viewModel.imageUrl)
        }
    }

    private fun downloadImage(url: String?) {
        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        val filename = url?.substring(url.lastIndexOf("/") + 1)
        val file =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + "/gifs/" + filename)
        Log.d("Environment", "Environment extraData=" + file.getPath())
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(filename)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(file))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
        downloadManager!!.enqueue(request)
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            viewModel.imageUrl = it.image
            binding.textResult.text = getString(R.string.text_result, it.answer)
            Glide.with(requireContext())
                .load(it.image)
                .placeholder(R.drawable.progress_animation)
                .into(binding.imageView)
        }
    }
}