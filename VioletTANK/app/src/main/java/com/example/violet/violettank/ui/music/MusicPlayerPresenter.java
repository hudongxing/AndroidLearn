package com.example.violet.violettank.ui.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.data.source.AppRepository;
import com.example.violet.violettank.data.source.PreferenceManager;
import com.example.violet.violettank.player.PlayMode;
import com.example.violet.violettank.player.PlaybackService;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/9.
 */

public class MusicPlayerPresenter  implements MusicPlayerContract.Presenter  {

    private Context mContext;
    private MusicPlayerContract.View mView;
    private AppRepository mRepository;
    private CompositeSubscription mSubscriptions;

    private PlaybackService mPlaybackService;
    private boolean mIsServiceBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mPlaybackService = ((PlaybackService.LocalBinder) service).getService();
            mView.onPlaybackServiceBound(mPlaybackService);
//            mView.onSongUpdated(mPlaybackService.getPlayingSong());
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mPlaybackService = null;
            mView.onPlaybackServiceUnbound();
        }
    };

    public MusicPlayerPresenter(Context context, AppRepository repository, MusicPlayerContract.View view) {
        mContext = context;
        mView = view;
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        bindPlaybackService();
        retrieveLastPlayMode();

        // TODO
//        if (mPlaybackService != null && mPlaybackService.isPlaying()) {
//            mView.onSongUpdated(mPlaybackService.getPlayingSong());
//        } else {
//            // - load last play list/folder/song
//        }
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void retrieveLastPlayMode() {
        PlayMode lastPlayMode = PreferenceManager.lastPlayMode(mContext);
        mView.updatePlayMode(lastPlayMode);
    }

    @Override
    public void setSongAsFavorite(Song song, boolean favorite) {

    }

    @Override
    public void bindPlaybackService() {
        mContext.bindService(new Intent(mContext, PlaybackService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsServiceBound = true;
    }

    @Override
    public void unbindPlaybackService() {

    }
}
