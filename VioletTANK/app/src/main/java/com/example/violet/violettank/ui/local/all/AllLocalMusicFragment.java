package com.example.violet.violettank.ui.local.all;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.violet.violettank.R;
import com.example.violet.violettank.RxBus;
import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.data.source.AppRepository;
import com.example.violet.violettank.event.PlaySongEvent;
import com.example.violet.violettank.ui.base.BaseFragment;
import com.example.violet.violettank.ui.common.DefaultDividerDecoration;
import com.example.violet.violettank.ui.widget.RecyclerViewFastScroller;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

public class AllLocalMusicFragment  extends BaseFragment implements LocalMusicContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fast_scroller)
    RecyclerViewFastScroller fastScroller;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.text_view_empty)
    View emptyView;

    LocalMusicAdapter mAdapter;
    LocalMusicContract.Presenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_local_music, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new LocalMusicAdapter(getActivity(), null);


        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DefaultDividerDecoration());
        fastScroller.setRecyclerView(recyclerView);

        mAdapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Song song = mAdapter.getItem(position);
                Logger.i("开始播放歌曲");
                // TODO: 2017/11/3
                RxBus.getInstance().post(new PlaySongEvent(song));
            }
        });
        mPresenter = new LocalMusicPresenter(AppRepository.getInstance(), this);
        mPresenter.subscribe();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void emptyView(boolean visible) {
        emptyView.setVisibility(visible ? View.VISIBLE : View.GONE);
        fastScroller.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    @Override
    public void handleError(Throwable error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocalMusicLoaded(List<Song> songs) {
        mAdapter.setData(songs);
        mAdapter.notifyDataSetChanged();
    }


    public void setPresenter() {

    }
}
