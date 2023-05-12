package com.gitficko.github.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gitficko.github.R
import com.gitficko.github.databinding.FragmentProfileBinding
import com.gitficko.github.utils.profile.RoundedCornersTransformation
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var mViewModel: ProfileViewModel
    private lateinit var nameTextView: TextView
    private lateinit var loginTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var bioTextView: TextView
    private lateinit var followersTextView: TextView
    private lateinit var avatarImageView: ImageView
    private val binding by viewBinding(FragmentProfileBinding::bind)

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

        binding.getUserRep.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_repositories)
        }

        nameTextView = view.findViewById(R.id.nameTextView)
        loginTextView = view.findViewById(R.id.loginTextView)
        locationTextView = view.findViewById(R.id.locationTextView)
        bioTextView = view.findViewById(R.id.bioTextView)
        avatarImageView = view.findViewById(R.id.avatarImageView)
        followersTextView = view.findViewById(R.id.followersTextView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorSecondary)
        mViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        lifecycleScope.launch {
            mViewModel.loadUser()
            updateUI()
        }
    }

    private fun updateUI() {
        val user = mViewModel.user
        if (user != null) {
            if (user.name == null){
                nameTextView.visibility = View.GONE
            } else {
                nameTextView.text = user.name
            }

            loginTextView.text = user.login

            if (user.location == null){
                locationTextView.visibility = View.GONE
            } else {
                locationTextView.text = user.location
            }

            if (user.bio == null){
                bioTextView.visibility = View.GONE
            } else {
                bioTextView.text = user.bio!!.lineSequence()
                    .map { it.trim() }
                    .joinToString(" ")
            }

            val followers = user.followers
            followersTextView.text = "".plus(getString(R.string.followers_sign)).plus(followers)

            Picasso.get().load(user.avatarUrl)
                .transform(RoundedCornersTransformation(250, 0))
                .into(avatarImageView)
        }
    }
}
