package com.ringoid.view.ui.view.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtils {

    public static String getString(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < clipData.getItemCount(); ++i) {
            result.append(clipData.getItemAt(i).getText());
        }
        return result.toString();
    }
}
