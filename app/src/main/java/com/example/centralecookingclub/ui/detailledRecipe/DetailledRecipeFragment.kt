package com.example.centralecookingclub.ui.detailledRecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.centralecookingclub.databinding.FragmentDetailledRecipeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailledRecipeFragment : Fragment(){

    private val args : DetailledRecipeFragmentArgs by navArgs()
    private lateinit var detailledRecipeViewModel: DetailledRecipeViewModel
    private var _binding: FragmentDetailledRecipeBinding? = null
    private lateinit var titleTextView : TextView
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
        fragmentScope.launch {
            detailledRecipeViewModel.getRecipe(args.idRecipe)
            withContext(Main)
            {
                titleTextView.text = detailledRecipeViewModel.title.value
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}