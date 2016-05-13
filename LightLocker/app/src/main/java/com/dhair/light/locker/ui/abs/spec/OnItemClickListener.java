package com.dhair.light.locker.ui.abs.spec;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View view, T t, int position);
}
