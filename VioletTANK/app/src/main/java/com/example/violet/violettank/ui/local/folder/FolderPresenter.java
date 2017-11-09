package com.example.violet.violettank.ui.local.folder;

import com.example.violet.violettank.RxBus;
import com.example.violet.violettank.data.model.Folder;
import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.data.source.AppRepository;
import com.example.violet.violettank.utils.FileUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/6.
 */

public class FolderPresenter implements FolderContract.Presenter {



    private FolderContract.View mView;
    private AppRepository mRepository;
    private CompositeSubscription mSubscriptions;

    public FolderPresenter(AppRepository repository, FolderContract.View view) {
        mView = view;
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();

    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadFolders() {
        Subscription subscription = mRepository.folders()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<List<Folder>>() {
                    @Override
                    public void call(List<Folder> folders) {
                        Collections.sort(folders, new Comparator<Folder>() {
                            @Override
                            public int compare(Folder f1, Folder f2) {
                                return f1.getName().compareToIgnoreCase(f2.getName());
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Folder>>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(List<Folder> folders) {
                        mView.onFoldersLoaded(folders);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void addFolders(List<File> folders, List<Folder> existedFolders) {
        Subscription subscription = Observable.from(folders)
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        for (Folder folder : existedFolders) {
                            if (file.getAbsolutePath().equals(folder.getPath())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .flatMap(new Func1<File, Observable<Folder>>() {
                    @Override
                    public Observable<Folder> call(File file) {
                        Folder folder = new Folder();
                        folder.setName(file.getName());
                        folder.setPath(file.getAbsolutePath());
                        List<Song> musicFiles = FileUtils.musicFiles(file);
                        folder.setSongs(musicFiles);
                        folder.setNumOfSongs(musicFiles.size());
                        return Observable.just(folder);
                    }
                })
                .toList()
                .flatMap(new Func1<List<Folder>, Observable<List<Folder>>>() {
                    @Override
                    public Observable<List<Folder>> call(List<Folder> folders) {
                        return mRepository.create(folders);
                    }
                })
                .doOnNext(new Action1<List<Folder>>() {
                    @Override
                    public void call(List<Folder> folders) {
                        Collections.sort(folders, new Comparator<Folder>() {
                            @Override
                            public int compare(Folder f1, Folder f2) {
                                return f1.getName().compareToIgnoreCase(f2.getName());
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Folder>>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(List<Folder> allNewFolders) {
                        mView.onFoldersAdded(allNewFolders);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void refreshFolder(Folder folder) {
        Subscription subscription = Observable.just(FileUtils.musicFiles(new File(folder.getPath())))
                .flatMap(new Func1<List<Song>, Observable<Folder>>() {
                    @Override
                    public Observable<Folder> call(List<Song> songs) {
                        folder.setSongs(songs);
                        folder.setNumOfSongs(songs.size());
                        return mRepository.update(folder);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Folder>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(Folder folder) {
                        mView.onFolderUpdated(folder);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void deleteFolder(Folder folder) {
        Subscription subscription = mRepository.delete(folder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Folder>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(Folder folder) {
                        mView.onFolderDeleted(folder);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void createPlayList(PlayList playList) {
        Subscription subscription = mRepository
                .create(playList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PlayList>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(PlayList playList) {
                        mView.onPlayListCreated(playList);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void addFolderToPlayList(Folder folder, PlayList playList) {
        if (folder.getSongs().isEmpty()) return;

        if (playList.isFavorite()) {
            for (Song song : folder.getSongs()) {
                song.setFavorite(true);
            }
        }
        playList.addSong(folder.getSongs(), 0);
        Subscription subscription = mRepository.update(playList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PlayList>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(PlayList playList) {
                        Logger.i("RxBus传值至PlayListUpdatedEvent");
//                        RxBus.getInstance().post(new PlayListUpdatedEvent(playList));
                    }
                });
        mSubscriptions.add(subscription);
    }
}
