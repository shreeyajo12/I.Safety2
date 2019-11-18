package com.example.isafety.ui.home;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.util.Log;
import com.example.isafety.R;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;

public class HomeFragment extends Fragment implements View.OnClickListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    private HomeViewModel homeViewModel;
    private  location;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        root.findViewById(R.id.custom_button).setOnClickListener(this);
        return root;
    }

    private void panic() {
        locat103.ionManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        @Override
        public void onLocationChanged (Location location)
        {

            txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        }

        @Override
        public void onProviderDisabled (String provider){
            Log.d("Latitude", "disable");
        }

        @Override
        public void onProviderEnabled (String provider){
            Log.d("Latitude", "enable");
        }

        @Override
        public void onStatusChanged (String provider,int status, Bundle extras){
            Log.d("Latitude", "status");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.custom_button:
                panic();
                break;
        }

    }
}