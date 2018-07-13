package com.zhkrb.atago.acticity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import com.zhkrb.atago.R;
import com.zhkrb.atago.fragment.SearchFragment;

public class MainActivity extends AbsActivity {

    public static final int SEARCH = 0;


    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    private FragmentManager mFragmentManager;
    private SparseArray<Fragment> mFragmentList;
    private SearchFragment mSearchFragment;

    private int mFragmentTag = 0x00;    //默认首页
    private View replaced;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void main() {
        mDrawerLayout = findViewById(R.id.MainDrawerLayout);
        mNavigationView =  findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(mItemSelectedListener);
        mFragmentList = new SparseArray<>();
        mSearchFragment = new SearchFragment();

        mFragmentList.put(SEARCH,mSearchFragment);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (int i = 0; i < mFragmentList.size(); i++) {
            Fragment f = mFragmentList.valueAt(i);
            ft.add(R.id.fragment_view, f);
            if (mFragmentTag == mFragmentList.keyAt(i)) {
                ft.show(f);
            } else {
                ft.hide(f);
            }
        }
        ft.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //切换fragment
    private void toggleFragment(int tag){
        if (tag == mFragmentTag){
            return;
        }

        mFragmentTag = tag;
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        for (int i = 0;i<mFragmentList.size();i++){
            Fragment f  = mFragmentList.valueAt(i);
            if (mFragmentTag == mFragmentList.keyAt(i)){
                ft.show(f);
            }else {
                ft.hide(f);
            }
            ft.commit();
            mDrawerLayout.closeDrawers();
        }
    }

    //侧边栏点击监听
    private NavigationView.OnNavigationItemSelectedListener mItemSelectedListener = new NavigationView
            .OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_index:
                    item.setChecked(true);
                    toggleFragment(0);
                    break;
                case R.id.nav_history:
                    item.setChecked(true);
                    toggleFragment(1);
                    break;
                case R.id.nav_res:
                    item.setChecked(true);
                    toggleFragment(2);
                    break;
                case R.id.nav_download:
                    item.setChecked(true);
                    toggleFragment(3);
                    break;
                case R.id.nav_set:
                    item.setChecked(true);
                    toggleFragment(4);
                    break;
            }
            return false;
        }
    };


}
