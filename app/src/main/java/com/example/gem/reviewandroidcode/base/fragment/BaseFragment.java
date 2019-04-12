package com.example.gem.reviewandroidcode.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.gem.reviewandroidcode.base.activity.BaseActivity;
import com.example.gem.reviewandroidcode.base.fragment.vipe.BaseFragmentContract;
import com.example.gem.reviewandroidcode.utils.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BaseFragmentContract.Presenter> extends Fragment
    implements BaseFragmentContract.View<P> {

  protected FragmentActivity self;
  private P mBaseFragmentPresenter;
  private Unbinder mUnbinder;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    self = getActivity();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutResId(), container, false);
    mUnbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onDestroyView() {
    getPresenter().dispose();
    super.onDestroyView();
  }

  @Override
  public P getPresenter() {
    return mBaseFragmentPresenter;
  }

  @Override
  public void setPresenter(P presenter) {
    mBaseFragmentPresenter = presenter;
  }

  @Override
  public Context getMvpContext() {
    return getActivity();
  }

  @Override
  public void showLoadingDialog() {
    if (getBaseActivity() != null) {
      getBaseActivity().showLoadingDialog();
      return;
    }
  }

  @Override
  public void dismissLoadingDialog() {
    if (getBaseActivity() != null) {
      getBaseActivity().dismissLoadingDialog();
      return;
    }
  }

  @Override
  public void showMessage(String message) {
    if (getActivity() != null && message != null)
      Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showMessage(@StringRes int messageResId) {
    if (getActivity() != null)
      Toast.makeText(getActivity(), getString(messageResId), Toast.LENGTH_SHORT).show();
  }

  public void showKeyboard(View v) {
    InputMethodManager imm = (InputMethodManager) self.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
  }

  public void hideKeyboard(View v) {
    InputMethodManager imm = (InputMethodManager) self.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
  }

  public void onLoadingDialogCanceled(){}

  protected BaseActivity getBaseActivity() {
    try {
      return (BaseActivity) getActivity();
    } catch (Exception e) {
      Logger.logException(e);
      return null;
    }
  }

  public void onBackStackChanged(boolean resume){}

  protected abstract int getLayoutResId();
}
