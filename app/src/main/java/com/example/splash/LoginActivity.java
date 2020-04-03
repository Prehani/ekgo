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
    String nameLength = "Username must be at least 6 characters";
    String passwordLength = "Password must be at least 6 characters";
    String success = "Username and Password created";
    String fail = "Unable to make given Username";
    String fail2 = "Username already exists";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Click function for making a new account
     */
    public void clickNewAccount(View view) {
        EditText usernameEntered = (EditText) findViewById(R.id.username);
        String username = usernameEntered.getText().toString();
        EditText passwordEntered = (EditText) findViewById(R.id.password);
        String password = passwordEntered.getText().toString();
        if (username.length() < 6) { // username must be 6 or more characters
            Toast.makeText(this, nameLength, Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) { // password must be 6 or more characters
            Toast.makeText(this, passwordLength, Toast.LENGTH_SHORT).show();
        } else if (loginList.contains(username)) { // username already exists
            Toast.makeText(this, fail2, Toast.LENGTH_SHORT).show();
        } else {
            try {
                loginList.insert(username, password);
                Toast.makeText(this, success, Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                Toast.makeText(this, fail, Toast.LENGTH_SHORT).show();
            }
        }
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
                Toast.makeText(this, "worked correctly", Toast.LENGTH_SHORT).show();
                // load the users information
                // make a new activity that loads a users info
            } else { // happens when key present, but password incorrect
                // display that the user had an incorrect username or password
                Toast.makeText(this, userFail, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) { // error thrown due to username not existing
            Toast.makeText(this, userFail, Toast.LENGTH_SHORT).show();
        }

    }




    }

