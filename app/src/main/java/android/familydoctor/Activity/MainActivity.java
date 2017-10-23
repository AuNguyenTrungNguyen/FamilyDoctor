package android.familydoctor.Activity;
//trungbanh

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Adapter.ViewPagerAdapter;
import android.familydoctor.Fragment.DanhSachBacSi_BenhNhan;
import android.familydoctor.Fragment.FragmentCaiDat;
import android.familydoctor.Fragment.FragmentHoSoBenhAn;
import android.familydoctor.Fragment.TinTucSucKhoe;
import android.familydoctor.R;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    public TabLayout mTabLayout;
    private FloatingActionButton fabThemHoSoBenhAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewPager(LoginPhone.dinhDanh);

    }

    private void initViewPager(int i) {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_main);


        List<String> titles = new ArrayList<>();

        Log.i("initView", "initViewPager: "+i);

        switch (i) {
            case 1:
                //Bác sĩ
                titles.add(getResources().getString(R.string.tab_title_main_1_doctoc));
                titles.add(getResources().getString(R.string.tab_title_main_2_doctoc));
                titles.add(getResources().getString(R.string.tab_title_main_3_doctoc));
                titles.add(getResources().getString(R.string.tab_title_main_4_doctoc));
            case 2:
                //Bệnh nhân
                titles.add(getResources().getString(R.string.tab_title_main_1_patient));
                titles.add(getResources().getString(R.string.tab_title_main_2_patient));
                titles.add(getResources().getString(R.string.tab_title_main_3_patient));
                titles.add(getResources().getString(R.string.tab_title_main_4_patient));
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
                View view = MainActivity.this.getCurrentFocus();
                switch (position) {
                    case 0:
                        fabThemHoSoBenhAn.hide();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        break;
                    case 1:
                        if (LoginPhone.dinhDanh == 1){
                            fabThemHoSoBenhAn.show();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                        break;
                    case 2:
                        fabThemHoSoBenhAn.hide();
                        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.GPS_not_enable) , Toast.LENGTH_SHORT).show();


                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder
                                    .setMessage(getResources().getString(R.string.enable_GPS))
                                    .setCancelable(false)
                                    .setPositiveButton(getResources().getString(R.string.yes),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int id) {
                                                    Intent callGPSSettingIntent = new Intent(
                                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                    startActivity(callGPSSettingIntent);
                                                }
                                            });
                            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no),
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
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

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

        toolbar.setTitleTextColor(Color.WHITE);

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
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.question_exit));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
