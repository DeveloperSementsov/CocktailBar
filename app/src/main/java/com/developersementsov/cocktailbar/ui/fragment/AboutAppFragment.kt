package com.developersementsov.cocktailbar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.developersementsov.cocktailbar.MainActivity
import com.developersementsov.cocktailbar.R
import com.developersementsov.cocktailbar.databinding.FragmentAboutAppBinding
import com.developersementsov.cocktailbar.databinding.FragmentMainBinding


class AboutAppFragment : Fragment() {
    private lateinit var _binding: FragmentAboutAppBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = requireContext().getString(R.string.about_app_fragment_title)
    }

}