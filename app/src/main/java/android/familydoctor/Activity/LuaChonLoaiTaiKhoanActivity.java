package android.familydoctor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.familydoctor.Adapter.ViewPagerAdapter;
import android.familydoctor.Fragment.FragmentBacSi;
import android.familydoctor.Fragment.FragmentBenhNhan;
import android.familydoctor.R;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class LuaChonLoaiTaiKhoanActivity extends AppCompatActivity {

    Toolbar toolbarChoose;
    TabLayout tabLayoutChoose;
    ViewPager viewPagerChoose;

    public void turnGPSOn() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage(getResources().getString(R.string.enable_GPS))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.open),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lua_chon_loai_tai_khoan);
        turnGPSOn();
        initViewPager();

    }

    private void initViewPager(){


        toolbarChoose = (Toolbar) findViewById(R.id.toolbar_choose);
        setSupportActionBar(toolbarChoose);
        toolbarChoose.setTitle(getResources().getString(R.string.select_an_account_type));
        toolbarChoose.setTitleTextColor(Color.WHITE);

        tabLayoutChoose = (TabLayout) findViewById(R.id.tab_layout_choose);
        viewPagerChoose = (ViewPager) findViewById(R.id.view_pager_choose);

        List<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.doctor));
        titles.add(getResources().getString(R.string.Patient));
        tabLayoutChoose.addTab(tabLayoutChoose.newTab().setText(titles.get(0)));
        tabLayoutChoose.addTab(tabLayoutChoose.newTab().setText(titles.get(1)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentBacSi());
        fragments.add(new FragmentBenhNhan());

        viewPagerChoose.setOffscreenPageLimit(2);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,getSupportFragmentManager(), fragments, titles);
        viewPagerChoose.setAdapter(viewPagerAdapter);
        tabLayoutChoose.setupWithViewPager(viewPagerChoose);

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
