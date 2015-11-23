package com.smallow.badminton.sys.common;

/**
 * Created by smallow on 15/11/23.
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int postion, T t);
}
