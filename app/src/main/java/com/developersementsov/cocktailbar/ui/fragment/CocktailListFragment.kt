package com.developersementsov.cocktailbar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developersementsov.cocktailbar.App
import com.developersementsov.cocktailbar.MainActivity
import com.developersementsov.cocktailbar.R
import com.developersementsov.cocktailbar.adapter.CocktailsAdapter
import com.developersementsov.cocktailbar.adapter.DrinkItem
import com.developersementsov.cocktailbar.databinding.FragmentCocktailListBinding
import com.developersementsov.cocktailbar.ui.view_model.CocktailListViewModel
import com.developersementsov.cocktailbar.ui.view_model.SharedViewModel
import com.developersementsov.cocktailbar.ui.view_model.launchWhenStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CocktailListFragment : Fragment() {

    private lateinit var _binding: FragmentCocktailListBinding
    private val binding get() = _binding

    @Inject
    lateinit var viewModel: CocktailListViewModel

    @Inject
    lateinit var sharedViewModel: SharedViewModel

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
        _binding = FragmentCocktailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title =
            requireContext().getString(R.string.cocktail_list_fragment_title)

        observeDrinks()

        sharedViewModel.getSelectedIngredient()
            .filterNotNull()
            .onEach {
                viewModel.getByIngredient(it)
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

    private fun setAdapter() {
        adapter = CocktailsAdapter(requireContext(), drinks)
        binding.recyclerView.adapter = adapter
        adapter.onItemClick = { drinkItem ->
            sharedViewModel.shareSelectedCocktailId(drinkItem.idDrink)

            (activity as MainActivity).startFragmentByType(
                CocktailFragment::class.java.name
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable, null))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }
}