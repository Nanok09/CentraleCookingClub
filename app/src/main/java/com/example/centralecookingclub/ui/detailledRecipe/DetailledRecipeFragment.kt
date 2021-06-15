package com.example.centralecookingclub.ui.detailledRecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.centralecookingclub.databinding.FragmentDetailledRecipeBinding

class DetailledRecipeFragment : Fragment(){

    private lateinit var detailledRecipeViewModel: DetailledRecipeViewModel
    private var _binding: FragmentDetailledRecipeBinding? = null
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


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}