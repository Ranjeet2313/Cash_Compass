package com.example.expensetrackersystem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.PieChart;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.expenseAdapter2;
import com.example.expensetrackersystem.model.expenseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Expense extends Fragment {

    private TextView tvExpense;
    private RecyclerView rvExpense;
    private List<expenseModel> expenseModelList = new ArrayList<>();
    private String totalExpense;
    private expenseAdapter2 expenseAdapter;

    private ImageView iv_expensePie;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        init(view);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("Expense");

        iv_expensePie.setOnClickListener(v -> startActivity(new Intent(getContext(), PieChart.class)));

        loadExpenseData();

        return view;
    }

    private void loadExpenseData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseModelList.clear();
                int total = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    expenseModel model = snapshot.getValue(expenseModel.class);
                    expenseModelList.add(model);
                    total += Integer.parseInt(model.getAmount());
                }
                totalExpense = String.valueOf(total);
                tvExpense.setText("₹" + totalExpense);

                expenseAdapter = new expenseAdapter2(getContext(), expenseModelList);
                rvExpense.setLayoutManager(new LinearLayoutManager(getContext()));
                rvExpense.setHasFixedSize(true);
                rvExpense.setAdapter(expenseAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors
            }
        });
    }

    private void init(View view) {
        tvExpense = view.findViewById(R.id.tvExpense);
        rvExpense = view.findViewById(R.id.rvExpense);
        iv_expensePie = view.findViewById(R.id.iv_expensePie);
    }
}
