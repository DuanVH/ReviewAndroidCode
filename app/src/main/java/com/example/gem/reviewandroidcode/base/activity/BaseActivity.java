package com.example.gem.reviewandroidcode.base.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.example.gem.reviewandroidcode.base.activity.vipe.ActivityContract;
import com.example.gem.reviewandroidcode.pojo.dto.BusEvent;
import com.example.gem.reviewandroidcode.service.ConnectionHelper;
import com.example.gem.reviewandroidcode.utils.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends ActivityContract.Presenter> extends FragmentActivity
    implements ActivityContract.View<P>, DialogInterface.OnCancelListener {

  private P mPresenter;
  private Unbinder mUnbinder;
  private Dialog mLoadingDialog;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());

    mUnbinder = ButterKnife.bind(this);

    mPresenter = initPresenter();
    if (mPresenter != null)
      mPresenter.attachView(this);

    if (getFragmentContainerId() != 0 && getFirstFragment() != null)
      getSupportFragmentManager().beginTransaction()
          .add(getFragmentContainerId(), getFirstFragment())
          .commit();

    mLoadingDialog = DialogUtils.createLoadingDialog(this);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus)
      getWindow().getDecorView().setSystemUiVisibility(
//          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_FULLSCREEN
              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
      );
  }

  @Override
  protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    notifyNetworkChanged();
  }

  @Override
  protected void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override
  protected void onDestroy() {
    mUnbinder.unbind();
    if (getPresenter() != null)
      getPresenter().dispose();
    super.onDestroy();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(BusEvent busEvent) {
    if (busEvent.getType() == BusEvent.TYPE.CONNECTION_STATUS) {
      notifyNetworkChanged();
    }
  }

  @Override
  public P getPresenter() {
    return mPresenter;
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showMessage(@StringRes int stringResId) {
    Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showLoadingDialog() {
    if (!mLoadingDialog.isShowing())
      mLoadingDialog.show();
  }

  @Override
  public void dismissLoadingDialog() {
    if (mLoadingDialog != null && mLoadingDialog.isShowing())
      mLoadingDialog.dismiss();
  }

  @Override
  public void onCancel(DialogInterface dialogInterface) {

  }

  @Override
  public void notifyNetworkChanged() {
    if (!ConnectionHelper.getInstance().isNotified()) {
      if (!ConnectionHelper.getInstance().isOnline()) {
        // do nothing
      }
    }
  }

  protected abstract int getLayoutResId();

  protected abstract Fragment getFirstFragment();

  protected abstract int getFragmentContainerId();
}
