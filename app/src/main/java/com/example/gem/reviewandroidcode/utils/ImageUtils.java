package com.example.gem.reviewandroidcode.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by maboy on 12/4/19.
 */

public class ImageUtils {

  public static void loadImage(Context context, ImageView imageView, String imageUrl) {
    if (imageUrl == null) return;
    Glide.with(context)
            .load(imageUrl)
            .into(imageView);
  }

  public static void loadImage(Context context, ImageView imageView, int resId) {
    Glide.with(context).load(resId).into(imageView);
  }

  public static void loadImage(Context context, ImageView imageView, String imageUrl, int placeHolder) {
    if (imageUrl == null) return;
    Glide.with(context).load(imageUrl).apply(new RequestOptions().placeholder(placeHolder)).into(imageView);
  }

  public static void loadImage(Context context, ImageView imageView, String imageUrl, int placeHolder, int fallbackId) {
    if (imageUrl == null) return;
    Glide.with(context).load(imageUrl).apply(new RequestOptions().placeholder(placeHolder).error(fallbackId)).into(imageView);
  }

  public static void loadImage(Context context, ImageView imageView, String imageUrl, String fallbackImageUrl) {
    if (imageUrl == null) return;
    Glide.with(context).load(imageUrl).error(Glide.with(context).load(fallbackImageUrl)).into(imageView);
  }

  public static Bitmap createBitmapFromUrl(Context context, String url, int width, int height) {
    try {
      return Glide.with(context)
              .asBitmap()
              .load(url)
              .submit(width, height)
              .get();
    } catch (Exception e) {
      return null;
    }
  }

  public static Bitmap createBitmapFromUrl(Context context, String url) {
    try {
      return Glide.with(context)
              .asBitmap()
              .load(url)
              .submit()
              .get();
    } catch (Exception e) {
      return null;
    }
  }

  /*Set Image Marker
   * Use with Relative Screen and Routing Screen*/
//  public static Bitmap setImageMarker(Context context, Bitmap bitmap) {
//    View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_one_marker, null);
//    ImageView markerImageView = customMarkerView.findViewById(R.id.iv_one_image);
//    if (null != bitmap)
//      markerImageView.setImageBitmap(bitmap);
//    customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//    customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
//    customMarkerView.buildDrawingCache();
//    Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//    Canvas canvas = new Canvas(returnedBitmap);
//    canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
//    Drawable drawable = customMarkerView.getBackground();
//    if (drawable != null)
//      drawable.draw(canvas);
//    customMarkerView.draw(canvas);
//    return returnedBitmap;
//  }

//  public static Bitmap setArMarker(Context context, Bitmap bitmap, int type) {
//    View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_marker_ar, null);
//    ImageView mainMarkerIv = customMarkerView.findViewById(R.id.iv_main_marker);
//    ImageView arTopIv = customMarkerView.findViewById(R.id.iv_ar_top);
//
//    if (null != bitmap)
//      mainMarkerIv.setImageBitmap(bitmap);
//
//    if (type == Constants.SPOT_TYPE.AR_SPOT)
//      arTopIv.setVisibility(View.VISIBLE);
//    else if (type == Constants.SPOT_TYPE.NOT_AR_SPOT)
//      arTopIv.setVisibility(View.GONE);
//
//    customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//    customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
//    customMarkerView.buildDrawingCache();
//    Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//    Canvas canvas = new Canvas(returnedBitmap);
//    canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
//    Drawable drawable = customMarkerView.getBackground();
//    if (drawable != null)
//      drawable.draw(canvas);
//    customMarkerView.draw(canvas);
//    return returnedBitmap;
//  }

  public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
    Drawable drawable = ContextCompat.getDrawable(context, drawableId);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      drawable = (DrawableCompat.wrap(drawable)).mutate();
    }

    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
        drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);

    return bitmap;
  }
}
