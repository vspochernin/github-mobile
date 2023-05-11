package com.gitficko.github.ui.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gitficko.github.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import androidx.navigation.fragment.findNavController

class TextFileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        return inflater.inflate(R.layout.fragment_text_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textFileUrl = requireArguments().getString("textFileUrl") ?: return
        val textFileContentTextView: TextView = view.findViewById(R.id.textFileContent)

        CoroutineScope(Dispatchers.IO).launch {
            val textFileContent = URL(textFileUrl).readText()
            withContext(Dispatchers.Main) {
                textFileContentTextView.text = textFileContent
                // TODO: Добавить подсветку синтаксиса.
            }
        }

        val toolbar = view.findViewById<Toolbar>(R.id.text_file_toolbar)
        toolbar.setTitle(requireArguments().getString("textFileName"))
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

}
