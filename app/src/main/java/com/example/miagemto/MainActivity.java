package com.example.miagemto;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    DrawerLayout m_drawer_layout;
    NavigationView m_navigation_view;
    ImageButton button_show_drawer;

    FragmentManager fragment_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DownloadFiles d = new DownloadFiles();
        d.onPreExecute();
        d.onPostExecute(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragment_manager = getSupportFragmentManager();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomePageFragment()).commit();

        m_drawer_layout = findViewById(R.id.main_drawer_layout);
        m_navigation_view = findViewById(R.id.nav_view);

        m_navigation_view.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        m_drawer_layout.closeDrawers();
                        return manageOnSelectedItem(menuItem);
                    }
                }
        );

        button_show_drawer = findViewById(R.id.btn_drawer_menu);
        button_show_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        configureNavHeader("Ballé");

    }


    private void configureNavHeader(String userPseudo){
        View headerView = m_navigation_view.inflateHeaderView(R.layout.nav_header);

        ImageView userAvaterView = headerView.findViewById(R.id.nav_header_img_userAvatar);
        TextView userPseudoView = headerView.findViewById(R.id.nav_header_userPseudo);

        userPseudoView.setText(userPseudo);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    return manageOnSelectedItem(menuItem);
                }
            };

    private boolean manageOnSelectedItem(MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()){
            case R.id.nav_home:
                selectedFragment = new HomePageFragment();
                break;
            case R.id.nav_favorites:
                selectedFragment = new FavoriteFragment();
                break;
            case R.id.nav_info_traffic:
                selectedFragment = new InfoTrafficFragment();
                break;
            case R.id.nav_account:
                selectedFragment = new AccountFragment();
                break;
            default:
                break;
        }

        fragment_manager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    }


    private class DownloadFiles extends AsyncTask<String, Boolean, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            refreshFromInternet();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }

        private void refreshFromInternet(){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //if (item.getItemId() = R.id.)
        m_drawer_layout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}
