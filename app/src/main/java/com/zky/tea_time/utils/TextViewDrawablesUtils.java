package com.zky.tea_time.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextViewDrawablesUtils {
    public static final TextViewDrawablesUtils INSTANCE;

    public static void setTextViewDrawables(@NotNull TextView textView, @Nullable Drawable lefticon, @Nullable Drawable topicon, @Nullable Drawable righticon, @Nullable Drawable bottomicon) {
        textView.setCompoundDrawablesWithIntrinsicBounds(lefticon, topicon, righticon, bottomicon);
    }

    static {
        TextViewDrawablesUtils var0 = new TextViewDrawablesUtils();
        INSTANCE = var0;
    }
}
