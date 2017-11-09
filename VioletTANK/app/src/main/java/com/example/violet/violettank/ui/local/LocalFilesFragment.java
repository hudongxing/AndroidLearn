package com.example.violet.violettank.ui.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.violet.violettank.R;
import com.example.violet.violettank.ui.base.BaseFragment;
import com.example.violet.violettank.ui.local.all.AllLocalMusicFragment;
import com.example.violet.violettank.ui.local.folder.FolderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

public class LocalFilesFragment extends BaseFragment {


    static final int DEFAULT_SEGMENT_INDEX = 0;

    @BindViews({R.id.radio_button_all, R.id.radio_button_folder})
    List<RadioButton> segmentedControls;

    List<Fragment> mFragments = new ArrayList<>(2);

    final int[] FRAGMENT_CONTAINER_IDS = {
            R.id.layout_fragment_container_all, R.id.layout_fragment_container_folder
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments.add(new AllLocalMusicFragment());
        mFragments.add(new FolderFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_files, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        for (int i=0;i<mFragments.size();i++){
            Fragment fragment = mFragments.get(i);
            fragmentTransaction.add(FRAGMENT_CONTAINER_IDS[i], fragment, fragment.getTag());
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
        segmentedControls.get(DEFAULT_SEGMENT_INDEX).setChecked(true);
    }

    @OnCheckedChanged({R.id.radio_button_all, R.id.radio_button_folder})
    public void onSegmentedChecked(RadioButton radioButton, boolean isChecked) {
        int index = segmentedControls.indexOf(radioButton);
        Fragment fragment = mFragments.get(index);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (isChecked) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
