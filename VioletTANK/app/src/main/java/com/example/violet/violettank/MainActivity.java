package com.example.violet.violettank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.violet.violettank.ui.base.BaseActivity;
import com.example.violet.violettank.ui.base.BaseFragment;
import com.example.violet.violettank.ui.home.MainPagerAdapter;
import com.example.violet.violettank.ui.local.LocalFilesFragment;
import com.example.violet.violettank.ui.music.MusicPlayerFragment;
import com.example.violet.violettank.ui.playlist.PlayListFragment;
import com.example.violet.violettank.ui.settings.SettingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseActivity {

    static final int DEFAULT_PAGE_INDEX = 2;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static void navigation(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
    @BindViews({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files, R.id.radio_button_settings})
    List<RadioButton> radioButtons;
//    @BindView(R.id.radio_group_controls)
//    RadioGroup radioGroupControls;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
//    @BindView(R.id.root)
//    RelativeLayout root;
    String[] mTitles;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mTitles = getResources().getStringArray(R.array.mp_main_titles);
        BaseFragment[] fragments =  new BaseFragment[mTitles.length];
        fragments[0] = new PlayListFragment();
        fragments[1] = new MusicPlayerFragment();
        fragments[1] = new LocalFilesFragment();
        fragments[2] = new LocalFilesFragment();
        fragments[3] = new SettingFragment();
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(),mTitles,fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.mp_margin_large));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                radioButtons.get(position).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        radioButtons.get(DEFAULT_PAGE_INDEX).setChecked(true);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    @OnCheckedChanged({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files, R.id.radio_button_settings})
    public void onRadioButtonChecked(RadioButton button, boolean isChecked) {
        if (isChecked) {
            onItemChecked(radioButtons.indexOf(button));
        }
    }
    private void onItemChecked(int position) {
        viewPager.setCurrentItem(position);
        toolbar.setTitle(mTitles[position]);
    }


    private void testRxAndroid(){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
