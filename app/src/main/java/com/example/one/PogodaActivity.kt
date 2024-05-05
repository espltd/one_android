package com.example.one

import android.content.Intent
import android.os.Bundle
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

        //данные с по направлению ветра с api приходят латиницей, таким образом
        //сделаем кириллическое обозначение ветра
        val wind_dir = when (current.getString("wind_dir")) {
            "N" -> getString(R.string.С)
            "S" -> getString(R.string.Ю)
            "W" -> getString(R.string.З)
            "E" -> getString(R.string.В)
            "NW" -> getString(R.string.СЗ)
            "SW" -> getString(R.string.ЮЗ)
            "NE" -> getString(R.string.СВ)
            "SE" -> getString(R.string.ЮВ)
            else -> ""
        }

        val rowData = arrayOf(
            getString(R.string.Region, location.getString("region")),
            getString(R.string.Country, location.getString("country")),
            getString(R.string.temperature_c, current.getString("temp_c")),
            getString(R.string.wind_speed, current.getString("wind_kph")),
            getString(R.string.wind_dir, wind_dir),
            getString(R.string.pressure_mb, current.getString("pressure_mb")),
            getString(R.string.precip_mm, current.getString("precip_mm")),
            getString(R.string.humidity, current.getString("humidity")),
            getString(R.string.cloud, current.getString("cloud")),
            getString(R.string.feelslike_c, current.getString("feelslike_c")),
            getString(R.string.gust_kph, current.getString("gust_kph"))
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