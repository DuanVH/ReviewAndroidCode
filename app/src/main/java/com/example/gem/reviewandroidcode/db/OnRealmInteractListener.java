package com.example.gem.reviewandroidcode.db;

/**
 * Created by GEM on 11/8/2017.
 */

public interface OnRealmInteractListener {
  interface OnDeleteListener {
    void onDeleteSuccess();

    void onDeleteFail();
  }
}
