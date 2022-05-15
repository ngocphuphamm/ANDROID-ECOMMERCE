package com.example.a19dh1100266_phamngocphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.a19dh1100266_phamngocphu.model.CartRepository;
import com.example.a19dh1100266_phamngocphu.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    NavigationView navigationView;
    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    FirebaseDatabase fDatabase;
    FirebaseAuth fAuth;
    TextView tvFullName, tvEmail;
    CartRepository cartRepository;
    Menu mMenu;
    int mCartItemCount = 0;
    TextView textCartItemCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();

        cartRepository = new CartRepository(getApplication());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.orderFragment, R.id.profileFragment).setDrawerLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.profileFragment) {
                    mMenu.findItem(R.id.mnuCart).setVisible(false);
                } else if (mMenu != null) {
                    mMenu.findItem(R.id.mnuCart).setVisible(true);
                }
            }
        });


        View view = navigationView.getHeaderView(0);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvEmail = view.findViewById(R.id.tvEmail);

        String userID = fAuth.getCurrentUser().getUid();

        fDatabase.getReference().child("users").child(userID).get()
                .addOnSuccessListener(dataSnapshot -> {
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserID(userID);
                    tvFullName.setText(user.getLastname() + ' ' + user.getFirstname());
                    tvEmail.setText(user.getEmail());

                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCartItemCount = cartRepository.getCountCart();
            setupBadge();
            Log.d("ABC", mCartItemCount + "");
        } catch (ExecutionException e) {
            Log.d("ABC", e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d("ABC", e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_menu, menu);
        mMenu = menu;


        View actionView = mMenu.findItem(R.id.mnuCart).getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(mMenu.findItem(R.id.mnuCart));
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuCart) {
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }

        }
    }
}