package com.notkamui.android.tp1.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.notkamui.android.tp1.R
import com.notkamui.android.tp1.model.City
import java.util.*

class HelloWorldActivity : AppCompatActivity() {

    private var clickCount = 0
    private val cities: List<City> by lazy { City.loadFromRes(this, R.raw.topcities) }
    private lateinit var currentCity: City

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_world)

        Log.i(localClassName, "onCreate called")

        val helloText = findViewById<TextView>(R.id.helloText)
        currentCity = cities.random(System.nanoTime())
        helloText.text = currentCity.name

        val quitButton = findViewById<Button>(R.id.quitButton)
        quitButton.setOnClickListener {
            Log.i(localClassName, "Quit button clicked")
            val toast = Toast.makeText(this, "Bye bye!", Toast.LENGTH_SHORT)
            toast.show()

            startActivity(Intent(this, FortuneActivity::class.java))
            finish()
        }

        val worldMap = findViewById<ImageView>(R.id.worldMap)
        worldMap.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                Log.i(localClassName, "action_down: ${event.x}, ${event.y}")
                clickCount++
                helloText.setBackgroundColor(Color.rgb(clickCount % 10 / 10f, clickCount % 15 / 15f, clickCount % 20 / 20f))

                val (latitude, longitude) = worldMap.coordToLatLong(event.x, event.y)
                helloText.text = City.haversine(
                    currentCity.latitude.toDouble(), currentCity.longitude.toDouble(),
                    latitude.toDouble(), longitude.toDouble(),
                ).toString()

                currentCity = cities.random(System.nanoTime())
                helloText.text = "${helloText.text}\nNew city : ${currentCity.name}"
            }
            true
        }
    }
}

fun ImageView.coordToLatLong(x: Float, y: Float): Pair<Float, Float> {
    val latitude = 90 - y / height * 180
    val longitude = x / width * 360 - 180
    return latitude to longitude
}

fun <T> List<T>.random(seed: Long): T {
    require(isNotEmpty()) { "List is empty." }
    val random = Random(seed)
    return this[random.nextInt(this.size)]
}