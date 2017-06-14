package com.vpaliy.bakingapp.utils;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class StringUtils {

    public static SpannableStringBuilder mergeColoredText(String leftPart, String rightPart, int leftColor, int rightColor) {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final SpannableString leftPartSpannable = new SpannableString(leftPart);
        final SpannableString rightPartSpannable = new SpannableString(rightPart);
        leftPartSpannable.setSpan(new ForegroundColorSpan(leftColor), 0, leftPart.length(), 0);
        rightPartSpannable.setSpan(new ForegroundColorSpan(rightColor), 0, rightPart.length(), 0);
        return builder.append(leftPartSpannable).append(" ").append(rightPartSpannable);
    }
}
