package com.example.violet.violettank.data.source;

import com.example.violet.violettank.Injection;
import com.example.violet.violettank.data.model.Folder;
import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.data.source.db.LiteOrmHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

public class AppRepository implements AppContract {

    private static volatile AppRepository sInstance;

    private AppLocalDataSource mLocalDataSource;

    private List<PlayList> mCachedPlayLists;

    private AppRepository() {
        mLocalDataSource = new AppLocalDataSource(Injection.provideContext(), LiteOrmHelper.getInstance());
    }

    public static AppRepository getInstance() {
        if (sInstance == null) {
            synchronized (AppRepository.class) {
                if (sInstance == null) {
                    sInstance = new AppRepository();
                }
            }
        }
        return sInstance;
    }

    // Play List

    @Override
    public Observable<List<PlayList>> playLists() {
        return mLocalDataSource.playLists()
                .doOnNext(new Action1<List<PlayList>>() {
                    @Override
                    public void call(List<PlayList> playLists) {
                        mCachedPlayLists = playLists;
                    }
                });
    }

    @Override
    public List<PlayList> cachedPlayLists() {
        if (mCachedPlayLists == null) {
            return new ArrayList<>(0);
        }
        return mCachedPlayLists;
    }

    @Override
    public Observable<PlayList> create(PlayList playList) {
        return mLocalDataSource.create(playList);
    }

    @Override
    public Observable<PlayList> update(PlayList playList) {
        return mLocalDataSource.update(playList);
    }

    @Override
    public Observable<PlayList> delete(PlayList playList) {
        return mLocalDataSource.delete(playList);
    }

    // Folders

    @Override
    public Observable<List<Folder>> folders() {
        return mLocalDataSource.folders();
    }

    @Override
    public Observable<Folder> create(Folder folder) {
        return mLocalDataSource.create(folder);
    }

    @Override
    public Observable<List<Folder>> create(List<Folder> folders) {
        return mLocalDataSource.create(folders);
    }

    @Override
    public Observable<Folder> update(Folder folder) {
        return mLocalDataSource.update(folder);
    }

    @Override
    public Observable<Folder> delete(Folder folder) {
        return mLocalDataSource.delete(folder);
    }

    @Override
    public Observable<List<Song>> insert(List<Song> songs) {
        return mLocalDataSource.insert(songs);
    }

    @Override
    public Observable<Song> update(Song song) {
        return mLocalDataSource.update(song);
    }

    @Override
    public Observable<Song> setSongAsFavorite(Song song, boolean favorite) {
        return mLocalDataSource.setSongAsFavorite(song, favorite);
    }
}

