package com.developersementsov.cocktailbar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.developersementsov.cocktailbar.App
import com.developersementsov.cocktailbar.R
import com.developersementsov.cocktailbar.databinding.FragmentCocktailBinding
import com.developersementsov.cocktailbar.ui.view_model.CocktailViewModel
import com.developersementsov.cocktailbar.ui.view_model.SharedViewModel
import com.developersementsov.cocktailbar.ui.view_model.launchWhenStarted
import com.developersementsov.data.entity.Drink
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CocktailFragment : Fragment() {

    @Inject
    lateinit var viewModel: CocktailViewModel

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    private lateinit var _binding: FragmentCocktailBinding
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title =
            requireContext().getString(R.string.cocktail_fragment_title)

        observeCocktail()

        sharedViewModel.getSelectedCocktailId()
            .onEach {
                viewModel.getCocktail(it)
            }.launchWhenStarted(lifecycleScope)
    }

    override fun onStop() {
        super.onStop()
        sharedViewModel.shareSelectedCocktailId(null)
    }

    private fun observeCocktail() {
        viewModel.getCocktailFlow()
            .filterNotNull()
            .onEach { drink ->
                setData(drink)
            }.launchWhenStarted(lifecycleScope)
    }

    private fun setData(drink: Drink) {
        requireActivity().title =
            drink.strDrinkName
        setImage(drink.strDrinkThumb)
        binding.category.text = drink.strCategory
        binding.name.text = drink.strDrinkName
        binding.type.text = drink.strAlcoholic
        binding.glass.text = drink.strGlass
        binding.instructions.text = drink.strInstructions
        setIngredients(drink)
    }

    private fun setImage(url: String) {
        Picasso.get().load(url).into(binding.image, object : Callback {
            override fun onSuccess() {
                binding.progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                // do nothing
            }
        }
        )
    }

    private fun setIngredients(drink: Drink) {
        with(binding) {
            drink.strIngredient1?.let {
                ingredient1.visibility = View.VISIBLE
                measure1.visibility = View.VISIBLE
                ingredient1.text = it
                measure1.text = drink.strMeasure1
            }
            drink.strIngredient2?.let {
                ingredient2.visibility = View.VISIBLE
                measure2.visibility = View.VISIBLE
                ingredient2.text = it
                measure2.text = drink.strMeasure2
            }
            drink.strIngredient3?.let {
                ingredient3.visibility = View.VISIBLE
                measure3.visibility = View.VISIBLE
                ingredient3.text = it
                measure3.text = drink.strMeasure3
            }
            drink.strIngredient4?.let {
                ingredient4.visibility = View.VISIBLE
                measure4.visibility = View.VISIBLE
                ingredient4.text = it
                measure4.text = drink.strMeasure4
            }
            drink.strIngredient5?.let {
                ingredient5.visibility = View.VISIBLE
                measure5.visibility = View.VISIBLE
                ingredient5.text = it
                measure5.text = drink.strMeasure5
            }
            drink.strIngredient6?.let {
                ingredient6.visibility = View.VISIBLE
                measure6.visibility = View.VISIBLE
                ingredient6.text = it
                measure6.text = drink.strMeasure6
            }
            drink.strIngredient7?.let {
                ingredient7.visibility = View.VISIBLE
                measure7.visibility = View.VISIBLE
                ingredient7.text = it
                measure7.text = drink.strMeasure7
            }
            drink.strIngredient8?.let {
                ingredient8.visibility = View.VISIBLE
                measure8.visibility = View.VISIBLE
                ingredient8.text = it
                measure8.text = drink.strMeasure8
            }
            drink.strIngredient9?.let {
                ingredient9.visibility = View.VISIBLE
                measure9.visibility = View.VISIBLE
                ingredient9.text = it
                measure9.text = drink.strMeasure9
            }
            drink.strIngredient10?.let {
                ingredient10.visibility = View.VISIBLE
                measure10.visibility = View.VISIBLE
                ingredient10.text = it
                measure10.text = drink.strMeasure10
            }
            drink.strIngredient11?.let {
                ingredient11.visibility = View.VISIBLE
                measure11.visibility = View.VISIBLE
                ingredient11.text = it
                measure11.text = drink.strMeasure11
            }
            drink.strIngredient12?.let {
                ingredient12.visibility = View.VISIBLE
                measure12.visibility = View.VISIBLE
                ingredient12.text = it
                measure12.text = drink.strMeasure12
            }
            drink.strIngredient13?.let {
                ingredient13.visibility = View.VISIBLE
                measure13.visibility = View.VISIBLE
                ingredient13.text = it
                measure13.text = drink.strMeasure13
            }
            drink.strIngredient14?.let {
                ingredient14.visibility = View.VISIBLE
                measure14.visibility = View.VISIBLE
                ingredient14.text = it
                measure14.text = drink.strMeasure14
            }
            drink.strIngredient15?.let {
                ingredient15.visibility = View.VISIBLE
                measure15.visibility = View.VISIBLE
                ingredient15.text = it
                measure15.text = drink.strMeasure15
            }
        }
    }
}