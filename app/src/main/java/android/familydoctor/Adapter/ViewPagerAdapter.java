package android.familydoctor.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by buimi on 5/27/2017.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;
    Context context;
    public ViewPagerAdapter(Context context,FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.context=context;
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTitles.get(position);
    }

}