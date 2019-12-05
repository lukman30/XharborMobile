/*
 * Copyright (c) 2019
 * Develop : Moh. Lukman Sholeh
 * Xplora Production Software
 */

package com.example.scan_tiket_kapal.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Cardtext extends TextView {

    public Cardtext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Cardtext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Cardtext(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Basic-Regular.ttf");
            setTypeface(tf);
        }
    }

}