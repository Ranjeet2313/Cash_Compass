package com.example.expensetrackersystem.model;

public class incomeModel {
    private String id;
    private String description;
    private String amount;
    private String date;

    // Empty constructor (needed for Firebase or default object initialization)
    public incomeModel() {
    }

    // Constructor with four parameters
    public incomeModel(String id, String description, String amount, String date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public incomeModel(String string, String string1, String string2, String string3, String string4) {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNote() {
        return 0;
    }

    public String getType() {
        return null;
    }
}
