package com.newproject.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Places_Fragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
BottomNavigationView navigationView;
    public Places_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationView=view.findViewById(R.id.bottom_nav_items);
        navigationView.setOnNavigationItemSelectedListener(this);
        slideView(R.id.lounge);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        slideView(item.getItemId());
        return false;
    }
    public boolean slideView(int item){
        Fragment fragment1=null;
        switch (item){
            case R.id.lounge:
             fragment1=new LoungeFragment();
             break;
            case R.id.cafe:
                fragment1=new CafeFragment();
                break;
            case R.id.reastorants:
                fragment1=new RestourantFragment();
                break;
            default:
        }
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.slide_view,fragment1);
        ft.commit();

        return true;
    }
}
