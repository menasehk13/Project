package com.newproject.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
 Toolbar toolbar;
 DrawerLayout drawerLayout;
 ActionBarDrawerToggle toggle;
 NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.tool);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        SelectedItems(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        SelectedItems(item.getItemId());
        return false;
    }
    public boolean SelectedItems(int item){
        Fragment fragment=null;
        switch (item){
            case R.id.home:
                fragment= new MapsFragment();
                break;
            case R.id.event:
                fragment=new Places_Fragment();
                break;
        }
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.logout){
            Prefmanager prefmanager=new Prefmanager(getApplicationContext());
            prefmanager.UserLogout();
            startActivity(new Intent(getApplicationContext(),Login_Activity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
