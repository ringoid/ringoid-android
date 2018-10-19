package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterDialogErrorUnknown;
import com.ringoid.view.presenter.callback.IPresenterDialogErrorUnknownListener;
import com.ringoid.view.ui.dialog.callback.IDialogErrorUnknownListener;

import javax.inject.Inject;

public class DialogErrorUnknown implements View.OnClickListener {

    @Inject
    IPresenterDialogErrorUnknown presenterDialogErrorNetwork;

    @Inject
    INavigator navigator;

    private IDialogErrorUnknownListener listener;
    private AlertDialog dialog;
    private ListenerPresenter listenerPresenter;
    private TextView tvMessage;

    public DialogErrorUnknown(Context context, IDialogErrorUnknownListener listener) {
        ApplicationRingoid.getComponent().inject(this);
        presenterDialogErrorNetwork.setListener(listenerPresenter = new ListenerPresenter());
        this.listener = listener;

        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_error_unknown, null);

        view.findViewById(R.id.tvCancel).setOnClickListener(this);
        view.findViewById(R.id.tvConfirm).setOnClickListener(this);
        dialog.setView(view);

        tvMessage = view.findViewById(R.id.tvMessage);
        tvMessage.setMovementMethod(new LinkMovementMethodInternal());
    }

    public void show() {
        dialog.show();
        presenterDialogErrorNetwork.onShow(dialog.getContext());
    }

    public void cancel() {
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvConfirm) {
            if (listener != null) listener.onConfirm();
        }
        cancel();
    }

    private class ListenerPresenter implements IPresenterDialogErrorUnknownListener {

        @Override
        public void setMessage(String message) {
            tvMessage.setText(Html.fromHtml(String.format(dialog.getContext().getResources().getString(R.string.message_error_unkown_format), message)));
        }
    }

    private class LinkMovementMethodInternal extends LinkMovementMethod {
        public boolean onTouchEvent(TextView widget, android.text.Spannable buffer, android.view.MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
                if (link.length != 0) {
                    String url = link[0].getURL();
                    navigator.navigateWebView(url, dialog.getContext().getString(url.equals(dialog.getContext().getString(R.string.url_terms)) ? R.string.subtitle_terms : R.string.subtitle_privacy));
                    return true;
                }
            }
            return super.onTouchEvent(widget, buffer, event);
        }
    }
}
