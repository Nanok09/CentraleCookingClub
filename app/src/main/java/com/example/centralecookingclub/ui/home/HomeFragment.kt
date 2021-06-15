package com.example.centralecookingclub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.data.Recette
import com.example.centralecookingclub.databinding.FragmentHomeBinding
import com.example.centralecookingclub.ui.adapter.ItemRecyclerAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var recettesAdapter : ItemRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var _recettes : MutableList<Recette>

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
        _recettes = mutableListOf(Recette("Tarte à la framboise","faite la tarte"),
                                    Recette("poulet curry","faite le poulet curry"))

        recyclerView = _binding!!.itemRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recettesAdapter = ItemRecyclerAdapter(_recettes)
        recyclerView.adapter = recettesAdapter

        homeViewModel.recettes.observe(viewLifecycleOwner, Observer { recettes ->
            _recettes.clear()
            _recettes.addAll(recettes)
            recettesAdapter.notifyDataSetChanged()})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}