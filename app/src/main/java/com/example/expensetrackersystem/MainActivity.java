package com.example.expensetrackersystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackersystem.fragments.Dashboard;
import com.example.expensetrackersystem.fragments.Expense;
import com.example.expensetrackersystem.fragments.Income;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.expensetrackersystem.R;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashboard); // Set default selected item

        // Load the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new Dashboard()).commit();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.income) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new Income()).commit();
            return true;
        } else if (itemId == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new Dashboard()).commit();
            return true;
        } else if (itemId == R.id.expense) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new Expense()).commit();
            return true;
        } else {
            return false;
        }
    }
}