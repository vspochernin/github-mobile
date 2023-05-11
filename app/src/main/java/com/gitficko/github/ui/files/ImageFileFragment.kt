package com.gitficko.github.ui.files

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gitficko.github.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ImageFileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        return inflater.inflate(R.layout.fragment_image_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageFileUrl = requireArguments().getString("imageFileUrl") ?: return
        val imageFileContentImageView: ImageView = view.findViewById(R.id.imageFileContent)

        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = BitmapFactory.decodeStream(URL(imageFileUrl).openStream())
            withContext(Dispatchers.Main) {
                imageFileContentImageView.setImageBitmap(bitmap)
            }
        }

        val toolbar = view.findViewById<Toolbar>(R.id.image_file_toolbar)
        toolbar.setTitle(requireArguments().getString("imageFileName"))
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

}
