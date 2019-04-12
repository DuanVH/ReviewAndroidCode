package com.example.gem.reviewandroidcode.base.fragment.vipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BaseFragmentPresenter<V extends BaseFragmentContract.View, I extends BaseFragmentContract.Interactor>
    implements BaseFragmentContract.Presenter<V, I> {

  private I mInteractor;
  private V mView;

  public BaseFragmentPresenter() {
    mView = initView();
    mInteractor = initInteractor();
    mView.setPresenter(this);
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
  public Fragment getFragment() {
    return (Fragment) mView;
  }

  public Fragment getFragment(Bundle bundle) {
    ((Fragment) mView).setArguments(bundle);
    return (Fragment) mView;
  }

  @Override
  public Context getContext() {
    return mView.getMvpContext();
  }

  @Override
  public void onError(String errorMessageCode) {
    getView().dismissLoadingDialog();
    getView().showMessage(errorMessageCode);
  }

  @Override
  public void onError(int errorMessageId) {
    getView().dismissLoadingDialog();
    getView().showMessage(errorMessageId);
  }

  @Override
  public void dispose() {
    if (getInteractor() != null)
      getInteractor().dispose();
  }
}
