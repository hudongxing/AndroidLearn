package com.example.violet.violettank.ui.music;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.player.PlayMode;
import com.example.violet.violettank.player.PlaybackService;
import com.example.violet.violettank.ui.base.BasePresenter;
import com.example.violet.violettank.ui.base.BaseView;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

interface MusicPlayerContract {

    interface View extends BaseView {

        void handleError(Throwable error);

        void onPlaybackServiceBound(PlaybackService service);

        void onPlaybackServiceUnbound();

        void onSongSetAsFavorite(@NonNull Song song);

        void onSongUpdated(@Nullable Song song);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);

        void updateFavoriteToggle(boolean favorite);
    }

    interface Presenter extends BasePresenter {

        void retrieveLastPlayMode();

        void setSongAsFavorite(Song song, boolean favorite);

        void bindPlaybackService();

        void unbindPlaybackService();
    }
}
