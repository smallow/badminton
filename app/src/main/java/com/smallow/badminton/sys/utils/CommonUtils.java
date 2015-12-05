package com.smallow.badminton.sys.utils;

import android.view.View;

/**
 * Created by smallow on 15/12/5.
 */
public class CommonUtils {

    public static void checkVisibility(View v, int visibility) {
        if (v == null) {
            return;
        }
        if (v.getVisibility() == visibility) {
            return;
        }
        v.setVisibility(visibility);
    }
}
