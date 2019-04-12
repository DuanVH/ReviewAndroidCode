package com.example.gem.reviewandroidcode.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.WebView;

import com.example.gem.reviewandroidcode.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;


/**
 * Created by GEM on 11/22/2017.
 */

public class VideoController {
  private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
  private static final int UPDATE_PROGRESS_INTERVAL = 1000;
  private static final String FOCUS_SKIP_BUTTON_WORKAROUND_JS = "javascript:try{ document.getElementsByClassName(\"videoAdUiSkipButton\")[0].focus(); } catch (e) {}";

  private Context mContext;
  private SimpleExoPlayerView mVideoView;

  private SimpleExoPlayer mVideoPlayer;
  private DataSource.Factory mMediaDataSourceFactory;
  private DefaultTrackSelector mTrackSelector;
  private Handler mMainHandler;
  private String mUserAgent;

  private Dialog mContinueDialog;

  private String mCurrentUrl;
  private String mPreviousUrl;

  private int mCurrentWindowIndex;
  private long mInitialPosition;
  private long mCurrentPosition;
  private boolean mIsFirstPlay;

  // Ads
//  private ImaAdsLoader mImaAdsLoader;
//  private boolean mAdPlaying;

  private OnVideoPlayerEventListener mListener;

  private Handler mUpdateProgressHandler = new Handler();
  private Runnable mUpdateProgressTask = new Runnable() {
    @Override
    public void run() {
      if (mVideoPlayer != null && mListener != null) {
        if (mVideoPlayer.getPlayWhenReady()) {
          mCurrentPosition = mVideoPlayer.getCurrentPosition();
          mCurrentWindowIndex = mVideoPlayer.getCurrentWindowIndex();
          mListener.onProgressChanged((int) mCurrentPosition, (int) mVideoPlayer.getDuration());
          mUpdateProgressHandler.postDelayed(this, UPDATE_PROGRESS_INTERVAL);
        }
        if (mVideoPlayer.isLoading())
          mListener.onPlayerLoading((int) mVideoPlayer.getBufferedPosition());
      }
    }
  };

  private DialogInterface.OnClickListener mOnContinueButtonClicked = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
      dialogInterface.dismiss();
      if (TextUtils.isEmpty(mPreviousUrl)) {
        mVideoPlayer.seekTo(mInitialPosition);
        mVideoPlayer.setPlayWhenReady(true);
      } else {
        stop();
        initVideoPlayer();
        prepareVideo(mPreviousUrl, "");
        mVideoPlayer.seekTo(mInitialPosition);
        mIsFirstPlay = false;
        if (mListener != null) {
          mListener.onContinueToWatch(mPreviousUrl);
        }
      }
    }
  };
  private DialogInterface.OnClickListener mOnStartOverButtonClicked = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
      dialogInterface.dismiss();
      mInitialPosition = 0;
      mVideoPlayer.setPlayWhenReady(true);
    }
  };

  public VideoController(Context context, SimpleExoPlayerView videoView) {
    mContext = context;
    mVideoView = videoView;
    mUserAgent = Util.getUserAgent(context, "MyclipSmartTv");
    init();
  }

  public void setVideoPlayerEventListener(OnVideoPlayerEventListener listener) {
    mListener = listener;
  }

  public long getCurrentPosition() {
    return mCurrentPosition;
  }

  public String getCurrentUrl() {
    return mCurrentUrl;
  }

  public void playVideo(String url, int initialPosition) {
    stop();
    initVideoPlayer();
    mCurrentUrl = url;
    mPreviousUrl = "";
    mInitialPosition = initialPosition;
    mIsFirstPlay = true;
    prepareVideo(url, "");
  }

  public void playVideo(String currentUrl, String previousUrl, int initialPosition) {
    playVideo(currentUrl, initialPosition);
    mPreviousUrl = previousUrl;
  }

  public void playVideoWithAds(String url, String adsUrl, int initialPosition) {
    stop();
    initVideoPlayer();
    mCurrentUrl = url;
    mPreviousUrl = "";
    mInitialPosition = initialPosition;
    mIsFirstPlay = true;
    prepareVideo(url, adsUrl);
  }

  public void playVideoWithAds(String currentUrl, String previousUrl, String adsUrl, int initialPosition) {
    playVideoWithAds(currentUrl, adsUrl, initialPosition);
    mPreviousUrl = previousUrl;
  }

  public void startOver() {
    if (mVideoPlayer != null) {
      mCurrentWindowIndex = 0;
      mCurrentPosition = 0;
      mVideoPlayer.seekTo(0);
      mVideoView.postDelayed(() -> {
        if (!mVideoPlayer.getPlayWhenReady())
          mVideoPlayer.setPlayWhenReady(true);
      }, 100);
    }
  }

  public void restart(String url, String adsUrl) {
    if (mVideoPlayer != null) {
      mVideoPlayer.stop();
      mVideoPlayer.release();
    }

    initVideoPlayer();
    prepareVideo(url, adsUrl);
  }

  public void resume() {
    if (mVideoPlayer != null && !mVideoPlayer.getPlayWhenReady())
      mVideoPlayer.setPlayWhenReady(true);
  }

  public void pause() {
    if (mVideoPlayer != null && mVideoPlayer.getPlayWhenReady()) {
      mVideoPlayer.setPlayWhenReady(false);
      mCurrentPosition = mVideoPlayer.getCurrentPosition();
      mCurrentWindowIndex = mVideoPlayer.getCurrentWindowIndex();
    }
  }

  public void stopAndSavePosition() {
    if (mVideoPlayer != null) {
      mVideoPlayer.stop();
      mCurrentPosition = mVideoPlayer.getCurrentPosition();
      mCurrentWindowIndex = mVideoPlayer.getCurrentWindowIndex();
      mVideoPlayer.release();
    }
  }

  public void stop() {
    if (mVideoPlayer != null) {
      mVideoPlayer.stop();
      mCurrentPosition = 0;
      mCurrentWindowIndex = 0;
      mVideoPlayer.release();
    }
  }

  public void releasePlayer() {
    if (mVideoPlayer != null) {
//      mVideoPlayer.stop();
      mVideoPlayer.release();
      mVideoPlayer = null;
      mTrackSelector = null;
    }
  }

  public void seekTo(int position) {
    if (mVideoPlayer != null) {
      mVideoPlayer.seekTo(position);
      mVideoPlayer.setPlayWhenReady(true);
    }
  }

  public long getDuration() {
    if (mVideoPlayer != null)
      return mVideoPlayer.getDuration();
    return 0;
  }

  public boolean isPlaying() {
    return mVideoPlayer != null && mVideoPlayer.getPlayWhenReady();
  }

  private void init() {
    mMainHandler = new Handler();
    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(mMainHandler, (elapsedMs, bytes, bitrate) -> {

    });
    TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
    mTrackSelector =
            new DefaultTrackSelector(videoTrackSelectionFactory);
    mTrackSelector.setParameters(mTrackSelector.getParameters().withMaxVideoSize(3086, 2160));
    mMediaDataSourceFactory = new DefaultDataSourceFactory(mContext, mUserAgent, (DefaultBandwidthMeter)bandwidthMeter);
//    mMediaDataSourceFactory = new CacheDataSourceFactory(mContext, 0, 0);
  }

  private void initVideoPlayer() {
    mVideoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, mTrackSelector);
    mVideoView.setPlayer(mVideoPlayer);

    mVideoPlayer.addListener(new Player.DefaultEventListener() {
      @Override
      public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
          case PlaybackState.STATE_PAUSED:
            if (mListener != null) {
              mListener.onPlayerBuffering();
            }
            break;
          case PlaybackState.STATE_PLAYING:
            if (playWhenReady) {
              mUpdateProgressHandler.post(mUpdateProgressTask);
              if (mListener != null) {
                mListener.onPlayerPlay();
              }
            } else {
              mUpdateProgressHandler.removeCallbacks(mUpdateProgressTask);
              if (mListener != null) {
                mListener.onPlayerPause();
              }
            }
            break;
          case Player.STATE_ENDED:
            if (playWhenReady) {
              mUpdateProgressHandler.removeCallbacks(mUpdateProgressTask);
              mVideoPlayer.setPlayWhenReady(false);
              if (mListener != null) {
                mListener.onPlayerFinish();
              }
            }
            break;
        }
      }

      @Override
      public void onPlayerError(ExoPlaybackException error) {
        if (mListener != null) {
          mListener.onPlayerError();
        }
      }
    });
  }

  private void prepareVideo(String videoUrl, String adsUrl) {
    Uri uri = Uri.parse(videoUrl);

    mIsFirstPlay = true;

    MediaSource mediaSource;
    mediaSource = buildMediaSource(uri, "");
    mVideoPlayer.prepare(mediaSource);
    if (mInitialPosition > 0)
      mVideoPlayer.seekTo(mInitialPosition);
    mVideoPlayer.setPlayWhenReady(true);

  }

  private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
    int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
            : Util.inferContentType("." + overrideExtension);
    switch (type) {
      case C.TYPE_DASH:
        return new DashMediaSource(uri, buildDataSourceFactory(false),
                new DefaultDashChunkSource.Factory(mMediaDataSourceFactory), mMainHandler, null);
      case C.TYPE_HLS:
        return new HlsMediaSource(uri, mMediaDataSourceFactory, mMainHandler, null);
      case C.TYPE_OTHER:
        return new ExtractorMediaSource(uri, mMediaDataSourceFactory, new DefaultExtractorsFactory(),
                mMainHandler, null);
      default: {
        throw new IllegalStateException("Unsupported type: " + type);
      }
    }
  }

  private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
    return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
  }

  private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
    return buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
  }

  private DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
    return new DefaultDataSourceFactory(mContext, bandwidthMeter,
            buildHttpDataSourceFactory(bandwidthMeter));
  }

  private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
    return new DefaultHttpDataSourceFactory(mUserAgent, bandwidthMeter);
  }

  public void focusSkipButton() {
    WebView webView = (WebView) (mVideoView.getOverlayFrameLayout().getChildAt(0));
    if (webView != null) {
      webView.requestFocus();
      webView.loadUrl(FOCUS_SKIP_BUTTON_WORKAROUND_JS);
    }
  }

  public interface OnVideoPlayerEventListener {
    void onProgressChanged(int progress, int duration);

    void onPlayerLoading(int progress);

    void onPlayerBuffering();

    void onPlayerPlay();

    void onPlayerPause();

    void onPlayerFinish();

    void onPlayerError();

    void onContinueToWatch(String currentPlayUrl);

    void onAdShowed();

    void onAdEnded();
  }

  class CacheDataSourceFactory implements DataSource.Factory {
    private final Context context;
    private final DefaultDataSourceFactory defaultDatasourceFactory;
    private final long maxFileSize, maxCacheSize;

    CacheDataSourceFactory(Context context, long maxCacheSize, long maxFileSize) {
      super();
      this.context = context;
      this.maxCacheSize = maxCacheSize;
      this.maxFileSize = maxFileSize;
      String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
      DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
      defaultDatasourceFactory = new DefaultDataSourceFactory(this.context,
              bandwidthMeter,
              new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter));
    }

    @Override
    public DataSource createDataSource() {
      LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(maxCacheSize);
      SimpleCache simpleCache = new SimpleCache(new File(context.getCacheDir(), "media"), evictor);
      return new CacheDataSource(simpleCache, defaultDatasourceFactory.createDataSource(),
              new FileDataSource(), new CacheDataSink(simpleCache, maxFileSize),
              CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
    }
  }
}
