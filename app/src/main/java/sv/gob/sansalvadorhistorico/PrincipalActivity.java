package sv.gob.sansalvadorhistorico;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import sv.gob.sansalvadorhistorico.fragment.EventosFragment;
import sv.gob.sansalvadorhistorico.fragment.HomeFragment;
import sv.gob.sansalvadorhistorico.fragment.MapsFragment;
import sv.gob.sansalvadorhistorico.fragment.PerfilFragment;
import sv.gob.sansalvadorhistorico.fragment.RecorridoFragment;
import sv.gob.sansalvadorhistorico.servicios.LocalizacionService;

public class PrincipalActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabAdapter adapter;
    public static TabLayout tabLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopService(new Intent(PrincipalActivity.this, LocalizacionService.class));
        }else{
            stopService(new Intent(PrincipalActivity.this, LocalizacionService.class));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        adapter = new TabAdapter(getSupportFragmentManager());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(PrincipalActivity.this, LocalizacionService.class));
        }else{
            startService(new Intent(PrincipalActivity.this, LocalizacionService.class));
        }


        adapter.addFragment(new HomeFragment(), "");
        adapter.addFragment(new MapsFragment(), "");
        adapter.addFragment(new RecorridoFragment(), "");
        adapter.addFragment(new EventosFragment(), "");
        adapter.addFragment(new PerfilFragment(), "");
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.marker);
        tabLayout.getTabAt(2).setIcon(R.drawable.icono_360);
        tabLayout.getTabAt(3).setIcon(R.drawable.calendario);
        tabLayout.getTabAt(4).setIcon(R.drawable.perfil);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position==2){
                    //tabLayout.getTabAt(2).removeBadge();

                }
                if(position==0){
                    //tabLayout.getTabAt(2).removeBadge();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }



    class TabAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}