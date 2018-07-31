package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterSettingsPrivacyDistance;
import org.byters.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;

import javax.inject.Inject;

public class FragmentSettingsPrivacyDistance extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterSettingsPrivacyDistance presenterSettingsPrivacyDistance;
    private TextView tvDistance0, tvDistance1, tvDistance2, tvDistance3, tvDistance4;
    private IPresenterSettingsPrivacyDistanceListener listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterSettingsPrivacyDistance.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_privacy_distance, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(R.string.privacy_distance_subtitle);

        tvDistance0 = view.findViewById(R.id.tvDistance0);
        tvDistance1 = view.findViewById(R.id.tvDistance1);
        tvDistance2 = view.findViewById(R.id.tvDistance2);
        tvDistance3 = view.findViewById(R.id.tvDistance3);
        tvDistance4 = view.findViewById(R.id.tvDistance4);

        tvDistance0.setOnClickListener(this);
        tvDistance1.setOnClickListener(this);
        tvDistance2.setOnClickListener(this);
        tvDistance3.setOnClickListener(this);
        tvDistance4.setOnClickListener(this);

        presenterSettingsPrivacyDistance.onCreateView();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        checkClickDistance(v, R.id.tvDistance0, 0);
        checkClickDistance(v, R.id.tvDistance1, 1);
        checkClickDistance(v, R.id.tvDistance2, 2);
        checkClickDistance(v, R.id.tvDistance3, 3);
        checkClickDistance(v, R.id.tvDistance4, 4);

    }

    private void checkClickDistance(View v, int viewId, int type) {
        if (v.getId() != viewId) return;
        presenterSettingsPrivacyDistance.onClickDistance(type);
    }

    private class ListenerPresenter implements IPresenterSettingsPrivacyDistanceListener {
        @Override
        public void setDistance(int type) {
            tvDistance0.setCompoundDrawablesWithIntrinsicBounds(null, null, type == 0 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance1.setCompoundDrawablesWithIntrinsicBounds(null, null, type == 1 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance2.setCompoundDrawablesWithIntrinsicBounds(null, null, type == 2 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance3.setCompoundDrawablesWithIntrinsicBounds(null, null, type == 3 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance4.setCompoundDrawablesWithIntrinsicBounds(null, null, type == 4 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
        }
    }
}
