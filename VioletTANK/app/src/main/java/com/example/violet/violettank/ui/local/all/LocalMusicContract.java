package com.example.violet.violettank.ui.local.all;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.ui.base.BasePresenter;
import com.example.violet.violettank.ui.base.BaseView;

import java.util.List;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

interface LocalMusicContract {

    interface View extends BaseView {

        LoaderManager getLoaderManager();

        Context getContext();

        void showProgress();

        void hideProgress();

        void emptyView(boolean visible);

        void handleError(Throwable error);

        void onLocalMusicLoaded(List<Song> songs);
    }

    interface Presenter extends BasePresenter {

        void loadLocalMusic();
    }
}
