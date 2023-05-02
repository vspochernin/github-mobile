package com.gitficko.github.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gitficko.github.R
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var mViewModel: ProfileViewModel
    private lateinit var nameTextView: TextView
    private lateinit var loginTextView: TextView

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.nameTextView)
        loginTextView = view.findViewById(R.id.loginTextView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        lifecycleScope.launch {
            mViewModel.loadUser()
            updateUI()
        }
    }

    private fun updateUI() {
        val user = mViewModel.user
        if (user != null) {
            nameTextView.text = user.login
            loginTextView.text = user.htmlUrl
        }
    }
}

