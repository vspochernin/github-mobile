package com.gitficko.github.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gitficko.github.R
import com.gitficko.github.databinding.FragmentRepositoryBinding
import com.gitficko.github.model.Repository
import com.gitficko.github.ui.repositories_list.RepositoryClickListener
import kotlinx.coroutines.launch

class RepositoryFragment : Fragment(), RepositoryClickListener {

    private val binding by viewBinding(FragmentRepositoryBinding::bind)
    private lateinit var ownerLogin: String
    private lateinit var repoName: String
    private lateinit var repoDescription: String
    private lateinit var ownerLoginTextView: TextView
    private lateinit var repoNameTextView: TextView
    private lateinit var repoDescriptionTextView: TextView

    companion object {
        fun newInstance(): RepositoryFragment {
            return RepositoryFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ownerLogin = arguments?.getString("ownerLogin").orEmpty()
        repoName = arguments?.getString("repoName").orEmpty()
        repoDescription = arguments?.getString("repoDescription").orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.colorSecondary)

        val view = inflater.inflate(R.layout.fragment_repository, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.arrow_back)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.browseCode.setOnClickListener {
            onRepositoryClick(Repository())
        }

        ownerLoginTextView = view.findViewById(R.id.ownerLoginTextView)
        repoNameTextView = view.findViewById(R.id.repoNameTextView)
        repoDescriptionTextView = view.findViewById(R.id.repoDescriptionTextView)
        R.id.nameTextView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.colorSecondary)
        lifecycleScope.launch {
            updateUI()
        }
    }

    private fun updateUI() {
        ownerLoginTextView.text = ownerLogin
        repoNameTextView.text = repoName
        repoDescriptionTextView.text = repoDescription
    }

    override fun onRepositoryClick(repository: Repository) {
        val action = RepositoryFragmentDirections
            .actionNavigationRepositoryToRepositoryContentsFragment(
                ownerLogin,
                repoName
            )
        findNavController().navigate(action)
    }
}
