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

class RepositoryContentsFragment : Fragment(), RepositoryContentsAdapter.ContentClickListener {
    private lateinit var ownerLogin: String
    private lateinit var repoName: String
    private val repositoryContentsAdapter = RepositoryContentsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ownerLogin = arguments?.getString("ownerLogin") ?: ""
        repoName = arguments?.getString("repoName") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_contents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentsRecyclerView = view.findViewById<RecyclerView>(R.id.contentsRecyclerView)
        contentsRecyclerView.layoutManager = LinearLayoutManager(context)
        contentsRecyclerView.adapter = repositoryContentsAdapter
        loadContents("")
    }

    private fun loadContents(path: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val contents = Networking.githubApi.getRepositoryContents(ownerLogin, repoName, path)
            withContext(Dispatchers.Main) {
                repositoryContentsAdapter.submitList(contents)
            }
        }
    }

    override fun onContentClick(contentDto: ContentDto) {
        if (contentDto.type == "dir") {
            loadContents(contentDto.path)
        }
    }
}
