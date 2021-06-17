package com.example.centralecookingclub.ui.gallery

import android.app.Activity
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
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.databinding.FragmentGalleryBinding
import com.example.centralecookingclub.ui.adapter.EditRecipeRecyclerAdapter
import com.example.centralecookingclub.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class GalleryFragment : Fragment(), EditRecipeRecyclerAdapter.ActionListener, View.OnClickListener {

    val REQUEST_CODE = 100
    private val CAT = "EDPMR"

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null
    lateinit var editRecipeAdapter : EditRecipeRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var _editRecipeList : MutableList<EditRecipe>
    lateinit var addImg: ImageView
    lateinit var addStepBtn : ImageView
    lateinit var btnvalidate : Button

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
        btnvalidate= binding.btnValidateRecipe
        addStepBtn=binding.addStepBtn
        addStepBtn.setOnClickListener(this)
        addImg=binding.imageofRecipe
        addImg.setOnClickListener(this)

        val root: View = binding.root
        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        galleryViewModel.editRecipeList.observe(viewLifecycleOwner, Observer { editRecipeList ->
            Log.d("CCC","observe")
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
            addImg.setImageURI(intent?.data)
        }
    }


    private fun addStep(){

        //We get the image from the hidden image view created by openGalleryForImage()
        var img = (addImg.drawable as BitmapDrawable).bitmap

        /*TODO() //get stepNumber, description, description short from ui and create Step object
        var step = Step(1, 1, "jojo", "koko", "", img)

        //Call Viewmodel
        galleryViewModel.addStep(step)*/

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.addStepBtn->{
                Log.d("CCC", galleryViewModel.editRecipeList.value?.size.toString())
                galleryViewModel.addEditRecipe()
            }
            R.id.imageofRecipe->{
                openGalleryForImage()
            }
        }
    }


}