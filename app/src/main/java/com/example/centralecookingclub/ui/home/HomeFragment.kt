package com.example.centralecookingclub.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.databinding.FragmentHomeBinding
import com.example.centralecookingclub.ui.adapter.ItemRecyclerAdapter
import com.example.centralecookingclub.ui.detailledRecipe.DetailledRecipeFragment
import com.vikramezhil.droidspeech.DroidSpeech
import com.vikramezhil.droidspeech.OnDSListener
import com.vikramezhil.droidspeech.OnDSPermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), ItemRecyclerAdapter.ActionListener, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener, OnDSListener, OnDSPermissionsListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var recettesAdapter : ItemRecyclerAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var _recettes : MutableList<Recipe>
    lateinit var acResearch : AutoCompleteTextView
    lateinit var btnResearch : Button
    lateinit var swFaved : Switch
    var bool = 0


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var TAG = "TagMainDroidSpeech"
    private var rechercheDroidSpeech: DroidSpeech? = null
    var lastTimeWorking: Long = 0
    var bugTimeCheckHandler: Handler? = null
    var timeCheckRunnable: Runnable? = null
    private val TIME_RECHECK_DELAY = 5000
    private val TIME_OUT_DELAY = 4000



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

        
        _recettes = mutableListOf()
        val fragmentScope = CoroutineScope(Dispatchers.IO)
        recyclerView = _binding!!.itemRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recettesAdapter = ItemRecyclerAdapter(this,_recettes)
        recyclerView.adapter = recettesAdapter

        homeViewModel.recettes.observe(viewLifecycleOwner, Observer { recettes ->
            _recettes.clear()
            _recettes.addAll(recettes)

            recettesAdapter.notifyDataSetChanged()})

        Log.i("EDPMR", "HomeFragmentGetrecipes")

        acResearch = _binding!!.acResearch
        btnResearch = binding!!.btnResearch
        btnResearch.setOnClickListener(this)

        swFaved = binding!!.swFaved
        swFaved.setOnCheckedChangeListener(this)

        fragmentScope.launch {
            homeViewModel.getRecipes()
            homeViewModel.setAutocompleteTextView(acResearch)
        }

        ////////////Droid Speech
        //***Bug detection handlers
        bugTimeCheckHandler = Handler()
        timeCheckRunnable = object : Runnable {
            override fun run() {
                //Log.i(TAG, "Handler 1 running...");
                val timeDifference = System.currentTimeMillis() - lastTimeWorking
                if (timeDifference > TIME_OUT_DELAY) {
                    //Do action (restartActivity or restartListening)
                    Log.e(TAG, "Bug Detected ! Restart listener...")
                    stopSpeech()
                    startSpeech()
                }
                bugTimeCheckHandler!!.postDelayed(this, TIME_RECHECK_DELAY.toLong())
            }
        }
        bugTimeCheckHandler!!.postDelayed(timeCheckRunnable as Runnable, TIME_RECHECK_DELAY.toLong())
        rechercheDroidSpeech = DroidSpeech(this.activity, activity?.fragmentManager)
        rechercheDroidSpeech!!.setOnDroidSpeechListener(this)
        rechercheDroidSpeech!!.setShowRecognitionProgressView(false)
        rechercheDroidSpeech!!.setOneStepResultVerify(false)

        stopSpeechHomeFragment = view?.findViewById<View>(R.id.fragmentHomeStopButton) as Button
        //Let's start listening
        //Initiation de l'écoute
        startSpeech()
        //////////////////////////////
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(position: Int) {
        val action = HomeFragmentDirections.actionNavHomeToDetailledRecipeFragment(_recettes[position].id)
        //Log.d("CCC",_recettes[position].id.toString())
        stopSpeech()
        findNavController().navigate(action)
    }

    override fun onClick(v: View) {

        val fragmentScope = CoroutineScope(Dispatchers.IO)
        when(v.id){
            R.id.btnResearch -> {
                fragmentScope.launch {
                    val recipeNameReasearched = acResearch.text.toString()
                    homeViewModel.research(recipeNameReasearched, swFaved)
                }
            }
            R.id.fragmentHomeStopButton -> {
                rechercheDroidSpeech!!.closeDroidSpeechOperations()
                stopSpeech()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {

        val fragmentScope = CoroutineScope(Dispatchers.IO)
        when(buttonView.id){
            R.id.swFaved -> {
                fragmentScope.launch {
                    val recipeNameReasearched = acResearch.text.toString()
                    homeViewModel.research(recipeNameReasearched, swFaved)
                }
            }
        }

    }

    ////////


    override fun onDroidSpeechSupportedLanguages(
        currentSpeechLanguage: String,
        supportedSpeechLanguages: List<String>
    ) {
        Log.i(TAG, "Supported speech languages = $supportedSpeechLanguages")
        if (supportedSpeechLanguages.contains("fr-FR")) {
            // Setting the droid speech preferred language as french
            // Définir la langue préférée du discours de droid speech en français
            rechercheDroidSpeech!!.setPreferredLanguage("fr-FR")
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

    @ExperimentalStdlibApi
    override fun onDroidSpeechFinalResult(finalSpeechResult: String) {
        if (finalSpeechResult == "Recherche"
            || finalSpeechResult.lowercase().contains("recherche")
        ) {
            btnResearch.performClick()
            bool = 1
            acResearch.setText("Recherche en cours")
        }

        if (finalSpeechResult == "Chercher"
            || finalSpeechResult.lowercase().contains("chercher")
        ) {
            val fragmentScopee = CoroutineScope(Dispatchers.IO)
            fragmentScopee.launch {
                homeViewModel.research(acResearch.text.toString(), swFaved)
            }
        } else if (bool == 1){
            acResearch.setText(finalSpeechResult)
        }
    }

    override fun onDroidSpeechClosedByUser() {
        //Permet de fermer Droid Speech
    }

    override fun onDroidSpeechError(errorMsg: String) {
        // Speech error
        //Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        Log.i(TAG, "Error $errorMsg")
        stopSpeech()
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
            }
            stopSpeech()
        }
    }
    private fun startSpeech() {
        rechercheDroidSpeech!!.startDroidSpeechRecognition()

    }

    private fun stopSpeech() {
        rechercheDroidSpeech!!.closeDroidSpeechOperations()
        stopSpeechHomeFragment!!.visibility = View.GONE
    }

    companion object {
        var stopSpeechHomeFragment: Button? = null
    }
}