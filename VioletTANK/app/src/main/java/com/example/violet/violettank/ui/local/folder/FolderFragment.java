package com.example.violet.violettank.ui.local.folder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.violet.violettank.R;
import com.example.violet.violettank.RxBus;
import com.example.violet.violettank.data.model.Folder;
import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.data.source.AppRepository;
import com.example.violet.violettank.event.AddFolderEvent;
import com.example.violet.violettank.ui.base.BaseFragment;
import com.example.violet.violettank.ui.common.DefaultDividerDecoration;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/3.
 */

public class FolderFragment  extends BaseFragment implements FolderContract.View,FolderAdapter.AddFolderCallback {
    private View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    private FolderAdapter mAdapter;
    private int mUpdateIndex, mDeleteIndex;

    FolderContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_added_folders,container,false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mAdapter = new FolderAdapter(getActivity(), null);

        // TODO: 2017/11/6
        mAdapter.setAddFolderCallback(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DefaultDividerDecoration());
        mPresenter = new FolderPresenter(AppRepository.getInstance(),this);
        mPresenter.loadFolders();
    }


    protected Subscription subscribeEvents() {
        return RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof AddFolderEvent) {
                            onAddFolders((AddFolderEvent) o);
                        }
                    }
                })
                .subscribe(RxBus.defaultSubscriber());
    }

    private void onAddFolders(AddFolderEvent event) {
        final List<File> folders = event.folders;
        final List<Folder> existedFolders = mAdapter.getData();
        mPresenter.addFolders(folders, existedFolders);
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
    public void onFoldersLoaded(List<Folder> folders) {
        mAdapter.setData(folders);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFoldersAdded(List<Folder> folders) {
        int newItemCount = folders.size() - (mAdapter.getData() == null ? 0 : mAdapter.getData().size());
        mAdapter.setData(folders);
        mAdapter.notifyDataSetChanged();
        mAdapter.updateFooterView();
        if (newItemCount > 0) {
            String toast = getResources().getQuantityString(
                    R.plurals.mp_folders_created_formatter,
                    newItemCount,
                    newItemCount
            );
            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFolderUpdated(Folder folder) {
        mAdapter.getData().set(mUpdateIndex, folder);
        mAdapter.notifyItemChanged(mUpdateIndex);
    }

    @Override
    public void onFolderDeleted(Folder folder) {
        mAdapter.getData().remove(mDeleteIndex);
        mAdapter.notifyItemRemoved(mDeleteIndex);
        mAdapter.updateFooterView();
    }

    @Override
    public void onPlayListCreated(PlayList playList) {
        // TODO: 2017/11/8
//        RxBus.getInstance().post(new PlayListCreatedEvent(playList));
//        Toast.makeText(getActivity(), getString(R.string.mp_play_list_created, playList.getName()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAction(View actionView, int position) {
        final Folder folder = mAdapter.getItem(position);
        PopupMenu actionMenu = new PopupMenu(getActivity(), actionView, Gravity.END | Gravity.BOTTOM);
        actionMenu.inflate(R.menu.folders_action);
        actionMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_add_to_play_list:
//                        new AddToPlayListDialogFragment()
//                                .setCallback(new AddToPlayListDialogFragment.Callback() {
//                                    @Override
//                                    public void onPlayListSelected(PlayList playList) {
//                                        mPresenter.addFolderToPlayList(folder, playList);
//                                    }
//                                })
//                                .show(getFragmentManager().beginTransaction(), "AddToPlayList");
                        break;
                    case R.id.menu_item_create_play_list:
                        PlayList playList = PlayList.fromFolder(folder);
//                        EditPlayListDialogFragment.editPlayList(playList)
//                                .setCallback(new EditPlayListDialogFragment.Callback() {
//                                    @Override
//                                    public void onCreated(PlayList playList) {
//                                        // Empty
//                                    }
//
//                                    @Override
//                                    public void onEdited(PlayList playList) {
//                                        mPresenter.createPlayList(playList);
//                                    }
//                                })
//                                .show(getFragmentManager().beginTransaction(), "CreatePlayList");
                        break;
                    case R.id.menu_item_refresh:
                        mUpdateIndex = position;
                        mPresenter.refreshFolder(folder);
                        break;
                    case R.id.menu_item_delete:
                        mDeleteIndex = position;
                        mPresenter.deleteFolder(folder);
                        break;
                }
                return true;
            }
        });
        actionMenu.show();
    }

    @Override
    public void onAddFolder() {
        // TODO: 2017/11/8
        Logger.i("FileSystemActivity");
//        startActivity(new Intent(getActivity(), FileSystemActivity.class));
    }
}
