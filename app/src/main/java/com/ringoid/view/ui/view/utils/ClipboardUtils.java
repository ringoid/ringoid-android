/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.view.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ClipboardUtils {

    public static String getString(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData == null) return null;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < clipData.getItemCount(); ++i) {
            result.append(clipData.getItemAt(i).getText());
        }
        return result.toString();
    }

    public static void copyToClipboard(Context context, String key, String message) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(message)) return;

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(key, message));
    }
}
