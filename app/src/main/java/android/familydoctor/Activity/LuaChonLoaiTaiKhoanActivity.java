package android.familydoctor.Activity;

import android.familydoctor.Adapter.ViewPagerAdapter;
import android.familydoctor.Fragment.FragmentBacSi;
import android.familydoctor.Fragment.FragmentBenhNhan;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class LuaChonLoaiTaiKhoanActivity extends AppCompatActivity {

    Toolbar toolbarChoose;
    TabLayout tabLayoutChoose;
    ViewPager viewPagerChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lua_chon_loai_tai_khoan);
        initViewPager();

    }

    private void initViewPager(){
        toolbarChoose = (Toolbar) findViewById(R.id.toolbar_choose);
        setSupportActionBar(toolbarChoose);

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
