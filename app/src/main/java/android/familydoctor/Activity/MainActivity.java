package android.familydoctor.Activity;
//trungbanh

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Adapter.ViewPagerAdapter;
import android.familydoctor.Fragment.DanhSachBacSi_BenhNhan;
import android.familydoctor.Fragment.FragmentCaiDat;
import android.familydoctor.Fragment.FragmentHoSoBenhAn;
import android.familydoctor.Fragment.TinTucSucKhoe;
import android.familydoctor.R;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton fabThemHoSoBenhAn;

    FirebaseAuth mAuth ;
    Bundle bundle;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initViewPager(LoginPhonee.dinhDanh);
        layduLieuDangNhap();
        checkPermission();
//        openView();

    }

    private void initViewPager(int i) {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_main);


        List<String> titles = new ArrayList<>();

        Log.i("initView", "initViewPager: "+i);

        switch (i) {
            case 1:
                //Bác sĩ
                titles.add("Tin tức sức khỏe");
                titles.add("Hồ sơ bệnh cá nhân");
                titles.add("Tìm Bệnh nhân");
                titles.add("Cài đặt");
            case 2:

                //Bệnh nhân
                titles.add("Tin tức sức khỏe");
                titles.add("Hồ sơ đã tạo");
                titles.add("Tìm Bác sĩ");
                titles.add("Cài đặt");
        }

        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TinTucSucKhoe());
        fragments.add(new FragmentHoSoBenhAn());
        fragments.add(new DanhSachBacSi_BenhNhan());
        fragments.add(new FragmentCaiDat());

        mViewPager.setOffscreenPageLimit(3);

        ViewPagerAdapter mFragmentAdapter = new ViewPagerAdapter(this, getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "tab 1", Toast.LENGTH_SHORT).show();
                        fabThemHoSoBenhAn.hide();
                        break;
                    case 1:

                        if (LoginPhonee.dinhDanh == 1){
                            fabThemHoSoBenhAn.show();
                        }

                        Toast.makeText(getApplicationContext(), "tab 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        fabThemHoSoBenhAn.hide();
                        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            Toast.makeText(getApplicationContext(), "Bạn chưa bật GPS nên không tìm được vị trí của bạn!", Toast.LENGTH_SHORT).show();


                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder
                                    .setMessage("Mở GPS của bạn!")
                                    .setCancelable(false)
                                    .setPositiveButton("Mở",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int id) {
                                                    Intent callGPSSettingIntent = new Intent(
                                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                    startActivity(callGPSSettingIntent);
                                                }
                                            });
                            alertDialogBuilder.setNegativeButton("Hủy",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                        }
                        break;
                    case 3:
                        fabThemHoSoBenhAn.hide();
                        Toast.makeText(MainActivity.this, "tab4", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        fabThemHoSoBenhAn = (FloatingActionButton) findViewById(R.id.fab_main);

        fabThemHoSoBenhAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThemHoSoBenhAn = new Intent(MainActivity.this, ThemHoSoBenhAnActivity.class);
                startActivity(intentThemHoSoBenhAn);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void phanHoiUngDung() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:familydoctor1996@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi về FamilyDoctor");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Không tìm thấy Gmail", Toast.LENGTH_SHORT).show();
        }
    }


    public void layduLieuDangNhap() {
        bundle = this.getIntent().getExtras();
//        Uri uri=getIntent().getData();
       /* id = bundle.getString("id");
        String ten = bundle.getString("ten");
        String email = bundle.getString("email");
        String sdt = bundle.getString("sdt");
*/

      /*  ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView_nav_header);
        TextView tv_ten= (TextView) headerView.findViewById(R.id.tv_nav_user_name);
        TextView tv_email= (TextView) headerView.findViewById(R.id.tv_nav_email);*/

     /*   tv_ten.setText(ten);
        tv_email.setText(email);*/
//        imageView.setImageURI(uri);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your Camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
