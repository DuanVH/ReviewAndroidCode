package com.example.gem.reviewandroidcode.base.fragment.vipe;

import com.example.gem.reviewandroidcode.utils.Logger;
import com.example.gem.reviewandroidcode.webservice.base.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BaseFragmentInteractor<P extends BaseFragmentContract.Presenter>
    implements BaseFragmentContract.Interactor<P> {

  private P mPresenter;
  private List<Disposable> baseFragmentDisposable;

  public BaseFragmentInteractor(P presenter) {
    this.mPresenter = presenter;
    baseFragmentDisposable = new ArrayList<>();
  }

  @Override
  public P getPresenter() {
    return mPresenter;
  }

  @Override
  public void dispose() {
    if (baseFragmentDisposable != null && !baseFragmentDisposable.isEmpty()) {
      for (Disposable disposable : baseFragmentDisposable) {
        if (!disposable.isDisposed()) {
          disposable.dispose();
        }
        baseFragmentDisposable.clear();
      }
    }
  }

  @Override
  public void addDisposable(Disposable disposable) {
    baseFragmentDisposable.add(disposable);
  }

  protected void handleError(Throwable throwable, ErrorHandler.ErrorHandlerCallback callback) {
    Logger.logException(throwable);
    ErrorHandler errorHandler = ErrorHandler.getInstance();
    errorHandler.handleError(throwable, callback);
//    getPresenter().onError(errorHandle.getMessage());
  }
}
