package com.example.centralecookingclub.ui.detailledRecipe

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.*
import com.example.centralecookingclub.databinding.FragmentDetailledRecipeBinding
import com.example.centralecookingclub.ui.adapter.EditRecipeRecyclerAdapter
import com.example.centralecookingclub.ui.adapter.IngAndStepRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailledRecipeFragment : Fragment(), IngAndStepRecyclerAdapter.ActionListener,
    View.OnClickListener {

    private val args : DetailledRecipeFragmentArgs by navArgs()
    private lateinit var detailledRecipeViewModel: DetailledRecipeViewModel
    private var _binding: FragmentDetailledRecipeBinding? = null
    private lateinit var titleTextView : TextView
    private lateinit var nbPersonneTextView : TextView
    private lateinit var image :ImageView
    lateinit var ingAndStepAdapter : IngAndStepRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var listOfIngredient : MutableList<Ingredient>
    lateinit var quantityRecipe : MutableList<RecipeQuantity>
    lateinit var listofStep : MutableList<Step>
    lateinit var listIngAndStep : MutableList<Any>
    lateinit var icPlus : ImageView
    lateinit var icMinus : ImageView
    lateinit var favoriteCheckBox: CheckBox
    private val fragmentScope = CoroutineScope(Dispatchers.IO)
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        detailledRecipeViewModel =
            ViewModelProvider(this).get(DetailledRecipeViewModel::class.java)

        _binding = FragmentDetailledRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleTextView = binding.titleDetailledRecipe
        nbPersonneTextView = binding.nbPersonneOfRecipe
        quantityRecipe = mutableListOf()
        listOfIngredient = mutableListOf()
        listofStep= mutableListOf()
        listIngAndStep = mutableListOf()
        recyclerView = _binding!!.ingAndStepRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this.activity)
        ingAndStepAdapter = IngAndStepRecyclerAdapter(this,listIngAndStep,quantityRecipe)
        recyclerView.adapter = ingAndStepAdapter
        image=binding.imageDetailledRecipe
        icMinus=binding.icMinus
        icPlus=binding.icPlus
        favoriteCheckBox=binding.favoriteCheckBox

        detailledRecipeViewModel.ingredientsOfTheRecipe.observe(viewLifecycleOwner, Observer { ingredientsOfTheRecipe ->
            listOfIngredient.clear()
            listOfIngredient.addAll(ingredientsOfTheRecipe)
            UpdateRecycler() })

        detailledRecipeViewModel.stepsOfTheRecipe.observe(viewLifecycleOwner, Observer { stepOfRecipe ->
            listofStep.clear()
            listofStep.addAll(stepOfRecipe)
            UpdateRecycler()})

        detailledRecipeViewModel.quantityOfRecipe.observe(viewLifecycleOwner, Observer { quantity ->
            quantityRecipe.clear()
            quantityRecipe.addAll(quantity)
            UpdateRecycler()})

        detailledRecipeViewModel.nbPersonne.observe(viewLifecycleOwner,Observer{
            nbPersonne -> nbPersonneTextView.text = nbPersonne
        })

        fragmentScope.launch {
            detailledRecipeViewModel.getRecipe(args.idRecipe)
            withContext(Main)
            {
                image.setImageBitmap(detailledRecipeViewModel.recipe.value?.recipeImage)
                titleTextView.text = detailledRecipeViewModel.title.value
                nbPersonneTextView.text = detailledRecipeViewModel.nbPersonne.value
                icPlus.setOnClickListener(this@DetailledRecipeFragment)
                icMinus.setOnClickListener(this@DetailledRecipeFragment)
                favoriteCheckBox.setOnClickListener(this@DetailledRecipeFragment)
                favoriteCheckBox.isChecked = detailledRecipeViewModel.recipe.value!!.faved == 1
            }
        }
    }

    private fun UpdateRecycler() {
        listIngAndStep.clear()
        listIngAndStep.addAll(listOfIngredient)
        listIngAndStep.addAll(listofStep)
        ingAndStepAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.ic_plus->{
                var nb =detailledRecipeViewModel.nbPersonne.value?.toInt()
                if(nb==null)nb=0
                nb++
                detailledRecipeViewModel.nbPersonne.value=nb.toString()
                fragmentScope.launch {
                    detailledRecipeViewModel.updateQuantity(nb!!)
                }
            }
            R.id.ic_minus->{
                var nb =detailledRecipeViewModel.nbPersonne.value?.toInt()
                if(nb==null)nb=0
                if(nb!=0)nb--
                detailledRecipeViewModel.nbPersonne.value=nb.toString()
                fragmentScope.launch {
                    detailledRecipeViewModel.updateQuantity(nb)
                }
            }
            R.id.favoriteCheckBox->{
                fragmentScope.launch {
                    val recipe = detailledRecipeViewModel.recipe.value!!
                    detailledRecipeViewModel.changeFaved(recipe)
                    Log.i("Test", recipe.toString())
                }
            }
        }
    }

}