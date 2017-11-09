package com.example.violet.violettank.ui.playlist;

import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.data.source.AppRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

public class PlayListPresenter implements PlayListContract.Presenter {

    private PlayListContract.View mView;
    private AppRepository mRepository;
    private CompositeSubscription mSubscriptions;

    public PlayListPresenter(AppRepository repository, PlayListContract.View view) {
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
    public void loadPlayLists() {
        Subscription subscription = mRepository.playLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PlayList>>() {
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
                    public void onNext(List<PlayList> playLists) {
                        mView.onPlayListsLoaded(playLists);
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
                    public void onNext(PlayList result) {
                        mView.onPlayListCreated(result);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void editPlayList(PlayList playList) {
        Subscription subscription = mRepository
                .update(playList)
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
                    public void onNext(PlayList result) {
                        mView.onPlayListEdited(result);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void deletePlayList(PlayList playList) {
        Subscription subscription = mRepository.delete(playList)
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
                        mView.onPlayListDeleted(playList);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
