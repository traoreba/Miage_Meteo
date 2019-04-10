package com.example.miagemto;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    DrawerLayout m_drawer_layout;
    NavigationView m_navigation_view;
    ImageButton button_show_drawer;
    BottomNavigationView bottomNav;
    private int currentNav;

    FragmentManager fragment_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_manager = getSupportFragmentManager();
        bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);
        fragment_manager.beginTransaction().replace(R.id.fragment_container,
                new HomePageFragment()).commit();

        m_drawer_layout = findViewById(R.id.main_drawer_layout);
        m_navigation_view = findViewById(R.id.nav_view);
        m_navigation_view.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        return manageNavDrawerOnSelectedItem(menuItem);
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

        configureNavHeader(getIntent().getStringExtra(LoginActivity.USER_NAME));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    return manageNavBottomOnSelectedItem(menuItem);
                }
            };

    private boolean manageNavBottomOnSelectedItem(MenuItem item) {
        if (item.getItemId() == currentNav)
            return true;
        currentNav = item.getItemId();
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
            case R.id.nav_schedule:
                selectedFragment = new ScheduleFragment();
                break;
            default:
                break;
        }
        fragment_manager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    }

    private boolean manageNavDrawerOnSelectedItem(MenuItem item) {
        m_drawer_layout.closeDrawers();
        Intent activity_to_launch = null;
        switch (item.getItemId()){
            case R.id.nav_account:
                activity_to_launch = new Intent(this, ProfilActivity.class);
                break;
            case R.id.nav_contact_us:
                activity_to_launch = new Intent(this, ContactUsActivity.class);
                break;
            case R.id.nav_parameters:
                activity_to_launch = new Intent(this, SettingsActivity.class);
                break;
            default:
                break;
        }
        startActivity(activity_to_launch);
        return true;
    }

    private void configureNavHeader(String userPseudo){
        View headerView = m_navigation_view.inflateHeaderView(R.layout.nav_header);

        ImageView userAvaterView = headerView.findViewById(R.id.nav_header_img_userAvatar);
        TextView userPseudoView = headerView.findViewById(R.id.nav_header_userPseudo);

        userPseudoView.setText(userPseudo);
    }
}
