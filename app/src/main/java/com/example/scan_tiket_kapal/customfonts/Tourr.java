/*
 * Copyright (c) 2019
 * Develop : Moh. Lukman Sholeh
 * Xplora Production Software
 */

package com.example.scan_tiket_kapal.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class Tourr extends Button {
    public Tourr(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Tourr(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Tourr(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Basic-Regular.otf");
            setTypeface(tf);
        }
    }

}