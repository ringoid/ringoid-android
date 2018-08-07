package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.byters.ringoid.R;

public class AdapterWelcome extends AdapterBase {
    private IAdapterWelcomeListener listener;

    public AdapterWelcome(IAdapterWelcomeListener listener) {
        super();
        this.listener = listener;
    }


    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolderBase holder) {
        super.onViewAttachedToWindow(holder);
        listener.onPageShown(holder.getAdapterPosition() == getItemCount() - 1);
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItem(parent);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private class ViewHolderItem extends ViewHolderBase {
        private TextView tvTitle, tvMessage1, tvMessage2, tvMessage3;
        private ImageView ivContent;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_onboarding);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMessage1 = itemView.findViewById(R.id.tvMessage1);
            tvMessage2 = itemView.findViewById(R.id.tvMessage2);
            tvMessage3 = itemView.findViewById(R.id.tvMessage3);
            ivContent = itemView.findViewById(R.id.ivContent);

        }

        @Override
        void setData(int position) {
            tvTitle.setText(position == 0 ? R.string.welcome_title_1 : position == 1 ? R.string.welcome_title_2 : R.string.welcome_title_3);
            tvMessage1.setText(Html.fromHtml(itemView.getResources().getString(position == 0 ? R.string.welcome_message_1_1 : position == 1 ? R.string.welcome_message_2_1 : R.string.welcome_message_3_1)));
            tvMessage2.setText(Html.fromHtml(itemView.getResources().getString(position == 0 ? R.string.welcome_message_1_2 : position == 1 ? R.string.welcome_message_2_2 : R.string.welcome_message_3_2)));
            tvMessage3.setText(Html.fromHtml(itemView.getResources().getString(position == 0 ? R.string.welcome_message_1_3 : position == 1 ? R.string.welcome_message_2_3 : R.string.welcome_message_3_3)));

            ivContent.setImageResource(position == 0 ? R.drawable.welcome_1 : position == 1 ? R.drawable.welcome_2 : R.drawable.welcome_3);

        }
    }
}
