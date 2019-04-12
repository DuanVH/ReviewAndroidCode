package com.example.gem.reviewandroidcode.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<E> extends RecyclerView.ViewHolder {

  public BaseViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public abstract void bindView(E item, int position);
}
