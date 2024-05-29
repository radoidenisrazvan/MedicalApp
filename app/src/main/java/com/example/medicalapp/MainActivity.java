package com.example.medicalapp;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences sharedPreferences;
    private TextView textViewWelcome;
    private boolean logged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (savedInstanceState == null) {
            loadFragment(new LoginFragment());
            navigationView.setCheckedItem(R.id.nav_login);
        }

        updateMenuItems();
    }


    private boolean isLoggedIn() {
        return logged;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.logged = isLoggedIn;
        Log.d("MainActivity", "Starea de autentificare actualizată: " + isLoggedIn);

        if (isLoggedIn) {
            updateMenuItems();
            loadFragment(new HomeFragment());
        }
        else {
            updateMenuItems();
            loadFragment(new LoginFragment());
        }
    }

    private void logoutUser() {
        setLoggedIn(false);
        loadFragment(new LoginFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_login:
                if (isLoggedIn()) {
                    return false;
                } else {
                    fragment = new LoginFragment();
                }
                break;
            case R.id.nav_register:
                if (isLoggedIn()) {
                    return false;
                } else {
                    fragment = new RegisterFragment();
                }
                break;
            case R.id.nav_select_disease:
                fragment = new SelectDiseaseFragment();
                break;
            case R.id.nav_reminder:
                fragment = new ReminderFragment();
                break;
            case R.id.nav_help:
                fragment = new HelpFragment();
                break;
            case R.id.nav_review:
                fragment = new ReviewFragment();
                break;
            case R.id.nav_logout:
                fragment = new LoginFragment();
                logoutUser();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }

        drawerLayout.closeDrawers();
        return true;
    }

    private void updateMenuItems() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();

        MenuItem loginItem = menu.findItem(R.id.nav_login);
        MenuItem registerItem = menu.findItem(R.id.nav_register);
        MenuItem logoutItem = menu.findItem(R.id.nav_logout);

        if ((this.logged)) {
            loginItem.setVisible(false);
            registerItem.setVisible(false);
            logoutItem.setVisible(true);
        } else {
            loginItem.setVisible(true);
            registerItem.setVisible(true);
            logoutItem.setVisible(false);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "loginFragment");
        ft.commit();
    }
}
