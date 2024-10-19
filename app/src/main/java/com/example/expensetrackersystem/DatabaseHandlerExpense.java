package com.example.expensetrackersystem;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.expensetrackersystem.model.expenseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerExpense {

    private DatabaseReference databaseRef;
    private FirebaseAuth firebaseAuth;
    private List<expenseModel> expenseModelList;

    public DatabaseHandlerExpense(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Expenses");
    }

    // Add expense data to Firebase
    public void addData(String amount, String type, String note, String date) {
        String expenseId = databaseRef.push().getKey();  // Generate a unique ID for each entry
        expenseModel expense = new expenseModel(expenseId, amount, type, note, date);
        databaseRef.child(expenseId).setValue(expense);  // Save the expense under the generated ID
    }

    // Update existing expense data in Firebase
    public void update(String id, String amount, String type, String note, String date) {
        expenseModel expense = new expenseModel(id, amount, type, note, date);
        databaseRef.child(id).setValue(expense);  // Update the existing expense entry
    }

    // Retrieve all expenses from Firebase (you may need to handle this asynchronously in your Fragment or Activity)
    public List<expenseModel> getAllExpenses() {
        List<expenseModel> expenseModelList = new ArrayList<>();
        // Implement a ValueEventListener in your fragment/activity to fetch data asynchronously
        return expenseModelList;
    }

    public List<expenseModel> getAllIncome() {
        return java.util.Collections.emptyList();
    }
}
