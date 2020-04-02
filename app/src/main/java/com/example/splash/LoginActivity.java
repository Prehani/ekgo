package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    HashTable<String, String> loginList = new HashTable<>(); // table of usernames and passwords
    String userFail = "Incorrect Username or Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Click function for making a new account
     */
    public void clickNewAccount(View view) {

    }

    /**
     * Click function for the login button
     * determines if the username and password are valid
     */
    public void clickLogin(View view) {
        // gets the username from the edittext field
        EditText usernameEntered = (EditText) findViewById(R.id.username);
        String username = usernameEntered.getText().toString();
        // gets the password from the corresponding edittext field
        EditText passwordEntered = (EditText) findViewById(R.id.password);
        String password = passwordEntered.getText().toString();
        try {
            // user name and password correct
            if (loginList.get(username).equals(password)) {
                // load the users information
            } else {
                // display that the user had an incorrect username or password
                Toast.makeText(this, userFail, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) { // error thrown during username/password lookup
            // display that the user had an incorrect username or password
            Toast.makeText(this, userFail, Toast.LENGTH_SHORT).show();
        }

    }




    }

