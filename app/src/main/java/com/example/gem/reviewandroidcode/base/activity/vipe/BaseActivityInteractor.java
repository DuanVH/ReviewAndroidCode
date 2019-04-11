package com.example.gem.reviewandroidcode.base.activity.vipe;

import com.example.gem.reviewandroidcode.webservice.base.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BaseActivityInteractor<P extends ActivityContract.Presenter>
implements ActivityContract.Interactor<P> {

  private P mPresenter;
  private List<Disposable> mBaseActivityDisposables;

  public BaseActivityInteractor(P presenter) {
    this.mPresenter = presenter;
    mBaseActivityDisposables = new ArrayList<>();
  }

  @Override
  public P getPresenter() {
    return mPresenter;
  }

  @Override
  public void addDisposable(Disposable disposable) {
    mBaseActivityDisposables.add(disposable);
  }

  @Override
  public void dispose() {
    if (mBaseActivityDisposables != null && !mBaseActivityDisposables.isEmpty()) {
      for (Disposable disposable : mBaseActivityDisposables) {
        if (!disposable.isDisposed()) {
          disposable.dispose();
        }
      }
    }
  }

  protected void handleError(Throwable throwable, ErrorHandler.ErrorHandlerCallback callback) {
    ErrorHandler errorHandler = ErrorHandler.getInstance();
    errorHandler.handleError(throwable, callback);
  }
}
