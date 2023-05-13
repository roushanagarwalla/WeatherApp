package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText cityName;
    Button submit;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = findViewById(R.id.cityName);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            city = cityName.getText().toString();
            if(city.isEmpty()){
                Toast.makeText(this, "Please enter a City", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("cityName", city);
                Intent intent = new Intent(this, WeatherActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}