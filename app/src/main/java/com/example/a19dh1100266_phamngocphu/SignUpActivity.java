package com.example.a19dh1100266_phamngocphu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //SET OBJECT
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this,R.id.nav_signin);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fullnameFragment, R.id.addressFragment,R.id.usernamePasswordFragment)
                .build();

        // set action for ui multi  navcontroller with appbarconfigure
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController( toolbar,navController);
    }

}
