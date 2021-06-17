package com.example.centralecookingclub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.databinding.FragmentHomeBinding
import com.example.centralecookingclub.ui.adapter.ItemRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), ItemRecyclerAdapter.ActionListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var recettesAdapter : ItemRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var _recettes : MutableList<Recipe>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _recettes= mutableListOf(Recipe(4,"test",45,"test",5))

        val fragmentScope = CoroutineScope(Dispatchers.IO)
        recyclerView = _binding!!.itemRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recettesAdapter = ItemRecyclerAdapter(this,_recettes)
        recyclerView.adapter = recettesAdapter

        homeViewModel.recettes.observe(viewLifecycleOwner, Observer { recettes ->
            _recettes.clear()
            _recettes.addAll(recettes)

            recettesAdapter.notifyDataSetChanged()})

        fragmentScope.launch {
            homeViewModel.getRecipes()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(position: Int) {
        val action = HomeFragmentDirections.actionNavHomeToDetailledRecipeFragment(_recettes[position].id)
        //Log.d("CCC",_recettes[position].id.toString())
        findNavController().navigate(action)
    }
}