package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterBlacklistPhones;
import org.byters.ringoid.view.ui.adapter.AdapterSpinnerCodes;

import javax.inject.Inject;

public class FragmentBlacklistPhonesAdd extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterBlacklistPhones presenterBlacklistPhones;

    private EditText tvPhone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist_add, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvPhone = view.findViewById(R.id.etPhone);
        view.findViewById(R.id.tvBlacklistAdd).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_privacy_phone_blacklist_add_subtitle);

        initSpinner(view);

        showKeyboard(tvPhone);
    }

    @Override
    public boolean onBackPressed() {
        hideKeyboard();
        return false;
    }

    private void initSpinner(View view) {
        Spinner spinner = view.findViewById(R.id.spCodes);
        BaseAdapter adapter = new AdapterSpinnerCodes();
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvBlacklistAdd) {
            presenterBlacklistPhones.onClickBlacklistAdd(tvPhone.getText().toString());
            tvPhone.setText("");
            getActivity().onBackPressed();
        }

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

    }
}
