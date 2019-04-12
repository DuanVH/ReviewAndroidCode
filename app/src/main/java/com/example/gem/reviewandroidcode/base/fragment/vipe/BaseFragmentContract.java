package com.example.gem.reviewandroidcode.base.fragment.vipe;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import io.reactivex.disposables.Disposable;

public interface BaseFragmentContract {

  interface Interactor<P extends Presenter> {
    P getPresenter();

    void dispose();

    void addDisposable(Disposable disposable);
  }

  interface View<P extends Presenter> {
    P getPresenter();

    void setPresenter(P presenter);

    Context getMvpContext();

    void showLoadingDialog();

    void dismissLoadingDialog();

    void showMessage(String message);

    void showMessage(@StringRes int messageResId);
  }

  interface Presenter<V extends View, I extends Interactor> {
    V getView();

    V initView();

    I getInteractor();

    I initInteractor();

    Fragment getFragment();

    Context getContext();

    void onError(String errorMessageCode);

    void onError(int errorMessageID);

    void dispose();
  }
}
