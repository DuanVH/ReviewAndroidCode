package com.example.gem.reviewandroidcode.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.gem.reviewandroidcode.R;
import com.example.gem.reviewandroidcode.base.activity.BaseActivity;
import com.example.gem.reviewandroidcode.base.activity.vipe.ActivityContract;
import com.example.gem.reviewandroidcode.utils.DateTimeUtils;
import com.example.gem.reviewandroidcode.utils.VideoController;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;


public abstract class BasePlayerActivity<P extends ActivityContract.Presenter> extends BaseActivity<P>
    implements VideoController.OnVideoPlayerEventListener {

  private SimpleExoPlayerView mExoPlayer;
  public boolean mControllerShowing;
  protected ImageView mBtnPlayPause;
  protected VideoController mExoController;
  protected TextView tvCurrentPosition;
  protected TextView tvDuration;
  protected boolean mCanPerformSeek;
  protected View mProgressContainer;
  protected AppCompatSeekBar mSeekBar;
  protected int mVodCurrentPosition;
  protected int mTotalDuration;
  protected PlayerState mState;
  private int retryCount;

  protected Handler mHandler = new Handler();

  protected static final int SKIP_INTERVAL = 5000;
  protected static final int SEEK_PLAYER_DELAY = 1000;

  public enum PlayerState {
    PREPARING,
    PLAYING,
    FINISHED,
    PAUSE
  }

  public void onPlayerPrepared() {
    mState = PlayerState.PLAYING;
    dismissLoadingDialog();
    onPlayerPlay();
  }

  @Override
  public void onProgressChanged(int currentPosition, int duration) {
    mVodCurrentPosition = currentPosition;
    mTotalDuration = duration;
    updatePlayTime();
  }

  @Override
  public void onPlayerLoading(int progress) {
    if (mSeekBar.getSecondaryProgress() == 0) {
      onPlayerPrepared();
    }
    mSeekBar.setSecondaryProgress(progress);
  }

  @Override
  public void onPlayerBuffering() {
    showLoadingDialog();
  }

  @Override
  public void onPlayerPlay() {
    dismissLoadingDialog();
    mBtnPlayPause.setImageResource(R.drawable.btn_pause);
    mState = PlayerState.PLAYING;
  }

  @Override
  public void onPlayerPause() {
    dismissLoadingDialog();
    mBtnPlayPause.setImageResource(R.drawable.btn_play);
    mState = PlayerState.PAUSE;
  }

  @Override
  public void onPlayerFinish() {
    mBtnPlayPause.setImageResource(R.drawable.btn_play);
//    if (mExoController != null)
//      mExoController.pause();
//    mState = PlayerState.FINISHED;
    mExoController.startOver();
  }

  @Override
  public void onPlayerError() {
    showMessage(R.string.player_default_error);
    mExoController.pause();
    dismissLoadingDialog();
  }

  @Override
  public void onContinueToWatch(String currentPlayUrl) {
  }

  @Override
  public void onAdShowed() {
  }

  @Override
  public void onAdEnded() {
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initViews();
    setEventListener();
  }

  private void initViews() {
    mExoPlayer = findViewById(getExoPlayerResId());
    mSeekBar = findViewById(getSeekBarResId());
    mProgressContainer = findViewById(getProgressContainerResId());
    mBtnPlayPause = findViewById(getButtonPlayPauseResId());
    if (getCurrentDurationResId() != 0)
      tvCurrentPosition = findViewById(getCurrentDurationResId());
    if (getTotalDurationResId() != 0)
      tvDuration = findViewById(getTotalDurationResId());

    mExoController = new VideoController(BasePlayerActivity.this, mExoPlayer);

  }

  private void setEventListener() {
    mBtnPlayPause.setOnClickListener(view -> togglePlayer());
    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        pausePlayer();
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        seekPlayer();
      }
    });
  }

  private void updatePlayTime() {
    if (mSeekBar.getMax() != mTotalDuration) {
      mSeekBar.setMax(mTotalDuration);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
      mSeekBar.setProgress(mVodCurrentPosition, true);
    else
      mSeekBar.setProgress(mVodCurrentPosition);
    String currentPositionText = DateTimeUtils.getVideoTimeString(mVodCurrentPosition);
    String durationText = DateTimeUtils.getVideoTimeString(mTotalDuration);
    tvCurrentPosition.setText(currentPositionText);
    tvDuration.setText(durationText);
  }

  protected void play(String videoUrl, final int currentPosition) {
    if (videoUrl != null) {
      startPlaying(videoUrl, currentPosition);
    }
  }

  protected void startPlaying(String videoUrl, int currentPosition) {
    mExoController.setVideoPlayerEventListener(BasePlayerActivity.this);
    mExoController.playVideo(videoUrl, currentPosition);
  }


  protected void reset() {
    mState = PlayerState.PAUSE;
    if (mExoController != null) {
      mExoController.pause();
      mExoController.stop();
    }
    mTotalDuration = 0;
    mVodCurrentPosition = 0;
    mSeekBar.setProgress(0);
    mSeekBar.setSecondaryProgress(0);
    tvDuration.setText("00:00");
    tvCurrentPosition.setText("00:00");
    mProgressContainer.setFocusable(false);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mExoController != null && mExoController.getCurrentUrl() != null
        && mExoController.getCurrentPosition() > 0)
      mExoController.playVideo(mExoController.getCurrentUrl(),
          (int) mExoController.getCurrentPosition());
    if (mCanPerformSeek)
      cancelSeek();
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (mExoController != null) {
      mExoController.stopAndSavePosition();
    }
  }

  @Override
  protected void onDestroy() {
    releasePlayer();
    mHandler.removeCallbacks(seekPlayerTask);
    super.onDestroy();
  }

  private void releasePlayer() {
    if (mExoController != null) {
      mExoController.releasePlayer();
      mExoController = null;
    }
  }


  protected void resumePlayer() {
    mExoController.resume();
  }

  protected void pausePlayer() {
    mExoController.pause();
  }

  protected void togglePlayer() {
    if (mExoController.isPlaying()) {
      mExoController.pause();
    } else {
      mExoController.resume();
    }
  }

  protected void startOver() {
    mExoController.startOver();
  }

  protected void fastForward() {
    mCanPerformSeek = true;
    pausePlayer();
    if (mVodCurrentPosition < mTotalDuration) {
      mVodCurrentPosition += skipInterval();
      if (mVodCurrentPosition >= mTotalDuration)
        mVodCurrentPosition = mTotalDuration - 1000;
    }
    updatePlayTime();
    seekPlayer();
  }

  protected void rewind() {
    mCanPerformSeek = true;
    pausePlayer();
    if (mVodCurrentPosition >= 0)
      mVodCurrentPosition -= skipInterval();
    if (mVodCurrentPosition < 0)
      mVodCurrentPosition = 0;
    updatePlayTime();
    seekPlayer();
  }

  private void onSkipNext() {

  }

  private void onSkipPrevious() {

  }

  private void seekWithFrame() {
    if (mTotalDuration == 0) return;
    showInfo(true);
    pausePlayer();
  }

  private void seekPlayer() {
    Log.i("BasePlayerActivity", "seekPlayer");
    mHandler.removeCallbacks(seekPlayerTask);
    mHandler.postDelayed(seekPlayerTask, SEEK_PLAYER_DELAY);
//    mExoController.seekTo(mSeekBar.getProgress());
  }

  protected void cancelSeek() {
    mCanPerformSeek = false;
    showInfo(false);
    resumePlayer();
  }

  private Runnable seekPlayerTask = new Runnable() {
    @Override
    public void run() {
      mExoController.seekTo(mSeekBar.getProgress());
    }
  };


  protected boolean isPlaying() {
    return mExoController.isPlaying();
  }

  protected abstract void showInfo(boolean show);

  protected abstract int getExoPlayerResId();

  protected abstract int getCurrentDurationResId();

  protected abstract int getTotalDurationResId();

  protected abstract int getButtonPlayPauseResId();

  protected abstract int getProgressContainerResId();

  protected abstract int getSeekBarResId();

  protected int skipInterval() {
    return SKIP_INTERVAL;
  }
}
