package com.example.centralecookingclub.ui.gallery

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.*
import com.example.centralecookingclub.databinding.FragmentGalleryBinding
import com.example.centralecookingclub.ui.adapter.AddIngAdapter
import com.example.centralecookingclub.ui.adapter.EditRecipeRecyclerAdapter
import com.example.centralecookingclub.ui.adapter.IngAndStepRecyclerAdapter
import com.example.centralecookingclub.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception


class GalleryFragment : Fragment(), EditRecipeRecyclerAdapter.ActionListener, View.OnClickListener {

    val REQUEST_CODE = 100
    private val CAT = "EDPMR"

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null
    lateinit var editRecipeAdapter : EditRecipeRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var _editRecipeList : MutableList<EditRecipe>
    private var addImg: ImageView?=null
    lateinit var addStepBtn : ImageView
    lateinit var btnvalidate : Button
    lateinit var btnAddIng : ImageView
    lateinit var recipeName: EditText
    private val fragmentScope = CoroutineScope(Dispatchers.IO)
    private lateinit var dialogBox : Dialog
    private lateinit var dialogAdapter : AddIngAdapter
    private lateinit var recyclerDialog: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun alerter(s: String) {
        Log.i(CAT, s)
        val t = Toast.makeText(activity, s, Toast.LENGTH_SHORT)
        t.show()
    }

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
        //UI Components
        btnvalidate= binding.btnValidateRecipe
        addStepBtn=binding.addStepBtn
        recipeName = binding.ETname
        btnAddIng = binding.btnaddIng
        addImg=binding.imageofRecipe

        //OnClickListeners
        addStepBtn.setOnClickListener(this)
        btnvalidate.setOnClickListener(this)
        addImg?.setOnClickListener(this)

        fragmentScope.launch {
            val list = galleryViewModel.initializeListOfIngredients()
            dialogAdapter = AddIngAdapter(list)
            withContext(Main)
            {
                btnAddIng.setOnClickListener(this@GalleryFragment)
            }
        }
        galleryViewModel.editRecipeList.observe(viewLifecycleOwner, Observer { editRecipeList ->
            Log.d("CCC","observeEditRecipeList")
            _editRecipeList.clear()
            _editRecipeList.addAll(editRecipeList)
            editRecipeAdapter.notifyDataSetChanged()})
        _editRecipeList = mutableListOf()

        val fragmentScope = CoroutineScope(Dispatchers.IO)
        recyclerView = _binding!!.editRecipeRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this.activity)
        editRecipeAdapter = EditRecipeRecyclerAdapter(this,_editRecipeList)
        recyclerView.adapter = editRecipeAdapter

        galleryViewModel.steps.observe(viewLifecycleOwner) {
                viewState ->
            when(viewState){
                is GalleryViewModel.ViewState.Content -> {
                    Log.i(CAT, "yo")
                    viewState.steps.forEach {
                        Log.i(CAT, it.description)
                        val t = Toast.makeText(activity, it.description, Toast.LENGTH_SHORT)
                        t.show()
                    }
                }
                is GalleryViewModel.ViewState.Error -> {
                    Log.i(CAT, "${viewState.message}")

                    Toast.makeText(activity, "${viewState.message} ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemClicked(position: Int, stepImage : ImageView) {
        addImg=stepImage
        alerter("click, lancement openGalleryForImage()")
        openGalleryForImage()
    }



    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    //https://stackoverflow.com/questions/56678011/kotlin-how-do-i-save-the-contents-of-an-imageview-to-the-internal-external-memo
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            addImg?.setImageURI(intent?.data)
        }
    }

/*
    private fun addStep(){

        //We get the image from the hidden image view created by openGalleryForImage()
        var img = (addImg?.drawable as BitmapDrawable).bitmap

        /*TODO() //get stepNumber, description, description short from ui and create Step object
        var step = Step(1, 1, "jojo", "koko", "", img)

        //Call Viewmodel
        galleryViewModel.addStep(step)*/

    }
*/
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.addStepBtn->{
                galleryViewModel.editRecipeList.value?.forEach {
                    Log.d("CCC", it.toString())
                }
                editRecipeAdapter.descriptionList.add("")
                galleryViewModel.addEditRecipe()
            }
            R.id.btnValidateRecipe->{
                fragmentScope.launch {
                    val addImgBitmap = (addImg?.drawable as BitmapDrawable).bitmap
                    val recipeId = galleryViewModel.getLastId()+1
                    val recipe = Recipe(recipeId, recipeName.text.toString(),20, addImgBitmap, 4, "Matyas")
                    val recipeSteps = mutableListOf<Step>()
                    val recipeQuantities = mutableListOf<RecipeQuantity>()
                        Log.d("EDPMR", editRecipeAdapter.editRecipeList.size.toString())
                        val listSize = editRecipeAdapter.editRecipeList.size-1
                        for (i in 0..listSize){
                            Log.i("EDPMR", i.toString())
                            val editRecipe = editRecipeAdapter.editRecipeList[i]

                            if (editRecipe.type != 0){
                            val quantity = editRecipeAdapter.quantityList[i]

                                recipeQuantities.add(RecipeQuantity(editRecipe.ingredient.id, recipeId, "", quantity.toFloat()))
                            }
                    }

                    editRecipeAdapter.descriptionList.forEachIndexed() {index,element->
                        Log.d("CCC",index.toString())
                        Log.d("CCC",element)
                        recipeSteps.add(Step(recipeId,
                            index+1,
                            element,
                            element,
                            "test",
                            addImgBitmap))
                    }
                    /*
                    _editRecipeList.forEachIndexed{
                            index,element ->val view = recyclerView.layoutManager?.findViewByPosition(index)
                        if(element.type==0)
                        {
                            val textview = view!!.findViewById(R.id.numEtape) as TextView
                            val textViewDecription = view.findViewById(R.id.description) as TextView
                            recipeSteps.add(Step(recipeId,
                                textview.text.toString().toInt(),
                                textViewDecription.text.toString(),
                                textViewDecription.text.toString(),
                                "test",
                                addImgBitmap))
                        }
                    }*/
                    galleryViewModel.saveRecipeQuantities(recipeQuantities)
                    galleryViewModel.saveSteps(recipeSteps)
                    galleryViewModel.saveRecipeToDatabase(recipe)
                }

            }
            R.id.btnaddIng->{
                try {
                    this.dialogBox = Dialog(this.requireContext())
                    this.dialogBox.setContentView(R.layout.dialogbox)
                    recyclerDialog = dialogBox.findViewById(R.id.dialog_recycler_view)
                    val btnValidateIng=dialogBox.findViewById<Button>(R.id.validateIng)
                    recyclerDialog.layoutManager=LinearLayoutManager(this.requireContext())
                    recyclerDialog.adapter=dialogAdapter


                    btnValidateIng.setOnClickListener {

                        //pour chaque ingrédient avec une quantité non nulle, on l'ajoute a la liste en premiere position
                        dialogAdapter.arrQuantity.forEachIndexed() {index,element->
                            if(element != 0f)
                            {
                                editRecipeAdapter.quantityList.add(0,dialogAdapter.arrQuantity[index].toString())
                                galleryViewModel.addEditIng(dialogAdapter.listAddIng[index])
                            }
                        }
                        dialogBox.dismiss()
                    }
                    dialogBox.show()
                }
                catch (e : Exception)
                {
                    Log.d("CCC","no Context")
                }
            }
            R.id.imageofRecipe->{
                openGalleryForImage()
            }
        }
    }


}