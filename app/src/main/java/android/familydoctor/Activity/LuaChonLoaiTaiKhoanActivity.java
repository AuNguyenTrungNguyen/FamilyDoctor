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
        toolbarChoose.setTitleTextColor(Color.WHITE);

        tabLayoutChoose = (TabLayout) findViewById(R.id.tab_layout_choose);
        viewPagerChoose = (ViewPager) findViewById(R.id.view_pager_choose);

        List<String> titles = new ArrayList<>();
        titles.add("Bác sĩ");
        titles.add("Bệnh nhân");
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
}
