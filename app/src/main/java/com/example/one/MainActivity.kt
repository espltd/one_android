package com.example.one

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.one.databinding.ActivityMainBinding
import org.json.JSONObject

const val API_KEY = "18cb6f191b5c4e589ba151051240305";

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onStart(view: View) {
        //читаем значение с поля город
        val fieldCity = findViewById<EditText>(R.id.inputCity)
        val city = fieldCity.text
        val getText = findViewById<TextView>(R.id.label_warning)
        if(!city.isEmpty()){
            getText.setText("")
            getResult("$city")
        }else{
            Log.d("info", "Не заполнено поле город!")
            getText.setText("Не заполнено поле город!")
        }
    }

    private fun getResult(city: String){
        val url = "http://api.weatherapi.com/v1/current.json" +
                "?key=$API_KEY&q=$city&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {
                response->
                val pogodaIntent = Intent(this, PogodaActivity::class.java)
                pogodaIntent.putExtra("jsonObject", "$response")
                startActivity(pogodaIntent)
            },
            {
                Log.d("MyLog", "error: $it")
            }
            )
        queue.add(stringRequest)
    }


}