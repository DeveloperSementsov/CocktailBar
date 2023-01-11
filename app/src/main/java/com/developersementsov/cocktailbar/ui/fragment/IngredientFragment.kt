package com.developersementsov.cocktailbar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developersementsov.cocktailbar.App
import com.developersementsov.cocktailbar.R
import com.developersementsov.cocktailbar.adapter.CocktailsAdapter
import com.developersementsov.cocktailbar.adapter.DrinkItem
import com.developersementsov.cocktailbar.databinding.FragmentIngredientBinding
import com.developersementsov.cocktailbar.ui.view_model.IngredientViewModel
import com.developersementsov.cocktailbar.ui.view_model.launchWhenStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class IngredientFragment : Fragment() {
    @Inject
    lateinit var viewModel: IngredientViewModel

    private lateinit var _binding: FragmentIngredientBinding
    private val binding get() = _binding
    private var ingredients: Array<String?> = emptyArray()
    private var drinks = mutableListOf<DrinkItem>()
    private lateinit var adapter: CocktailsAdapter

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

        observeViewModel()
        setAdapter()
    }

    private fun setAdapter() {
        adapter = CocktailsAdapter(requireContext(), drinks)
        binding.recyclerView.adapter = adapter
        adapter.onItemClick = { drinkItem ->
            println("${drinkItem.drinkName} pressed")
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable, null))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun observeViewModel() {
        observeIngredients()
        observeDrinks()
    }

    private fun observeIngredients() {
        viewModel.getIngredientsFlow()
            .filterNotNull()
            .onEach { ing ->
                ing.sort()
                ing.add(0, "Select Ingredient")
                ingredients = ing.toTypedArray()
                setSpinner()
            }.launchWhenStarted(lifecycleScope)
    }

    private fun observeDrinks() {
        viewModel.getDrinksFlow()
            .filterNotNull()
            .onEach {
                drinks = it
                setAdapter()
            }.launchWhenStarted(lifecycleScope)
    }

    private fun setSpinner() {
        val spinner = binding.spinner
        ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            ingredients
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position != 0) {
                    binding.spinner.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    viewModel.getByIngredient(ingredients[position].toString())
                } else {
                    // do nothing
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // do nothing
            }
        }

    }

}