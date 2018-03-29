package com.example.yanwu.androidexercise.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerViewArrayAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements View.OnClickListener, Filterable {
    private List<T> items = new ArrayList<>();
    private List<T> itemsFiltered = items;
    private OnRecyclerViewItemClickedListener onItemClickedListener;
    private Filter filter;
    private int selectedPosition = -1;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    @Override
    public int getItemCount() {
        return itemsFiltered == null ? 0 : itemsFiltered.size();
    }

    public void add(T item) {
        items.add(item);
    }

    public void add(int index, T item) {
        items.add(index, item);
    }

    public void addAll(Collection<T> items) {
        this.items.addAll(items);
    }

    public void clear() {
        items.clear();
    }

    public List<T> getOriginalItems() {
        return items;
    }

    public List<T> getItems() {
        return itemsFiltered;
    }

    public T getItem(int position) {
        return itemsFiltered.get(position);
    }

    public int getItemIndex(T item) {
        return itemsFiltered.indexOf(item);
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public void setOnItemClickedListener(OnRecyclerViewItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    // Single selection support
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public T getSelectedItem() {
        return itemsFiltered.get(selectedPosition);
    }

    // multiple selection support
    public boolean isSelected(int position) {
        return getSelectedPositions().contains(position);
    }

    public void switchSelectedState(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelectedState() {
        List<Integer> selection = getSelectedPositions();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedPositions() {
        List<Integer> result = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            result.add(selectedItems.keyAt(i));
        }
        return result;
    }

    public List<T> getSelectedItems() {
        List<T> result = new ArrayList<>(selectedItems.size());
        for (int position: getSelectedPositions()) {
            result.add(items.get(position));
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag == null) {
            return;
        }
        int position = (Integer) tag;
        if (onItemClickedListener != null) {
            onItemClickedListener.onItemClicked(v, this, position);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public void setFilteredItems(Collection<T> array) {
        itemsFiltered = new ArrayList<>();
        itemsFiltered.addAll(array);
    }
}
