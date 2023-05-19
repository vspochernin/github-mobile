package com.gitficko.github.ui.files_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.ContentDto
import com.gitficko.github.remote.Networking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.gitficko.github.utils.Utils

class RepositoryContentsFragment : Fragment(), RepositoryContentsAdapter.ContentClickListener {
    private lateinit var ownerLogin: String
    private lateinit var repoName: String
    private val repositoryContentsAdapter = RepositoryContentsAdapter(this)
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ownerLogin = arguments?.getString("ownerLogin") ?: ""
        repoName = arguments?.getString("repoName") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        return inflater.inflate(R.layout.fragment_repository_contents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentsRecyclerView = view.findViewById<RecyclerView>(R.id.contentsRecyclerView)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.files_sign)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        contentsRecyclerView.layoutManager = LinearLayoutManager(context)
        contentsRecyclerView.adapter = repositoryContentsAdapter

        loadContents(arguments?.getString("path") ?: "")
    }

    private fun loadContents(path: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val contents = Networking.githubApi.getRepositoryContents(ownerLogin, repoName, path)
            val sortedContents = contents.sortedWith(
                compareBy({ it.type == "file" }, { it.name.lowercase() })
            )
            withContext(Dispatchers.Main) {
                repositoryContentsAdapter.submitList(sortedContents)
                toolbar.title = if (path.isNotEmpty()) path else getString(R.string.files_sign)
            }
        }
    }

    override fun onContentClick(contentDto: ContentDto) {
        val navController = findNavController()
        when {
            contentDto.type == "dir" -> {
                val bundle = bundleOf(
                    "path" to contentDto.path,
                    "ownerLogin" to ownerLogin,
                    "repoName" to repoName
                )
                navController.navigate(
                    R.id.repositoryContentsFragment,
                    bundle,
                    Utils.navOptionsToRight
                )
            }
            isTextFile(contentDto) -> {
                val bundle = bundleOf(
                    "textFileUrl" to contentDto.download_url,
                    "textFileName" to contentDto.name
                )
                navController.navigate(R.id.nav_textFileFragment, bundle, Utils.navOptionsToRight)
            }
            isImageFile(contentDto) -> {
                val bundle = bundleOf(
                    "imageFileUrl" to contentDto.download_url,
                    "imageFileName" to contentDto.name
                )
                navController.navigate(R.id.nav_imageFileFragment, bundle, Utils.navOptionsToRight)
            }
            else -> {
                val bundle = bundleOf(
                    "unsupportedFileUrl" to contentDto.download_url,
                    "unsupportedFileName" to contentDto.name
                )
                navController.navigate(
                    R.id.nav_unsupportedFileFragment,
                    bundle,
                    Utils.navOptionsToRight
                )
            }
        }
    }

    private fun isTextFile(contentDto: ContentDto): Boolean {
        val extension = contentDto.name.substringAfterLast('.', "")
        return extension in listOf("txt", "md", "java", "kt", "js", "html", "css", "json", "xml", "cpp")
    }

    private fun isImageFile(contentDto: ContentDto): Boolean {
        val extension = contentDto.name.substringAfterLast('.', "")
        return extension in listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")
    }
}
