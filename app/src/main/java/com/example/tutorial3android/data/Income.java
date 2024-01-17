package com.example.tutorial3android.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Income {
    private int incomeId;
    private String username;
    private double total;
    private String message;
    private String time;

    public Income(String username, double total, String message, String time) {
        this.username = username;
        this.total = total;
        this.message = message;
        this.time = time;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getTimeAsDate() {
        // Assuming your time string is in a valid format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the parsing exception as needed
            return null;
        }
    }
}
