package com.example.centralecookingclub

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.ui.detailledRecipe.DetailledRecipeFragment
import com.google.android.material.snackbar.Snackbar
import com.vikramezhil.droidspeech.DroidSpeech
import com.vikramezhil.droidspeech.OnDSListener
import com.vikramezhil.droidspeech.OnDSPermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuiviRecette : Activity(), OnDSListener, OnDSPermissionsListener {

    private val activityScope = CoroutineScope(Dispatchers.IO)
    var textStep: TextView? = null
    private val cccRepository by lazy { CCCRepository.newInstance(application)}
    private var steps : MutableList<Step>? = null
    private var nbSteps : Int? = null
    private var nStep = 0;
    ///////////// Variables droidSpeech /////////////
    var TAG = "TagSuiviRecette"
    private var suiviRecetteDroidSpeech: DroidSpeech? = null
    var lastTimeWorking: Long = 0
    var bugTimeCheckHandler: Handler? = null
    var timeCheckRunnable: Runnable? = null
    private val TIME_RECHECK_DELAY = 5000
    private val TIME_OUT_DELAY = 4000
    //////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.suivi_recette_layout)
        textStep = findViewById(R.id.instruction)
        var id: Int? =  intent.extras?.getInt("idRecette")
        Log.i("suiviRecette",id.toString())
        activityScope.launch {
            steps = cccRepository.getAllSteps(id!!)
            nbSteps = steps?.size
            Log.i("suiviRecette",steps!![0].descriptionShort)
            withContext(Dispatchers.Main){
                textStep!!.text = "1: " + steps!![0].descriptionShort
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
                    DetailledRecipeFragment.stopSpeechDetailledR!!.performClick()
                    startSpeech()
                }
                bugTimeCheckHandler!!.postDelayed(this, TIME_RECHECK_DELAY.toLong())
            }
        }
        bugTimeCheckHandler!!.postDelayed(timeCheckRunnable as Runnable, TIME_RECHECK_DELAY.toLong())
        suiviRecetteDroidSpeech = DroidSpeech(this, fragmentManager)
        suiviRecetteDroidSpeech!!.setOnDroidSpeechListener(this)
        suiviRecetteDroidSpeech!!.setShowRecognitionProgressView(false)
        suiviRecetteDroidSpeech!!.setOneStepResultVerify(false)

        Toast.makeText(this, "Naviguer en disant suivant ou précédent", Toast.LENGTH_LONG).show()
        //Let's start listening
        //Initiation de l'écoute
        startSpeech()
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
            suiviRecetteDroidSpeech!!.setPreferredLanguage("fr-FR")
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
        DetailledRecipeFragment.stopSpeechDetailledR!!.performClick() // a suppr si ça prends trop de temps
        if (finalSpeechResult == "Suivant"
            || finalSpeechResult.lowercase().contains("suivant")
        ) {
            etapeSuivante()
        }
        if (finalSpeechResult == "Précédent"
            || finalSpeechResult.lowercase().contains("précédent")
        ) {
            etapePrecedente()
        }
        if (finalSpeechResult == "Stop"
            || finalSpeechResult.lowercase().contains("stop")
        ) {
            stopSpeech()
            finish()
        }
    }

    private fun etapeSuivante() {
        if (nStep+1 < nbSteps!!) {
            nStep++
            textStep!!.text = (nStep+1).toString() + ": " + steps!![nStep].descriptionShort
        }
        else {
            Toast.makeText(this, "Fini ! (Dire stop pour revenir)", Toast.LENGTH_LONG).show()
        }
    }

    private fun etapePrecedente() {
        if (nStep > 0) {
            nStep--
            textStep!!.text = (nStep+1).toString() + ": " + steps!![nStep].descriptionShort
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
        DetailledRecipeFragment.stopSpeechDetailledR!!.performClick()

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
        suiviRecetteDroidSpeech!!.startDroidSpeechRecognition()
    }

    private fun stopSpeech() {
        suiviRecetteDroidSpeech!!.closeDroidSpeechOperations()
    }

}