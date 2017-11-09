package com.example.violet.violettank.ui.music;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.violet.violettank.R;
import com.example.violet.violettank.RxBus;
import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.data.source.AppRepository;
import com.example.violet.violettank.data.source.PreferenceManager;
import com.example.violet.violettank.event.PlaySongEvent;
import com.example.violet.violettank.player.IPlayback;
import com.example.violet.violettank.player.PlayMode;
import com.example.violet.violettank.player.PlaybackService;
import com.example.violet.violettank.ui.base.BaseFragment;
import com.example.violet.violettank.ui.widget.ShadowImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

public class MusicPlayerFragment extends BaseFragment implements MusicPlayerContract.View {

    @BindView(R.id.image_view_album)
    ShadowImageView imageViewAlbum;
    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.text_view_artist)
    TextView textViewArtist;
    @BindView(R.id.text_view_progress)
    TextView textViewProgress;
    @BindView(R.id.seek_bar)
    AppCompatSeekBar seekBar;
    @BindView(R.id.text_view_duration)
    TextView textViewDuration;
    @BindView(R.id.layout_progress)
    LinearLayout layoutProgress;
    @BindView(R.id.button_play_mode_toggle)
    AppCompatImageView buttonPlayModeToggle;
    @BindView(R.id.button_play_last)
    AppCompatImageView buttonPlayLast;
    @BindView(R.id.button_play_toggle)
    AppCompatImageView buttonPlayToggle;
    @BindView(R.id.button_play_next)
    AppCompatImageView buttonPlayNext;
    @BindView(R.id.button_favorite_toggle)
    AppCompatImageView buttonFavoriteToggle;
    @BindView(R.id.layout_play_controls)
    LinearLayout layoutPlayControls;
    Unbinder unbinder;
    private Handler mHandler = new Handler();
    private IPlayback mPlayer;
    private PlaybackService mServer;
    private MusicPlayerPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mPresenter = new MusicPlayerPresenter(getActivity(), AppRepository.getInstance(), this);
        mPresenter.subscribe();
    }


    // RXBus Events

    @Override
    protected Subscription subscribeEvents() {
        return RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof PlaySongEvent) {
                            onPlaySongEvent((PlaySongEvent) o);
                        }
//                        else if (o instanceof PlayListNowEvent) {
//                            onPlayListNowEvent((PlayListNowEvent) o);
//                        }
                    }
                })
                .subscribe(RxBus.defaultSubscriber());
    }

    @OnClick(R.id.button_play_toggle)
    public void onPlayToggleAction(View view) {
        if (mPlayer == null) return;

        if (mServer.isPlaying()) {
            mServer.pause();
        } else {
            mServer.play();
        }
    }

    private void onPlaySongEvent(PlaySongEvent songEvent){
        Song song = songEvent.song;
        playSong(song);
    }

    private void playSong(Song song){
        PlayList playList = new PlayList(song);
        playSong(playList, 0);
    }

    private void playSong(PlayList playList,int playIndex){
        if (playList == null) return;
        playList.setPlayMode(PreferenceManager.lastPlayMode(getContext()));
    }

    @Override
    public void handleError(Throwable error) {

    }
    @Override
    public void onPlaybackServiceBound(PlaybackService service) {
        mServer = service;
//        mPlayer.registerCallback(this);
    }

    @Override
    public void onPlaybackServiceUnbound() {

    }

    @Override
    public void onSongSetAsFavorite(@NonNull Song song) {

    }

    @Override
    public void onSongUpdated(@Nullable Song song) {

    }

    @Override
    public void updatePlayMode(PlayMode playMode) {
        if (playMode == null) {
            playMode = PlayMode.getDefault();
        }
        switch (playMode) {
            case LIST:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_list);
                break;
            case LOOP:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_loop);
                break;
            case SHUFFLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_shuffle);
                break;
            case SINGLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_single);
                break;
        }
    }

    @Override
    public void updatePlayToggle(boolean play) {

    }

    @Override
    public void updateFavoriteToggle(boolean favorite) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
