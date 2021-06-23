package com.example.centralecookingclub.ui.detailledRecipe

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.MainActivity
import com.example.centralecookingclub.R
import com.example.centralecookingclub.SuiviRecette
import com.example.centralecookingclub.data.model.*
import com.example.centralecookingclub.databinding.FragmentDetailledRecipeBinding
import com.example.centralecookingclub.ui.adapter.EditRecipeRecyclerAdapter
import com.example.centralecookingclub.ui.adapter.IngAndStepRecyclerAdapter
import com.google.android.material.snackbar.Snackbar
import com.vikramezhil.droidspeech.DroidSpeech
import com.vikramezhil.droidspeech.OnDSListener
import com.vikramezhil.droidspeech.OnDSPermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailledRecipeFragment : Fragment(), IngAndStepRecyclerAdapter.ActionListener, View.OnClickListener,
    OnDSListener, OnDSPermissionsListener {


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
    private val fragmentScope = CoroutineScope(Dispatchers.IO)
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var bdl: Bundle? = null

    ///////////// Variables droidSpeech /////////////
    var TAG = "TagDetailledRecipeFragment"
    var detailledRecipeDroidSpeech: DroidSpeech? = null
    var lastTimeWorking: Long = 0
    var bugTimeCheckHandler: Handler? = null
    var timeCheckRunnable: Runnable? = null
    private val TIME_RECHECK_DELAY = 5000
    private val TIME_OUT_DELAY = 4000
    //////////////////////////////////////////////////

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

        binding.playButton.setOnClickListener {
                /* view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            */
            suiviRecette()
        }

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
            }
        }

        ///////// DroidSpeech //////////////
        bugTimeCheckHandler = Handler()
        timeCheckRunnable = object : Runnable {
            override fun run() {
                //Log.i(TAG, "Handler 1 running...");
                val timeDifference = System.currentTimeMillis() - lastTimeWorking
                if (timeDifference > TIME_OUT_DELAY) {
                    //Do action (restartActivity or restartListening)
                    Log.e(TAG, "Bug Detected ! Restart listener...")
                    stopSpeech()
//modif                    MainActivity.stopSpeechMain!!.performClick()
                    startSpeech()
                }
                bugTimeCheckHandler!!.postDelayed(this, TIME_RECHECK_DELAY.toLong())
            }
        }
        bugTimeCheckHandler!!.postDelayed(timeCheckRunnable as Runnable, TIME_RECHECK_DELAY.toLong())
        detailledRecipeDroidSpeech = DroidSpeech(this.activity, activity?.fragmentManager)
        detailledRecipeDroidSpeech!!.setOnDroidSpeechListener(this)
        detailledRecipeDroidSpeech!!.setShowRecognitionProgressView(false)
        detailledRecipeDroidSpeech!!.setOneStepResultVerify(false)

        stopSpeechDetailledR = view?.findViewById<View>(R.id.fragmentDetailledRecipeStopButton) as Button
        stopSpeechDetailledR!!.setOnClickListener(this)

        //Let's start listening
        //Initiation de l'écoute
        startSpeech()
    }

    fun suiviRecette() {
        val suiviRecetteIntent = Intent(activity, SuiviRecette::class.java)
        bdl = Bundle()
        //Récupération des instructions short de la recette
        val id = detailledRecipeViewModel.recipe.value?.id
        bdl!!.putInt("idRecette", id!!)
        suiviRecetteIntent.putExtras(bdl!!)
        stopSpeech()
        detailledRecipeDroidSpeech!!.closeDroidSpeechOperations()
        startActivity(suiviRecetteIntent)
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

    ///////////// DroidSpeech /////////////////////

    override fun onDroidSpeechSupportedLanguages(
        currentSpeechLanguage: String,
        supportedSpeechLanguages: List<String>
    ) {
        Log.i(TAG, "Supported speech languages = $supportedSpeechLanguages")
        if (supportedSpeechLanguages.contains("fr-FR")) {
            // Setting the droid speech preferred language as french
            // Définir la langue préférée du discours de droid speech en français
            detailledRecipeDroidSpeech!!.setPreferredLanguage("fr-FR")
        }
        Log.i(TAG, "Current speech language = $currentSpeechLanguage")
    }

    override fun onDroidSpeechRmsChanged(rmsChangedValue: Float) {

        // Permet de visualiser des valeurs en nombre à chaque tonalité/ fréquence de la voix détécté
        Log.i(TAG, "Rms change value = $rmsChangedValue")
        lastTimeWorking = System.currentTimeMillis()
    }

    override fun onDroidSpeechLiveResult(liveSpeechResult: String) {
        // Permet de visualiser le mot détécté prédefinit
        Log.i(TAG, "Live speech result = $liveSpeechResult")
        // recherche = liveSpeechResult;
        // edtRecherche.setText("recherche");
    }

    // mots-clés
    @ExperimentalStdlibApi
    override fun onDroidSpeechFinalResult(finalSpeechResult: String) {
        if (finalSpeechResult == "Commencer"
            || finalSpeechResult.lowercase().contains("commencer")
        ) {
            stopSpeech()
            suiviRecette()
        }
    }

    override fun onDroidSpeechClosedByUser() {
        //Permet de fermer Droid Speech
      //  stopSpeechRecherche!!.visibility = View.GONE
      //  startSpeechRecherche!!.visibility = View.INVISIBLE
    }

    override fun onDroidSpeechError(errorMsg: String) {
        // Speech error
        //Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        Log.i(TAG, "Error $errorMsg")
        stopSpeech()
//modif            MainActivity.stopSpeechMain!!.performClick()

    }

    override fun onDroidSpeechAudioPermissionStatus(
        audioPermissionGiven: Boolean,
        errorMsgIfAny: String
    ) {
        if (audioPermissionGiven) {
            startSpeech()
        } else {
            if (errorMsgIfAny != null) {
                // Permissions error
//                Toast.makeText(this, errorMsgIfAny, Toast.LENGTH_LONG).show()
            }
            stopSpeech()
        }
    }

    private fun startSpeech() {
        detailledRecipeDroidSpeech!!.startDroidSpeechRecognition()
        stopSpeechDetailledR!!.visibility = View.INVISIBLE
    }

    private fun stopSpeech() {
        detailledRecipeDroidSpeech!!.closeDroidSpeechOperations()
        stopSpeechDetailledR!!.visibility = View.GONE
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fragmentDetailledRecipeStopButton -> {
                detailledRecipeDroidSpeech!!.closeDroidSpeechOperations()
                stopSpeechDetailledR!!.visibility = View.GONE
            }
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
        }
    }
    companion object {
        var stopSpeechDetailledR: Button? = null
    }

}