package com.example.gem.reviewandroidcode.base.activity.vipe;

import android.content.Context;
import android.support.annotation.StringRes;

import io.reactivex.disposables.Disposable;

public interface ActivityContract {
  interface Interactor<P extends Presenter> {
    P getPresenter();

    void addDisposable(Disposable disposable);

    void dispose();
  }

  interface View<P extends Presenter> {
    P initPresenter();

    P getPresenter();

    void showMessage(String message);

    void showMessage(@StringRes int stringResId);

    void showLoadingDialog();

    void dismissLoadingDialog();

    void notifyNetworkChanged();
  }

  interface Presenter<V extends View, I extends Interactor> {
    V getView();

    I initInteractor();

    I getInteractor();

    void attachView(V view);

    Context getContext();

    void dispose();

    void onError(int errorCode, String errorMessage);
  }
}
