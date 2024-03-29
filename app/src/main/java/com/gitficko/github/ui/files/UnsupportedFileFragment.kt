package com.gitficko.github.ui.files

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gitficko.github.R

class UnsupportedFileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        return inflater.inflate(R.layout.fragment_unsupported_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val unsupportedFileUrl = requireArguments().getString("unsupportedFileUrl") ?: return
        val unsupportedFileName = requireArguments().getString("unsupportedFileName") ?: return
        val downloadButton: Button = view.findViewById(R.id.downloadButton)

        downloadButton.setOnClickListener {
            downloadFile(unsupportedFileUrl, unsupportedFileName)
        }

        val toolbar = view.findViewById<Toolbar>(R.id.unsupported_file_toolbar)
        toolbar.setTitle(requireArguments().getString("unsupportedFileName"))
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun downloadFile(fileUrl: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(fileUrl))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}
