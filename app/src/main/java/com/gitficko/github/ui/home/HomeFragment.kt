package com.gitficko.github.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gitficko.github.R
import com.gitficko.github.databinding.FragmentHomeBinding
import com.gitficko.github.utils.launchAndCollectIn
import com.gitficko.github.utils.resetNavGraph
import com.gitficko.github.utils.toast

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val logoutResponse = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.webLogoutComplete()
        } else {
            viewModel.webLogoutComplete()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getUserRep.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_repositories)
        }
        binding.logout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.getUserRep.isEnabled = !isLoading
            binding.userInfo.isVisible = !isLoading
        }

        viewModel.userInfoFlow.launchAndCollectIn(viewLifecycleOwner) { userInfo ->
            binding.userInfo.text = userInfo?.login
        }

        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }

        viewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            logoutResponse.launch(it)
        }

        viewModel.logoutCompletedFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().resetNavGraph(R.navigation.nav_graph)
        }
    }
}