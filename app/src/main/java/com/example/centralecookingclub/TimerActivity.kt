package com.example.centralecookingclub

import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TimerActivity : AppCompatActivity() {

    private var edtTest:EditText? = null
    private var textViewTest: TextView? = null

    private val CAT: String = "EDPMR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_layout)


        edtTest = findViewById(R.id.edtTextTest)
        textViewTest = findViewById(R.id.textViewTest)


        edtTest?.afterTextChangedDelayed {
            Log.i(CAT, "afterTextChanged called ${edtTest?.text}")
            textViewTest?.text = edtTest?.text
        }


    }

    fun TextView.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(3000, 1500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        afterTextChanged.invoke(editable.toString())
                    }
                }.start()
            }
        })
    }



//    private val START_TIME_IN_MILLIS: Long = 600000
//    private var mTextViewCountDown: TextView? = null
//    private var mButtonStartPause: Button? = null
//    private var mButtonReset: Button? = null
//    private var mCountDownTimer: CountDownTimer? = null
//    private var mTimerRunning = false
//    private var mTimeLeftInMillis = START_TIME_IN_MILLIS
//    private var mEndTime: Long = 0
//
//    private val CAT: String = "EDPMR"
//
//    @Override
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.timer_layout)
//        Log.i(CAT, "onCreate")
//
//        mTextViewCountDown = findViewById(R.id.text_view_countdown)
//
//        mButtonStartPause = findViewById(R.id.button_start_pause)
//        mButtonReset = findViewById(R.id.button_reset)
//
//        mButtonStartPause?.setOnClickListener(this)
//        mButtonReset?.setOnClickListener(this)
//
//        updateCountDownText()
//
//    }
//
//    override fun onClick(v: View?) {
//        Log.i(CAT, "onClick")
//
//        when(v?.id){
//
//
//        R.id.button_start_pause ->
//            if (mTimerRunning){
//                pauseTimer()
//            } else{
//                startTimer()
//            }
//        R.id.button_reset ->
//            resetTimer()
//
//        }
//    }
//
//
//    fun startTimer(){
//        Log.i(CAT, "startTimer")
//        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis
//        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
//
//            override fun onTick(millisUntilFinished: Long) {
//                mTimeLeftInMillis = millisUntilFinished
//                updateCountDownText()
//            }
//
//            override fun onFinish() {
//                mTimerRunning = false
//                updateButtons()
//            }
//        }.start()
//
//        mTimerRunning = true
//        updateButtons()
//
//    }
//
//    fun pauseTimer(){
//        Log.i(CAT, "pauseTimer")
//        mCountDownTimer?.cancel()
//        mTimerRunning = false
//        updateButtons()
//    }
//
//    fun resetTimer(){
//        Log.i(CAT, "resetTimer")
//        mTimeLeftInMillis = START_TIME_IN_MILLIS
//        updateCountDownText()
//        updateButtons()
//    }
//
//    fun updateCountDownText(){
//        Log.i(CAT, "updateCountDownText")
//        val minutes: Int = ((mTimeLeftInMillis / 1000) / 60).toInt()
//        val seconds: Int = ((mTimeLeftInMillis /1000) % 60).toInt()
//        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
//
//        mTextViewCountDown?.setText(timeLeftFormatted)
//    }
//
//    fun updateButtons(){
//        Log.i(CAT, "updateButtons")
//        if(mTimerRunning){
//            mButtonReset?.visibility = View.INVISIBLE
//            mButtonStartPause?.setText("Pause")
//        } else{
//            mButtonStartPause?.setText("Start")
//
//            if (mTimeLeftInMillis < 1000){
//                mButtonStartPause?.visibility = View.INVISIBLE
//            } else {
//                mButtonStartPause?.visibility = View.VISIBLE
//            }
//
//            if (mTimeLeftInMillis < START_TIME_IN_MILLIS){
//                mButtonReset?.visibility = View.VISIBLE
//            } else{
//                mButtonReset?.visibility = View.INVISIBLE
//            }
//        }
//    }
//
//    @Override
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        Log.i(CAT, "onSaveInstanceState")
//        outState.putLong("millisLeft", mTimeLeftInMillis)
//        outState.putBoolean("timerRunning", mTimerRunning)
//        outState.putLong("endTime", mEndTime)
//    }
//
//
//    @Override
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        Log.i(CAT, "onRestoreInstanceState")
//        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft")
//        mTimerRunning = savedInstanceState.getBoolean("timerRunning")
//        updateCountDownText()
//        updateButtons()
//
//        if(mTimerRunning){
//            mEndTime = savedInstanceState.getLong("endTime")
//            mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
//            startTimer()
//        }
//    }
}