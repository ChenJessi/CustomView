package com.chen.customview.widget;

//WaveSideBar

import android.text.TextUtils;

import com.chen.customview.bean.SortBean;
import java.util.Comparator;

public class LetterComparator implements Comparator<SortBean> {

    @Override
    public int compare(SortBean l, SortBean r) {
        if (l == null || r == null) {
            return 0;
        }
        String lpy = l.getPinyin().toUpperCase();
        String rpy = r.getPinyin().toUpperCase();
        if (lpy == null || rpy == null) {
            return 0;
        }
        String lInitial = l.getInitial().toUpperCase();
        String rInitial = r.getInitial().toUpperCase();
        if (lInitial == null || rInitial == null){
            return 0;
        }
        if (TextUtils.equals(lInitial, "#") && !TextUtils.equals(rInitial, "#")){
            return 1;
        }
        if (TextUtils.equals(rInitial, "#") && !TextUtils.equals(lInitial, "#")){
            return -1;
        }
        return lpy.compareTo(rpy);
    }
}