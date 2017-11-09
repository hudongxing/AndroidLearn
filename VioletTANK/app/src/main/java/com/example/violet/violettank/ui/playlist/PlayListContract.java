package com.example.violet.violettank.ui.playlist;

import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.ui.base.BasePresenter;
import com.example.violet.violettank.ui.base.BaseView;

import java.util.List;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

interface PlayListContract {

    interface View extends BaseView {

        void showLoading();

        void hideLoading();

        void handleError(Throwable error);

        void onPlayListsLoaded(List<PlayList> playLists);

        void onPlayListCreated(PlayList playList);

        void onPlayListEdited(PlayList playList);

        void onPlayListDeleted(PlayList playList);
    }

    interface Presenter extends BasePresenter {

        void loadPlayLists();

        void createPlayList(PlayList playList);

        void editPlayList(PlayList playList);

        void deletePlayList(PlayList playList);
    }
}

