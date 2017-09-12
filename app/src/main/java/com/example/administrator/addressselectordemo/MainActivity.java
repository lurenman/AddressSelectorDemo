package com.example.administrator.addressselectordemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_New_Activity_SelectCities;
    private TextView tv_AddressSelector;
    private TextView tv_AddressSelector1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }


    private void initViews() {
        tv_New_Activity_SelectCities = (TextView) findViewById(R.id.tv_New_Activity_SelectCities);
        tv_AddressSelector = (TextView) findViewById(R.id.tv_AddressSelector);
        tv_AddressSelector1 = (TextView) findViewById(R.id.tv_AddressSelector1);
    }
    private void initEvents() {
        tv_New_Activity_SelectCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,New_Activity_SelectCities.class);
                startActivity(intent);
            }
        });
        tv_AddressSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SelectCitiesActivity.class);
                startActivity(intent);
            }
        });
        tv_AddressSelector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SelectCitiesActivity1.class);
                startActivity(intent);
            }
        });
    }

}
