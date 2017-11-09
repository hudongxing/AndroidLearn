package com.example.violet.violettank.ui.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

public class DefaultDividerDecoration extends RecyclerView.ItemDecoration {

    private static final int DIVIDER_HEIGHT = 1; // 1 pixel

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = DIVIDER_HEIGHT;
    }
}
