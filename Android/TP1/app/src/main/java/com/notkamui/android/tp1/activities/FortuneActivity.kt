package com.notkamui.android.tp1.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.notkamui.android.tp1.R
import org.json.JSONTokener


class FortuneActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fortune)

        val fortuneText = findViewById<TextView>(R.id.fortuneText)
        val queue = Volley.newRequestQueue(this)
        val url = "https://fortuneapi.herokuapp.com/"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response -> fortuneText.text = response.unescapeJSON() },
            { error -> fortuneText.text = "That didn't work because of this error: $error" }
        )

        queue.add(stringRequest)

        val quitButton = findViewById<Button>(R.id.realQuitButton)
        quitButton.setOnClickListener {
            finish()
        }
    }
}

fun String.unescapeJSON(): String = JSONTokener(this).nextValue().toString()