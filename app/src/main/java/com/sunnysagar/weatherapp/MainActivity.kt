package com.sunnysagar.weatherapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        window.statusBarColor = Color.parseColor("#1383c3")
       getJsonData(lat,long)

    }

    private fun getJsonData(lat: String?, long: String?) {
        val api_key = "960a45362c5849e2f5ea239aaf5cee9b"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${api_key}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
//                Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                setValues(response)
            },
            Response.ErrorListener{Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()})
        queue.add(jsonRequest)

    }

    @SuppressLint("SetTextI18n")
    private fun setValues(response: JSONObject) {
        city.text = response.getString("name")

        val lat = response.getJSONObject("coord").getString("lat")
        val long = response.getJSONObject("coord").getString("lon")
        coordinates.text = "${lat}, ${long}"

        weather.text=response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempo = response.getJSONObject("main").getString("temp")
        tempo = ((((tempo).toFloat()-273.15)).toInt()).toString()
        temp.text = "${tempo}°C"

        var mintemp = response.getJSONObject("main").getString("temp_min")
        mintemp = ((((mintemp).toFloat()-273.15)).toInt()).toString()
        min_temp.text = "$mintemp°C"

        var maxtemp = response.getJSONObject("main").getString("temp_max")
        maxtemp = ((ceil((maxtemp).toFloat()-273.15)).toInt()).toString()
        max_temp.text = "$maxtemp°C"

        pressure.text=response.getJSONObject("main").getString("pressure")
        humidity.text = response.getJSONObject("main").getString("humidity")
        wind.text = response.getJSONObject("wind").getString("speed")
        degree.text="Degree : " +response.getJSONObject("wind").getString("deg")
//        gust.text="Gust : " +response.getJSONObject("wind").getString("gust")

    }
}