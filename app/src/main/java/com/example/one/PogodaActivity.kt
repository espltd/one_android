package com.example.one

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class PogodaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pogoda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Получение JSON-объекта из Intent
        val jsonObject = intent.getStringExtra("jsonObject")
        val obj = JSONObject(jsonObject)

        val location = obj.getJSONObject("location")
        val current = obj.getJSONObject("current")

        val dataListView = findViewById<ListView>(R.id.dataListView)

        val rowData = arrayOf(
            "Регион: ${ location.getString("region") }",
            "Страна: ${ location.getString("country") }",
            "Время запроса: ${ location.getString("localtime") }",
            "Температура: ${ current.getString("temp_c") } °C",
            "Температура: ${ current.getString("temp_f") } °F",
            "Скорость ветра: ${ current.getString("wind_mph") } м/с",
            "Давление: ${ current.getString("pressure_in") }",
            "Осадки (мм): ${ current.getString("precip_mm") }",
            "Влажность: ${ current.getString("humidity") } %",
            "Облачность: ${ current.getString("cloud") } %",
            "По ощущениям: ${ current.getString("feelslike_c") } °C",
            "Порыв ветра: ${ current.getString("gust_mph") } м/с"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, rowData
        )
        dataListView.setAdapter(adapter);
    }

    fun onBack(){
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    

}