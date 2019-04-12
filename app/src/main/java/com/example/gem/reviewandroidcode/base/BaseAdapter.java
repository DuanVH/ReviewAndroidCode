package com.example.gem.reviewandroidcode.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<E, V extends BaseViewHolder<E>> extends RecyclerView.Adapter<V> {

  private Context mContext;
  public List<E> mItems;

  public BaseAdapter(Context context) {
    mContext = context;
    mItems = new ArrayList<>();
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  public void addItems(List<E> items) {
    int previousSize = mItems.size();
    mItems = items;
    notifyItemRangeInserted(previousSize, items.size());
  }

  public void refreshList(List<E> items) {
    mItems.clear();
    mItems.addAll(items);
    notifyDataSetChanged();
  }

  public void clear() {
    mItems.clear();
    notifyDataSetChanged();
  }

  public E getItem(int position) {
    return mItems.get(position);
  }

  public List<E> getItems() {
    return mItems;
  }

  protected Context getContext() {
    return mContext;
  }
}
