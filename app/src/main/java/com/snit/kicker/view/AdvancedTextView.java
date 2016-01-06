package com.snit.kicker.view;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Ilya Snitavets
 */
public class AdvancedTextView extends TextView {
    private int mMaxValue = 10;

    public AdvancedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AdvancedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvancedTextView(Context context) {
        super(context);
    }

    public void setMaxValue(int maxValue){
        mMaxValue = maxValue;
    }

    public synchronized void setValue(int value) {
        this.setText(String.valueOf(value));

        LayerDrawable background = (LayerDrawable) this.getBackground();

        ClipDrawable barValue = (ClipDrawable) background.getDrawable(1);

        int newClipLevel = value / mMaxValue;
        barValue.setLevel(newClipLevel);

        drawableStateChanged();
    }

}