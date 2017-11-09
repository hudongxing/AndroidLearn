package com.example.violet.violettank.ui.playlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.violet.violettank.R;
import com.example.violet.violettank.RxBus;
import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.data.source.AppRepository;
import com.example.violet.violettank.event.FavoriteChangeEvent;
import com.example.violet.violettank.event.PlayListCreatedEvent;
import com.example.violet.violettank.event.PlayListUpdatedEvent;
import com.example.violet.violettank.event.PlaySongEvent;
import com.example.violet.violettank.ui.base.BaseFragment;
import com.example.violet.violettank.ui.common.DefaultDividerDecoration;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by violet.
 *
 * @ author VioLet.3
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

public class PlayListFragment extends BaseFragment implements PlayListContract.View, PlayListAdapter.AddPlayListCallback {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private PlayListAdapter mAdapter;

    PlayListContract.Presenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mAdapter = new PlayListAdapter(getContext(),null);
        mAdapter.setOnItemClickListener(new PlayListAdapter.OnItemClickListener() {
            @Override
            public void click() {

            }
        });
        mAdapter.setAddPlayListCallback(this);
        recyclerView.addItemDecoration(new DefaultDividerDecoration());

        mPresenter = new PlayListPresenter(AppRepository.getInstance(), this);
        mPresenter.loadPlayLists();

    }

    protected Subscription subscribeEvents() {
        return RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof PlayListCreatedEvent) {
                            onPlayListCreatedEvent((PlayListCreatedEvent) o);
                        } else if (o instanceof FavoriteChangeEvent) {
                            onFavoriteChangeEvent((FavoriteChangeEvent) o);
                        } else if (o instanceof PlayListUpdatedEvent) {
                            onPlayListUpdatedEvent((PlayListUpdatedEvent) o);
                        }else if (o instanceof PlaySongEvent){
                            Logger.i("RxBus传值成功");
                        }
                    }
                })
                .subscribe(RxBus.defaultSubscriber());
    }

    private void onPlayListCreatedEvent(PlayListCreatedEvent event) {
        mAdapter.getData().add(event.playList);
        mAdapter.notifyDataSetChanged();
//        mAdapter.updateFooterView();
    }
    
    private void onFavoriteChangeEvent(FavoriteChangeEvent event){
        mPresenter.loadPlayLists();
    }

    private void onPlayListUpdatedEvent(PlayListUpdatedEvent event){
        mPresenter.loadPlayLists();
    }


    @Override
    public void onAction(View actionView, int position) {

    }

    @Override
    public void onAddPlayList() {
        Logger.i("EditPlayListDialogFragment");
//        EditPlayListDialogFragment.createPlayList()
//                .setCallback(PlayListFragment.this)
//                .show(getFragmentManager().beginTransaction(), "CreatePlayList");
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleError(Throwable error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayListsLoaded(List<PlayList> playLists) {
        mAdapter.setData(playLists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayListCreated(PlayList playList) {
        mAdapter.getData().add(playList);
        mAdapter.notifyItemInserted(mAdapter.getData().size() - 1);
//        mAdapter.updateFooterView();
    }

    @Override
    public void onPlayListEdited(PlayList playList) {
//        mAdapter.getData().set(mEditIndex, playList);
//        mAdapter.notifyItemChanged(mEditIndex);
//        mAdapter.updateFooterView();
        // TODO: 2017/11/8
    }

    @Override
    public void onPlayListDeleted(PlayList playList) {

    }
}
