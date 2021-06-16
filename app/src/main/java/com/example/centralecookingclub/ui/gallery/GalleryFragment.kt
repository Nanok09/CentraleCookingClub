package com.example.centralecookingclub.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.databinding.FragmentGalleryBinding
import com.example.centralecookingclub.ui.adapter.EditRecipeRecyclerAdapter
import com.example.centralecookingclub.ui.adapter.ItemRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryFragment : Fragment(), EditRecipeRecyclerAdapter.ActionListener {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null
    lateinit var editRecipeAdapter : EditRecipeRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var _editRecipeList : MutableList<EditRecipe>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        galleryViewModel.editRecipeList.observe(viewLifecycleOwner, Observer { editRecipeList ->
            _editRecipeList.clear()
            _editRecipeList.addAll(editRecipeList)
            editRecipeAdapter.notifyDataSetChanged()})

        galleryViewModel.editRecipeList.value= mutableListOf(EditRecipe())
        _editRecipeList = mutableListOf()

        val fragmentScope = CoroutineScope(Dispatchers.IO)
        recyclerView = _binding!!.editRecipeRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this.activity)
        editRecipeAdapter = EditRecipeRecyclerAdapter(this,_editRecipeList)
        recyclerView.adapter = editRecipeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(position: Int) {
        TODO("Not yet implemented")
    }
}