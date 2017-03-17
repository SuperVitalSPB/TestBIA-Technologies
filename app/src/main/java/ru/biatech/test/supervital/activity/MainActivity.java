package ru.biatech.test.supervital.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.biatech.test.supervital.ApplicationBIATech;
import ru.biatech.test.supervital.Const;
import ru.biatech.test.supervital.testbia_tech.R;
import ru.biatech.test.supervital.fragment.AllTracksFragment;
import ru.biatech.test.supervital.fragment.FavoriteTracksFragment;
import ru.biatech.test.supervital.fragment.StartFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    NavigationView navigationView;
    DrawerLayout drawer;
    ApplicationBIATech myApp;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myApp.idxMenuChecked = navigationView.getMenu().getItem(0).isChecked() ? 0 : 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (ApplicationBIATech) getApplication();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showStartFrame(){
        try {
            Fragment fragment = (Fragment) StartFragment.class.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void EnabledDrawerItems(boolean aVal){
        navigationView.getMenu().getItem(0).setEnabled(aVal);
        navigationView.getMenu().getItem(1).setEnabled(aVal);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        if (id == R.id.nav_all_tracks) {
            fragmentClass = AllTracksFragment.class;
        } else if (id == R.id.nav_favorite_tracks) {
            fragmentClass = FavoriteTracksFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void RestoreTracksFrame(){
        if (!isOnline())
            return;
        Fragment fra = (Fragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if(fra != null && fra instanceof StartFragment)
            ((StartFragment) fra).txtNotInternet.setText("");
        EnabledDrawerItems(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(myApp.idxMenuChecked));
    }

    public boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnline()) {
            RestoreTracksFrame();
        } else {
            showStartFrame();
            EnabledDrawerItems(false);
        }

    }

}
