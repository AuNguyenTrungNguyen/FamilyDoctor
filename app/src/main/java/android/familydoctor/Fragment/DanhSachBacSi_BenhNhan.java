package android.familydoctor.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.familydoctor.Class.BacSi;
import android.familydoctor.R;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DanhSachBacSi_BenhNhan extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap = null;
    DatabaseReference database;
    ArrayList<BacSi> dsBacSi;
    double longtitudeGPS;
    double latitudeGPS;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_danhasch_bs_bn, container, false);
        database = FirebaseDatabase.getInstance().getReference();
        // testPusuDULieu();
        dsBacSi = new ArrayList<>();
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        loadDuLieuFirebase();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        LocationManager lm = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longtitudeGPS = location.getLongitude();
        latitudeGPS = location.getLatitude();

        Toast.makeText(getContext(),latitudeGPS+"   "+longtitudeGPS , Toast.LENGTH_SHORT).show();
        Log.d("TOADO",latitudeGPS+"   "+longtitudeGPS);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                googleMap = mMap;

                if (googleMap != null) {
                    moveCameraMyLoc(latitudeGPS, longtitudeGPS, 17);
                } else {
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (googleMap != null) {
                                moveCameraMyLoc(latitudeGPS
                                        , longtitudeGPS
                                        , 17);
                            } else {
                                handler.postDelayed(this, 300);
                            }
                        }
                    }, 300);
                }


                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        googleMap.clear();
                        moveCamera(latLng.latitude, latLng.longitude, 18);
                        googleMap.addCircle(new CircleOptions()
                                .radius(500)
                                .center(latLng)
                        );
                        for (int i = 0; i < dsBacSi.size(); i++) {
                            MarkerOptions markerOptions;
                            LatLng toado = new LatLng(Double.parseDouble(dsBacSi.get(i).getX()), Double.parseDouble(dsBacSi.get(i).getY()));
                            float[] results = new float[1];
                            Location.distanceBetween(latLng.latitude, latLng.longitude,
                                    toado.latitude, toado.longitude
                                    , results);
                            if (results[0] < 500) {
                                markerOptions = new MarkerOptions()
                                        .position(toado)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.doctor))
                                        .title(dsBacSi.get(i).getHoten())
                                        .snippet(dsBacSi.get(i).getLinhvucchuyenmon()
                                        );
                                googleMap.addMarker(markerOptions).showInfoWindow();
                            }
                        }
                    }
                });
                // For dropping a marker at a point on the Map
                /*LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("HBQ").snippet("huynh bao quoc"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));*/
            }
        });


        return rootView;
    }

    private void testPusuDULieu() {
        BacSi bs = new BacSi(
                "Huynh quoc"
                , "01262985603"
                , "quocb1400@gmail.com"
                , "Răng hàm mặt"
                , "Ninh kiều, Cần thơ"
                , ""
                , ""
                , "10.028935"
                , "105.763333"
        );
        database.child("BacSi").child("01262985603").setValue(bs);
        BacSi bs1 = new BacSi(
                "Huynh quoc 1"
                , "0123123123"
                , "quocb1400@gmail.com"
                , "Mắt mũi"
                , "Cái răng, Cần thơ"
                , ""
                , ""
                , "10.029458"
                , "105.762469"
        );
        database.child("BacSi").child("0123123123").setValue(bs1);
        BacSi bs2 = new BacSi(
                "Huynh quoc 2"
                , "0124124124"
                , "quocb1400@gmail.com"
                , "Xương khóp"
                , "Ô môn, Cần thơ"
                , ""
                , ""
                , "10.029879"
                , "105.763230"
        );
        database.child("BacSi").child("0124124124").setValue(bs2);
        BacSi bs3 = new BacSi(
                "Huynh quoc 3"
                , "01261262126"
                , "quocb1400@gmail.com"
                , "Da liễu"
                , "Mậu thân, Cần thơ"
                , ""
                , ""
                , "10.029840"
                , "105.764165"
        );
        database.child("BacSi").child("01261262126").setValue(bs3);
        BacSi bs4 = new BacSi(
                "Huynh quoc 44"
                , "0122122122"
                , "quocb1400@gmail.com"
                , "Tổng hợp"
                , "Ngô quyền, Cần thơ"
                , ""
                , ""
                , "10.029583"
                , "105.764165"
        );
        database.child("BacSi").child("0122122122").setValue(bs4);

    }

    private void loadDuLieuFirebase() {
        database.child("BacSi").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BacSi doctor = dataSnapshot.getValue(BacSi.class);
                dsBacSi.add(doctor);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void moveCamera(double lat, double lng, float zoom) {
        LatLng loc = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(loc));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
    }

    public void moveCameraMyLoc(double lat, double lng, float zoom) {
        LatLng loc = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(loc).title("Bạn đang ở đây!"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
    }
}
