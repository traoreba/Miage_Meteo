package com.example.miagemto;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class HomePage extends AppCompatActivity {

    DrawerLayout m_drawerLayout;
    NavigationView m_navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /*
            Permet d'activer le menu burger
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);

        /*
            Retrouver la reférence du drawer layout
         */
        m_drawerLayout = (DrawerLayout) findViewById(R.id.homepage_drawer_layout);

        /*
            Retrouver la reference de la configuration de la NavigationView
             et de la configuration
         */

        m_navigationView = (NavigationView) findViewById(R.id.homepage_nav_view);

        //Manage les clics de l'utilisateur dans le menu lateral
        m_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return manageNavigationViewItemClick(menuItem);
            }
        });

    }

    private boolean manageNavigationViewItemClick(MenuItem item) {
        item.setChecked(true);
        m_drawerLayout.closeDrawers();

        if(item.getItemId() == R.id.menu_forecast)
            startActivity(new Intent(this, MainActivity.class));
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            m_drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }



}
