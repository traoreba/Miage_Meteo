package com.example.miagemto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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

public class MainActivity extends AppCompatActivity implements LocationListener{

    DrawerLayout m_drawer_layout;
    NavigationView m_navigation_view;
    ImageButton button_show_drawer;
    BottomNavigationView bottomNav;

    FragmentManager fragment_manager;

    private LocationManager locationManager;
    private String locationProvider = LocationManager.NETWORK_PROVIDER;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final int THIRTY_METERS = 300;
    public Location currentBestLocation;
    private final int MY_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(locationProvider, TWO_MINUTES, THIRTY_METERS, this);
        currentBestLocation = locationManager.getLastKnownLocation(locationProvider);
        configureActivity();
    }

    private void configureActivity() {
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

        configureNavHeader("Ballé");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION:
                if( grantResults[0] > 0 && grantResults[1] ==0 ){
                    configureActivity();
                }
                return;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    return manageNavBottomOnSelectedItem(menuItem);
                }
            };

    private boolean manageNavBottomOnSelectedItem(MenuItem item) {
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

    /** Determines whether one GPSTracker reading is better than the current GPSTracker fix
     * @param location  The new GPSTracker that you want to evaluate
     */
    protected boolean isBetterLocation(Location location) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (isBetterLocation(location)){
            currentBestLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
