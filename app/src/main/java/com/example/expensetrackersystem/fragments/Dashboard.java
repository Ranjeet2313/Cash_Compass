package com.example.expensetrackersystem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.expenseAdapter;
import com.example.expensetrackersystem.adapter.incomeAdapter;
import com.example.expensetrackersystem.model.expenseModel;
import com.example.expensetrackersystem.model.incomeModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Fragment {

    private RecyclerView rv_income, rv_expense;
    private TextView tv_income, tv_expense;
    private FloatingActionButton mAddFab, mAddIncomeFab, mAddExpenseFab;
    private TextView addIncomeText, addExpenseText;
    private Boolean isAllFabsVisible;
    private incomeAdapter incomeAdapter;
    private expenseAdapter expenseAdapter;
    private List<incomeModel> incomeModelList = new ArrayList<>();
    private List<expenseModel> expenseModelList = new ArrayList<>();
    private String totalIncome, totalExpense;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(view);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());

        loadIncomeData();
        loadExpenseData();

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabsVisibility();
            }
        });

        mAddIncomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIncomeDialog();
            }
        });

        mAddExpenseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExpenseDialog();
            }
        });

        return view;
    }

    private void loadIncomeData() {
        databaseRef.child("Income").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                incomeModelList.clear();
                int total = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    incomeModel model = snapshot.getValue(incomeModel.class);
                    incomeModelList.add(model);
                    total += Integer.parseInt(model.getAmount());
                }
                totalIncome = String.valueOf(total);
                tv_income.setText("₹" + totalIncome);

                incomeAdapter = new incomeAdapter(getContext(), incomeModelList);
                rv_income.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                rv_income.setHasFixedSize(true);
                rv_income.setAdapter(incomeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void loadExpenseData() {
        databaseRef.child("Expense").addValueEventListener(new ValueEventListener() {
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
                tv_expense.setText("₹" + totalExpense);

                expenseAdapter = new expenseAdapter(getContext(), expenseModelList);
                rv_expense.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                rv_expense.setHasFixedSize(true);
                rv_expense.setAdapter(expenseAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void toggleFabsVisibility() {
        if (!isAllFabsVisible) {
            mAddIncomeFab.show();
            mAddExpenseFab.show();
            addExpenseText.setVisibility(View.VISIBLE);
            addIncomeText.setVisibility(View.VISIBLE);
            isAllFabsVisible = true;
        } else {
            mAddIncomeFab.hide();
            mAddExpenseFab.hide();
            addExpenseText.setVisibility(View.GONE);
            addIncomeText.setVisibility(View.GONE);
            isAllFabsVisible = false;
        }
    }

    private void showIncomeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View customLayout = getLayoutInflater().inflate(R.layout.income_add_litem, null);
        EditText et_income = customLayout.findViewById(R.id.et_incomeAmount);
        EditText et_type = customLayout.findViewById(R.id.et_incomeType);
        EditText et_note = customLayout.findViewById(R.id.et_incomeNote);
        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);
        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());
        btn_save.setOnClickListener(v -> {
            String amount = et_income.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_income.setError("Empty amount");
            } else if (type.isEmpty()) {
                et_type.setError("Empty type");
            } else if (note.isEmpty()) {
                et_note.setError("Empty note");
            } else {
                String key = databaseRef.child("Income").push().getKey();
                incomeModel income = new incomeModel(amount, type, note, String.valueOf(date));
                databaseRef.child("Income").child(key).setValue(income);
                alertDialog.dismiss();
            }
        });
    }

    private void showExpenseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View customLayout = getLayoutInflater().inflate(R.layout.expense_add_item, null);
        EditText et_income = customLayout.findViewById(R.id.et_incomeAmount);
        EditText et_type = customLayout.findViewById(R.id.et_incomeType);
        EditText et_note = customLayout.findViewById(R.id.et_incomeNote);
        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);
        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());
        btn_save.setOnClickListener(v -> {
            String amount = et_income.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_income.setError("Empty amount");
            } else if (type.isEmpty()) {
                et_type.setError("Empty type");
            } else if (note.isEmpty()) {
                et_note.setError("Empty note");
            } else {
                String key = databaseRef.child("Expense").push().getKey();
                expenseModel expense = new expenseModel(amount, type, note, String.valueOf(date));
                databaseRef.child("Expense").child(key).setValue(expense);
                alertDialog.dismiss();
            }
        });
    }

    private void init(View root) {
        rv_income = root.findViewById(R.id.rv_income);
        rv_expense = root.findViewById(R.id.rv_expense);
        tv_income = root.findViewById(R.id.tv_income);
        tv_expense = root.findViewById(R.id.tv_expense);
        mAddFab = root.findViewById(R.id.add_fab);
        mAddIncomeFab = root.findViewById(R.id.add_income_fab);
        mAddExpenseFab = root.findViewById(R.id.add_expense_fab);
        addIncomeText = root.findViewById(R.id.add_income_text);
        addExpenseText = root.findViewById(R.id.add_expense_text);
        isAllFabsVisible = false;
    }
}
