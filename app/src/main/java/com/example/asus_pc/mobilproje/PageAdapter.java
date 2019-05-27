package com.example.asus_pc.mobilproje;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {
  private final   ArrayList<Fragment> fragments = new ArrayList<>();
  private  final   ArrayList<String> fragmentsTitle = new ArrayList<>();
  public  void AddFragment(Fragment fragment, String string) {
      fragments.add(fragment);
      fragmentsTitle.add(string);


  }

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return  fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
