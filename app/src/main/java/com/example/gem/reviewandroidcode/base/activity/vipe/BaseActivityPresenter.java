package com.example.gem.reviewandroidcode.base.activity.vipe;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BaseActivityPresenter<V extends ActivityContract.View, I extends ActivityContract.Interactor>
    implements ActivityContract.Presenter<V, I> {

  private V mView;
  private I mInteractor;

  public BaseActivityPresenter() {
    mInteractor = initInteractor();
  }

  @Override
  public V getView() {
    return mView;
  }

  @Override
  public I getInteractor() {
    return mInteractor;
  }

  @Override
  public void attachView(V view) {
    mView = view;
  }

  @Override
  public Context getContext() {
    return (Context) mView;
  }

  @Override
  public void dispose() {
    getInteractor().dispose();
  }

  @Override
  public void onError(int errorCode, String errorMessage) {
    getView().dismissLoadingDialog();
  }
}
