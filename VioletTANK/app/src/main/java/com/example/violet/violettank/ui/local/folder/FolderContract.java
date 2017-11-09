package com.example.violet.violettank.ui.local.folder;

import com.example.violet.violettank.data.model.Folder;
import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.ui.base.BasePresenter;
import com.example.violet.violettank.ui.base.BaseView;

import java.io.File;
import java.util.List;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/3.
 */

interface FolderContract {

    interface View extends BaseView {

        void showLoading();

        void hideLoading();

        void handleError(Throwable error);

        void onFoldersLoaded(List<Folder> folders);

        void onFoldersAdded(List<Folder> folders);

        void onFolderUpdated(Folder folder);

        void onFolderDeleted(Folder folder);

        void onPlayListCreated(PlayList playList);
    }

    interface Presenter extends BasePresenter {

        void loadFolders();

        void addFolders(List<File> folders, List<Folder> existedFolders);

        void refreshFolder(Folder folder);

        void deleteFolder(Folder folder);

        void createPlayList(PlayList playList);

        void addFolderToPlayList(Folder folder, PlayList playList);
    }
}

