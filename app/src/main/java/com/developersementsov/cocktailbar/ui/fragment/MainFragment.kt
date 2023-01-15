package com.developersementsov.cocktailbar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.developersementsov.cocktailbar.MainActivity
import com.developersementsov.cocktailbar.R
import com.developersementsov.cocktailbar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var _binding: FragmentMainBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = requireContext().getString(R.string.cocktail_fragment_title)
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            buttonRandomCocktail.setOnClickListener {
                (activity as MainActivity).startFragmentByType(
                    CocktailFragment::class.java.name
                )
            }

            buttonSearchByIngredient.setOnClickListener {
                (activity as MainActivity).startFragmentByType(
                    IngredientFragment::class.java.name
                )
            }
        }
    }
}