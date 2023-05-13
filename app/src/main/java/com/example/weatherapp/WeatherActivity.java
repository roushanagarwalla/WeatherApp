package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class WeatherActivity extends AppCompatActivity {
    String city;
    TextView CityName, CountryName, Temp, Desc, todaySunrise, todaySunset, day1Date, day1Temp, day1Desc, day1Sunrise, day1Sunset;
    TextView day2Date, day2Temp, day2Desc, day2Sunrise, day2Sunset;
    ProgressBar progressBar;
    // generate API key from https://weatherapi.com
    String APIKey = "ENTER_YOUR_API_KEY";
    String url;

    RecyclerView rv;
    Adapter rvAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> tempData;
    ArrayList<String> textData;
    ArrayList<String> hourData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        init();
        weatherMain();
    }

    void init(){
        CityName = findViewById(R.id.cityName);
        CountryName = findViewById(R.id.country);
        Temp = findViewById(R.id.temp);
        Desc = findViewById(R.id.desc);
        todaySunrise = findViewById(R.id.todaySunrise);
        todaySunset = findViewById(R.id.todaySunset);

        day1Date = findViewById(R.id.day1);
        day1Temp = findViewById(R.id.day1Temp);
        day1Desc = findViewById(R.id.day1Desc);
        day1Sunrise = findViewById(R.id.day1Sunrise);
        day1Sunset = findViewById(R.id.day1Sunset);

        day2Date = findViewById(R.id.day2);
        day2Temp = findViewById(R.id.day2Temp);
        day2Desc = findViewById(R.id.day2Desc);
        day2Sunrise = findViewById(R.id.day2Sunrise);
        day2Sunset = findViewById(R.id.day2Sunset);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        city = bundle.getString("cityName");
        rv = findViewById(R.id.rv);
        tempData = new ArrayList<String>();
        textData = new ArrayList<String>();
        hourData = new ArrayList<String>(Arrays.asList("01:00 AM", "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 AM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM", "12:00 PM"));
    }

    void weatherMain(){
        url = "https://api.weatherapi.com/v1/forecast.json?key="+APIKey+"&q="+city+"&days=3&aqi=no&alerts=no";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    progressBar.setVisibility(View.INVISIBLE);

                    JSONObject location = response.getJSONObject("location");
                    String city = location.getString("name");
                    String country = location.getString("country");
                    CityName.setText(city);
                    CountryName.setText(country);

                    JSONObject current = response.getJSONObject("current");
                    String temp = current.getString("temp_c");
                    String desc = current.getJSONObject("condition").getString("text");
                    temp = temp + " °C";
                    Temp.setText(temp);
                    Desc.setText(desc);
                    JSONObject firstDay = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourList = firstDay.getJSONArray("hour");
                    JSONObject astro = firstDay.getJSONObject("astro");
                    String today_sunrise = astro.getString("sunrise");
                    String today_sunset = astro.getString("sunset");
                    todaySunrise.setText(today_sunrise);
                    todaySunset.setText(today_sunset);
                    for(int i = 0; i<hourList.length(); i++){
                        JSONObject data = hourList.getJSONObject(i);
                        String temp_c = data.getString("temp_c");
                        String condition = data.getJSONObject("condition").getString("text");
                        tempData.add(temp_c);
                        textData.add(condition);
                    }
                    linearLayoutManager = new LinearLayoutManager(WeatherActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rvAdapter = new Adapter(tempData, textData, hourData);
                    rv.setLayoutManager(linearLayoutManager);
                    rv.setAdapter(rvAdapter);

                    JSONObject day1 = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1);
                    String day1_date = day1.getString("date");
                    String day1_mintemp = day1.getJSONObject("day").getString("mintemp_c");
                    String day1_maxtemp = day1.getJSONObject("day").getString("maxtemp_c");
                    String day1_temp = day1_mintemp + " - " + day1_maxtemp + " °C";
                    String day1_desc = day1.getJSONObject("day").getJSONObject("condition").getString("text");
                    String day1_sunrise = day1.getJSONObject("astro").getString("sunrise");
                    String day1_sunset = day1.getJSONObject("astro").getString("sunset");
                    day1Date.setText(day1_date);
                    day1Temp.setText(day1_temp);
                    day1Desc.setText(day1_desc);
                    day1Sunrise.setText(day1_sunrise);
                    day1Sunset.setText(day1_sunset);

                    JSONObject day2 = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2);
                    String day2_date = day2.getString("date");
                    String day2_mintemp = day2.getJSONObject("day").getString("mintemp_c");
                    String day2_maxtemp = day2.getJSONObject("day").getString("maxtemp_c");
                    String day2_temp = day2_mintemp + " - " + day2_maxtemp + " °C";
                    String day2_desc = day2.getJSONObject("day").getJSONObject("condition").getString("text");
                    String day2_sunrise = day2.getJSONObject("astro").getString("sunrise");
                    String day2_sunset = day2.getJSONObject("astro").getString("sunset");
                    day2Date.setText(day2_date);
                    day2Temp.setText(day2_temp);
                    day2Desc.setText(day2_desc);
                    day2Sunrise.setText(day2_sunrise);
                    day2Sunset.setText(day2_sunset);

                } catch (JSONException e){
                    Toast.makeText(WeatherActivity.this, "Json Parse Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null){
                    int statusCode = error.networkResponse.statusCode;
                    if(statusCode == 404){
                        Toast.makeText(WeatherActivity.this, "Please Enter a valid City", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(WeatherActivity.this, "Unidentified Error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(WeatherActivity.this, "Some error occurred, please check your Network Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(request);
    }
}


