package android.familydoctor.Fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Activity.LoginPhone;
import android.familydoctor.Activity.XemTTBacSi_Act;
import android.familydoctor.Activity.XemTTBenhNhan_Act;
import android.familydoctor.Class.BacSi;
import android.familydoctor.Class.BenhNhan;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.R;
import android.familydoctor.service.GPSTracker;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;
import static android.familydoctor.R.drawable.doctor;
import static android.familydoctor.R.drawable.patient;

public class DanhSachBacSi_BenhNhan extends Fragment implements View.OnClickListener {
    LocationManager locationManager;
    MapView mMapView;
    private GoogleMap googleMap = null;

    DatabaseReference databaseBS;
    DatabaseReference databaseBN;
    DatabaseReference databaseHSBA;
    ArrayList<BacSi> dsBacSi;
    ArrayList<BenhNhan> dsBenhNhan;
    ArrayList<HoSoBenh> dsHSBA;
    public static double longtitudeGPS;
    public static double latitudeGPS;
    EditText edtInput;
    ImageButton btnSearch;
    private GPSTracker gpsTracker;
    private Location mLocation;


    /*GoogleMap.OnMyLocationChangeListener listener =new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                moveCameraMyLoc(location.getLatitude(),location.getLongitude(),18);
            }
        };*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_danhasch_bs_bn, container, false);
        databaseBN = FirebaseDatabase.getInstance().getReference();
        databaseBS = FirebaseDatabase.getInstance().getReference();
        databaseHSBA = FirebaseDatabase.getInstance().getReference();
        edtInput = (EditText) rootView.findViewById(R.id.edtSDT);
        btnSearch = (ImageButton) rootView.findViewById(R.id.btnSearchAddress);
        btnSearch.clearFocus();
        btnSearch.setOnClickListener(this);
        dsBacSi = new ArrayList<>();
        dsBenhNhan = new ArrayList<>();
        dsHSBA = new ArrayList<>();
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        loadDuLieuFirebase();
        Log.d("DinhDanh", LoginPhone.dinhDanh + "    " + "toado" + latitudeGPS + "   " + longtitudeGPS);
        ///qudjqwhdwqd
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
       /* gpsTracker = new GPSTracker(getContext());
        if (gpsTracker != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocation = gpsTracker.getLocation();
            latitudeGPS = mLocation.getLatitude();
            longtitudeGPS = mLocation.getLongitude();
        }*/
        //Toast.makeText(getContext(), latitudeGPS + "   " + longtitudeGPS, Toast.LENGTH_SHORT).show();
        //Log.d("TOADO", latitudeGPS + "   " + longtitudeGPS);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        LinearLayout info = new LinearLayout(getContext().getApplicationContext());
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(getContext());
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(getContext().getApplicationContext());
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });
                if (googleMap != null) {
                    // moveCameraMyLoc(LoginPhone.xxx, LoginPhone.yyy, 17);
                    moveCameraMyLoc(latitudeGPS, longtitudeGPS, 17);

                } else {
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (googleMap != null) {
//                                moveCameraMyLoc(LoginPhone.xxx, LoginPhone.yyy, 17);
                                moveCameraMyLoc(latitudeGPS, longtitudeGPS, 17);
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
                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            Toast.makeText(getContext(), getResources().getString(R.string.enable_GPS), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                //googleMap.setOnMyLocationChangeListener(listener);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        googleMap.clear();
                        moveCamera(latLng.latitude, latLng.longitude, 18);
                        googleMap.addCircle(new CircleOptions()
                                .radius(100)
                                .strokeColor(Color.parseColor("#007497"))
                                .fillColor(Color.parseColor("#186e6e6e"))
                                .center(latLng)
                        );
                        if (LoginPhone.dinhDanh == 2)//////////laf benh nhan thi xem cac bac si
                        {
                            for (int i = 0; i < dsBacSi.size(); i++) {
                                MarkerOptions markerOptions;
                                LatLng toado = new LatLng(dsBacSi.get(i).getxBacSi()
                                        , dsBacSi.get(i).getyBacSi());
                                float[] results = new float[1];
                                Location.distanceBetween(latLng.latitude, latLng.longitude,
                                        toado.latitude, toado.longitude
                                        , results);
                                if (results[0] < 100) {
                                    markerOptions = new MarkerOptions()
                                            .position(toado)
                                            .icon(BitmapDescriptorFactory.fromResource(doctor))
                                            .title(dsBacSi.get(i).getHoTenBacSi())
                                            .snippet
                                                    (
                                                            getResources().getString(R.string.Medical_specialty)+": " + dsBacSi.get(i).getChuyenMonBacSi()
                                                                    + "\n"
                                                                    + getResources().getString(R.string.Address)+": " + dsBacSi.get(i).getDiaChiBacSi()
                                                                    + "\n"
                                                                    + getResources().getString(R.string.Phone_number)+": " + dsBacSi.get(i).getSoDienThoaiBacSi()

                                                    );
                                    googleMap.addMarker(markerOptions).setTag(dsBacSi.get(i));
                                }
                            }
                            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    Toast.makeText(getContext(), getResources().getString(R.string.Please_hold_long_to_call) , Toast.LENGTH_SHORT).show();
                                }
                            });
                            googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                                @Override
                                public void onInfoWindowLongClick(final Marker marker) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setPositiveButton(getResources().getString(R.string.View_this_doctor_details), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            BacSi bs = (BacSi) marker.getTag();
                                            Intent it = new Intent(getContext(), XemTTBacSi_Act.class);
                                            it.putExtra("name", bs.getHoTenBacSi());
                                            it.putExtra("sdt", bs.getSoDienThoaiBacSi());
                                            it.putExtra("chuyenmon", bs.getChuyenMonBacSi());
                                            it.putExtra("diachi", bs.getDiaChiBacSi());
                                            it.putExtra("url1", bs.getUriHinhAnhBacSi());
                                            it.putExtra("url2", bs.getUriVanBangBacSi());
                                            startActivity(it);
                                        }
                                    });

                                    builder.setNegativeButton(getResources().getString(R.string.Call), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int dauhieu = marker.getSnippet().indexOf("::");

                                            String number = marker.getSnippet().substring
                                                    (
                                                            dauhieu + 1
                                                            ,
                                                            marker.getSnippet().length()
                                                    );
                                            Log.d("SDT", number);
                                            Intent intent = new Intent(Intent.ACTION_CALL);
                                            intent.setData(Uri.parse("tel:" + number));
                                            startActivity(intent);
                                        }
                                    });

                                    Dialog dialog = builder.create();
                                    dialog.show();
                                }
                            });
                        }
                        else////la bac si thi xem benh nhan trong ho so benh an
                        {
                            for (int i = 0; i < dsBenhNhan.size(); i++) {
                                MarkerOptions markerOptions;
                                LatLng toado = new LatLng(dsBenhNhan.get(i).getxBenhNhan()
                                        , dsBenhNhan.get(i).getyBenhNhan());
                                float[] results = new float[1];
                                Location.distanceBetween(latLng.latitude, latLng.longitude,
                                        toado.latitude, toado.longitude
                                        , results);
                                if (results[0] < 100) {
                                    markerOptions = new MarkerOptions()
                                            .position(toado)
                                            .icon(BitmapDescriptorFactory.fromResource(patient))
                                            .title(dsBenhNhan.get(i).getHoTenBenhNhan())
                                            .snippet
                                                    (

                                                            getResources().getString(R.string.Address)+ ": " + dsBenhNhan.get(i).getDiaChiBenhNhan()
                                                                    + "\n"
                                                                    + getResources().getString(R.string.Phone_number)+ ": " + dsBenhNhan.get(i).getSoDienThoaiBenhNhan()

                                                    );
                                    googleMap.addMarker(markerOptions).setTag(dsBenhNhan.get(i));
                                }
                            }
                            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    Toast.makeText(getContext(),getResources().getString(R.string.Please_hold_long_to_call), Toast.LENGTH_SHORT).show();
                                }
                            });
                            googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                                @Override
                                public void onInfoWindowLongClick(final Marker marker) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setPositiveButton(getResources().getString(R.string.View_this_patient_details), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            BenhNhan bs = (BenhNhan) marker.getTag();
                                            Intent it = new Intent(getContext(), XemTTBenhNhan_Act.class);
                                            it.putExtra("name", bs.getHoTenBenhNhan());
                                            it.putExtra("sdt", bs.getSoDienThoaiBenhNhan());
                                            it.putExtra("diachi", bs.getDiaChiBenhNhan());
                                            it.putExtra("urlBN", bs.getUriHinhAnhBenhNhan());
                                            startActivity(it);
                                        }
                                    });

                                    builder.setNegativeButton(getResources().getString(R.string.Call), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int dauhieu = marker.getSnippet().indexOf("::");

                                            String number = marker.getSnippet().substring
                                                    (
                                                            dauhieu + 1
                                                            ,
                                                            marker.getSnippet().length()
                                                    );
                                            Log.d("SDT", number);
                                            Intent intent = new Intent(Intent.ACTION_CALL);
                                            intent.setData(Uri.parse("tel:" + number));
                                            startActivity(intent);
                                        }
                                    });

                                    Dialog dialog = builder.create();
                                    dialog.show();
                                }
                            });
                        }
                    }

                });
            }
        });


        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Latlng", mLocation);
    }


    private void loadDuLieuFirebase() {

        databaseBN.child("User_BenhNhan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BenhNhan benhNhan = dataSnapshot.getValue(BenhNhan.class);
                dsBenhNhan.add(benhNhan);
                Log.d("THONGTIN",benhNhan.getUriHinhAnhBenhNhan());
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
        databaseBS.child("User_BacSi").addChildEventListener(new ChildEventListener() {
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


    public void moveCamera(double lat, double lng, float zoom) {
        LatLng loc = new LatLng(lat, lng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
    }

    public void moveCameraMyLoc(double lat, double lng, float zoom) {
        LatLng loc = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(loc).title(getResources().getString(R.string.You_are_here)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearchAddress) {
            String input = edtInput.getText().toString();
            for (int i = 0; i < dsBacSi.size(); i++) {
                if (TextUtils.equals(input, dsBacSi.get(i).getSoDienThoaiBacSi())) {
                    moveCamera(dsBacSi.get(i).getxBacSi()
                            , dsBacSi.get(i).getyBacSi()
                            , 19);
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(dsBacSi.get(i).getxBacSi()
                                    , dsBacSi.get(i).getyBacSi()))
                            .icon(BitmapDescriptorFactory.fromResource(doctor))
                            .title(dsBacSi.get(i).getHoTenBacSi())
                            .snippet(
                                    getResources().getString(R.string.Medical_specialty)+": " + dsBacSi.get(i).getChuyenMonBacSi()
                                            + "\n"
                                            + getResources().getString(R.string.Address)+": " + dsBacSi.get(i).getDiaChiBacSi()
                                            + "\n"
                                            + getResources().getString(R.string.Phone_number)+": " + dsBacSi.get(i).getSoDienThoaiBacSi()
                            );
                    googleMap.addMarker(markerOptions).setTag(dsBacSi.get(i));
//                    Toast.makeText(getContext(), "That it", Toast.LENGTH_SHORT).show();
                }
            }
            for (int i = 0; i < dsBenhNhan.size(); i++) {
                if (TextUtils.equals(input, dsBenhNhan.get(i).getSoDienThoaiBenhNhan())) {
                    moveCamera(dsBenhNhan.get(i).getxBenhNhan()
                            , dsBenhNhan.get(i).getyBenhNhan()
                            , 19);
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(dsBenhNhan.get(i).getxBenhNhan()
                                    , dsBenhNhan.get(i).getyBenhNhan()))
                            .icon(BitmapDescriptorFactory.fromResource(patient))
                            .title(dsBenhNhan.get(i).getHoTenBenhNhan())
                            .snippet(
                                    getResources().getString(R.string.Address)+": " + dsBenhNhan.get(i).getDiaChiBenhNhan()
                                            + "\n"
                                            + getResources().getString(R.string.Phone_number)+": " + dsBenhNhan.get(i).getSoDienThoaiBenhNhan()
                            );
                    googleMap.addMarker(markerOptions).setTag(dsBenhNhan.get(i));
                }

            }
        }
    }

}
