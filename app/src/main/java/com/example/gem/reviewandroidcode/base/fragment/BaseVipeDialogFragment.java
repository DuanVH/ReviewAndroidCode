package com.example.gem.reviewandroidcode.base.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.gem.reviewandroidcode.base.fragment.vipe.BaseFragmentContract;

public abstract class BaseVipeDialogFragment<P extends BaseFragmentContract.Presenter> extends Fragment
    implements BaseFragmentContract.View<P> {

  private P mBvdPresenter;
  protected Activity self;
  private Dialog mBvdLoadingDialog;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    self = getActivity();
  }

  @Override
  public P getPresenter() {
    return mBvdPresenter;
  }

  @Override
  public void setPresenter(P presenter) {
    mBvdPresenter = presenter;
  }

  @Override
  public Context getMvpContext() {
    return getActivity();
  }

  @Override
  public void showLoadingDialog() {
    if (mBvdLoadingDialog != null)
      mBvdLoadingDialog.show();
  }

  @Override
  public void dismissLoadingDialog() {
    if (mBvdLoadingDialog != null && mBvdLoadingDialog.isShowing())
      mBvdLoadingDialog.dismiss();
  }

  @Override
  public void showMessage(String message) {
    if (getActivity() != null && message != null)
      Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showMessage(@StringRes int messageResId) {
    if (getActivity() != null)
      Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT).show();
  }
}
