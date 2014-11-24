package com.keseni.emmtek_map;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity {

    private static final String MAP_FRAGMENT_TAG = "map";
    private GoogleMap mMap;
    private MapFragment mMapFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.bottom_frame, new Bottom_Fragment_Button())
                    .commit();
        }
        mMapFragment = (MapFragment) getFragmentManager().findFragmentByTag(MAP_FRAGMENT_TAG);
        if(mMapFragment == null){
            mMapFragment = MapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.top_frame,mMapFragment,MAP_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
        sharedPreferences = getSharedPreferences("LocateData", Context.MODE_PRIVATE);
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {

            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String mLat = sharedPreferences.getString("lat","0");
                String mLon = sharedPreferences.getString("lon","0");
                String mTime = sharedPreferences.getString("time","0");
                LatLng mLatLng = find_coordinate(mLat,mLon);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15));
                mMap.addMarker(new MarkerOptions().position(mLatLng));
            }
        };
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        // revisit the location data
        if(mMap != null)
            setUpMap();
    }

    private void setUpMapIfNeeded() {
        if(mMap == null){
            mMap = mMapFragment.getMap();
            if (mMap != null){
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //SharedPreferences sharedPreferences = getSharedPreferences("LocateData", Context.MODE_PRIVATE);
        String mLat = sharedPreferences.getString("lat","0");
        String mLon = sharedPreferences.getString("lon","0");
        String mTime = sharedPreferences.getString("time","0");
        LatLng mLatLng = find_coordinate(mLat,mLon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15));
        mMap.addMarker(new MarkerOptions().position(mLatLng));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentManager fragmentManager = getFragmentManager();
                final LocationDataDialog locationDataDialog = new LocationDataDialog();
                locationDataDialog.show(fragmentManager,"locationdatadialog");
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_phonenumber){
            FragmentManager fragmentManager = getFragmentManager();
            final PhoneConfigDialog phoneConfigDialog = new PhoneConfigDialog();
            phoneConfigDialog.show(fragmentManager,"phoneconfig");

            return true;
        }

        if (id == R.id.action_locationdata){
            FragmentManager fragmentManager = getFragmentManager();
            final LocationDataDialog locationDataDialog = new LocationDataDialog();
            locationDataDialog.show(fragmentManager,"locationdatadialog");
        }

        return super.onOptionsItemSelected(item);
    }

    public LatLng find_coordinate(String lat, String lon){
        Double mDLat = (Double.parseDouble(lat))/100;
        Double mDLon = (Double.parseDouble(lon))/100;
        //Toast.makeText(this, mDLon.toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this,mDLat.toString(),Toast.LENGTH_LONG).show();
        LatLng mLatLng = new LatLng(mDLat,mDLon);
        return mLatLng;
    }



}
