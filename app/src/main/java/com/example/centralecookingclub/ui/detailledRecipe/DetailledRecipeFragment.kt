package com.example.centralecookingclub.ui.detailledRecipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.databinding.FragmentDetailledRecipeBinding
import com.example.centralecookingclub.ui.adapter.EditRecipeRecyclerAdapter
import com.example.centralecookingclub.ui.adapter.IngAndStepRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailledRecipeFragment : Fragment(), IngAndStepRecyclerAdapter.ActionListener {

    private val args : DetailledRecipeFragmentArgs by navArgs()
    private lateinit var detailledRecipeViewModel: DetailledRecipeViewModel
    private var _binding: FragmentDetailledRecipeBinding? = null
    private lateinit var titleTextView : TextView
    lateinit var ingAndStepAdapter : IngAndStepRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var listOfIngredient : MutableList<Ingredient>
    lateinit var listofStep : MutableList<Step>
    lateinit var listIngAndStep : MutableList<Any>
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
        val fragmentScope = CoroutineScope(Dispatchers.IO)
        titleTextView = _binding!!.titleDetailledRecipe
        listOfIngredient = mutableListOf()
        listofStep= mutableListOf()
        listIngAndStep = mutableListOf()
        recyclerView = _binding!!.ingAndStepRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this.activity)
        ingAndStepAdapter = IngAndStepRecyclerAdapter(this,listIngAndStep)
        recyclerView.adapter = ingAndStepAdapter

        detailledRecipeViewModel.ingredientsOfTheRecipe.observe(viewLifecycleOwner, Observer { ingredientsOfTheRecipe ->
            listOfIngredient.clear()
            listOfIngredient.addAll(ingredientsOfTheRecipe)
            UpdateRecycler() })

        detailledRecipeViewModel.stepsOfTheRecipe.observe(viewLifecycleOwner, Observer { stepOfRecipe ->
            listofStep.clear()
            listofStep.addAll(stepOfRecipe)
            UpdateRecycler()})

        fragmentScope.launch {
            detailledRecipeViewModel.getRecipe(args.idRecipe)
            withContext(Main)
            {
                titleTextView.text = detailledRecipeViewModel.title.value
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

}