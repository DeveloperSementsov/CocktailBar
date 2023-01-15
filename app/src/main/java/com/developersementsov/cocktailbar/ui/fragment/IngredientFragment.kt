package com.developersementsov.cocktailbar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.developersementsov.cocktailbar.App
import com.developersementsov.cocktailbar.MainActivity
import com.developersementsov.cocktailbar.R
import com.developersementsov.cocktailbar.databinding.FragmentIngredientBinding
import com.developersementsov.cocktailbar.ui.view_model.IngredientViewModel
import com.developersementsov.cocktailbar.ui.view_model.SharedViewModel
import com.developersementsov.cocktailbar.ui.view_model.launchWhenStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class IngredientFragment : Fragment() {
    @Inject
    lateinit var viewModel: IngredientViewModel

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    private lateinit var _binding: FragmentIngredientBinding
    private val binding get() = _binding

    private var ingredientsArray: Array<String?> = emptyArray()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title =
            requireContext().getString(R.string.search_by_ingredient_fragment_title)
        observeIngredients()
    }

    private fun observeIngredients() {
        viewModel.getIngredientsFlow()
            .filterNotNull()
            .onEach { ingredients ->
                ingredients.sort()
                ingredients.add(0, "Select Ingredient")
                ingredientsArray = ingredients.toTypedArray()
                setSpinner()
            }.launchWhenStarted(lifecycleScope)
    }

    private fun setSpinner() {
        val spinner = binding.spinner
        ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            ingredientsArray
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.selected { position ->
            if (position != 0) {
                sharedViewModel.shareSelectedIngredient(ingredientsArray[position].toString())

                (activity as MainActivity).startFragmentByType(
                    CocktailListFragment::class.java.name
                )
            } else {
                // do nothing
            }
        }
    }

    private fun Spinner.selected(action: (position: Int) -> Unit) {
        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                action(position)
            }
        }
    }
}