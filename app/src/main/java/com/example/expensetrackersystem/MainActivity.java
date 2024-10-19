package com.example.expensetrackersystem;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackersystem.fragments.DashboardFragment;
import com.example.expensetrackersystem.fragments.ExpenseFragment;
import com.example.expensetrackersystem.fragments.IncomeFragment;
import com.example.expensetrackersystem.fragments.ProfileFragment;
import com.example.expensetrackersystem.fragments.SavingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashboard); // Default item

        // Load the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new DashboardFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.income) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new IncomeFragment()).commit();
            return true;
        } else if (itemId == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new DashboardFragment()).commit();
            return true;
        } else if (itemId == R.id.expense) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new ExpenseFragment()).commit();
            return true;
        } else if (itemId == R.id.saving) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new SavingFragment()).commit();
            return true;
        } else if (itemId == R.id.profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new ProfileFragment()).commit();
            return true;
        }
        return false;
    }
}
